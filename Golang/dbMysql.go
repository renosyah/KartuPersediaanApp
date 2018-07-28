package main

import (
	"database/sql"
	"fmt"
	"os"
	_ "github.com/go-sql-driver/mysql"
	"github.com/pkg/errors"
)

func (d *DbVar) connect() *sql.DB {
	var db, err = sql.Open("mysql", d.User + ":"+d.Password+"@/"+d.NameDB) //default mysql setting
	err = db.Ping()
	if err != nil {
		fmt.Println("database tidak bisa dihubungi")
		os.Exit(0)
	}
	return db
}

// ----------------------------- save main Data ----------------------------------------------

func (data *DataFromCloud) SaveDataFromCloud (sr *RestfullServer) error{

	checkIfUsernameSameWithOtherUser,errcheckIfUsernameSameWithOtherUser := data.User.CheckIfSameUsernameWithOtherUser(sr)
	if errcheckIfUsernameSameWithOtherUser != nil {
		return errcheckIfUsernameSameWithOtherUser
	}

	CheckIfSameEmailWithOtherUser,errCheckIfSameEmailWithOtherUser := data.User.CheckIfSameEmailWithOtherUser(sr)
	if errCheckIfSameEmailWithOtherUser != nil {
		return errCheckIfSameEmailWithOtherUser
	}


	if checkIfUsernameSameWithOtherUser {
		return errors.New("username is use by other user!")
	}

	if CheckIfSameEmailWithOtherUser {
		return errors.New("email is use by other user!")
	}


	errRemoveThisUserTransaksi := data.User.RemoveTransaksi(sr)
	if errRemoveThisUserTransaksi != nil{
		return errRemoveThisUserTransaksi
	}

	errRemoveThisUserProduct := data.User.RemoveProduk(sr)
	if errRemoveThisUserProduct != nil{
		return errRemoveThisUserProduct
	}

	errRemoveThisUserData := data.User.RemoveUser(sr)
	if errRemoveThisUserData != nil{
		return errRemoveThisUserData
	}

	errSaveThisNewUserData := data.User.InsertUserData(sr)
	if errSaveThisNewUserData != nil{
		return errSaveThisNewUserData
	}

	for _,EachDataProduct := range data.Produk {
		errSaveThisNewUserProduct := EachDataProduct.InsertProductData(sr,data.User)
		if errSaveThisNewUserProduct != nil{
			return errSaveThisNewUserProduct
		}
	}


	for _,EachDataTrans := range data.Transaksi {
		errSaveThisNewUserTransaksi := EachDataTrans.InsertTransData(sr,data.User)
		if errSaveThisNewUserTransaksi != nil{
			return errSaveThisNewUserTransaksi
		}
	}


	return nil
}
// ----------------------------- ----------- ----------------------------------------------

// ----------------------------- get main Data ----------------------------------------------
func (user *UserData) LoadDataFromCloud (sr *RestfullServer) (*DataFromCloud,error){
	var datas *DataFromCloud

	userToSend,errGetDataUserFromLogin := user.GetUserByLogin(sr)
	if errGetDataUserFromLogin != nil {
		return datas,errGetDataUserFromLogin
	}

	transToSend,errGetAllDataTrans := userToSend.GetAllUserTransaction(sr)
	if errGetAllDataTrans != nil {
		return datas,errGetAllDataTrans
	}
	produkToSend,errGetAllProduk := userToSend.GetAllUserProduct(sr)
	if errGetAllProduk != nil {
		return datas,errGetAllProduk
	}

	datas = &DataFromCloud{
		User:userToSend,
		Transaksi:transToSend,
		Produk:produkToSend,
	}

	return datas,nil
}
// ----------------------------- ----------- ----------------------------------------------

// ----------------------------- get data from db ----------------------------------------------
func (u *UserData) CheckIfSameUsernameWithOtherUser(sr *RestfullServer) (bool,error){
	var adaUsernameYgSama = false

	db := sr.Db.connect()
	defer db.Close()

	errUserQuery := db.QueryRow("select IF(COUNT(*),'true','false') from User where Username=? and IdUser !=?",u.UserName,u.IdUser).Scan(&adaUsernameYgSama)
	if errUserQuery != nil {
		return adaUsernameYgSama,errUserQuery
	}

	return adaUsernameYgSama,nil
}

func (u *UserData) CheckIfSameEmailWithOtherUser(sr *RestfullServer) (bool,error){
	var adaEmailYangSama = false

	db := sr.Db.connect()
	defer db.Close()

	errUserEmailQuery := db.QueryRow("select IF(COUNT(*),'true','false') from User where Email=? and IdUser !=?",u.Email,u.IdUser).Scan(&adaEmailYangSama)
	if errUserEmailQuery != nil {
		return adaEmailYangSama,errUserEmailQuery
	}

	return adaEmailYangSama,nil
}

func (u *UserData) GetUserByLogin(sr *RestfullServer) (*UserData,error){
	var user  = &UserData{}

	db := sr.Db.connect()
	defer db.Close()

	errUserQuery := db.QueryRow("select IdUser,NamaPemilik,NamaPerusahaan,Email,Username,Password from User where Username=? or Email=?",u.UserName,u.UserName).Scan(&user.IdUser,&user.Name,&user.CompanyName,&user.Email,&user.UserName,&user.Password)
	if errUserQuery != nil {
		return user,errUserQuery
	}

	if user.Password != u.Password {
		return user,errors.New("password is not valid!")
	}


	return user,nil
}

func (u *UserData) GetAllUserProduct(sr *RestfullServer) ([]ProdukData,error) {

	db := sr.Db.connect()
	defer db.Close()

	var productList []ProdukData
	productList = []ProdukData{}

	dataProductQuery,errProductQuery := db.Query("select IdProduk,NamaProduk,harga,satuan from Produk where IdUser=?",u.IdUser)
	if errProductQuery != nil {
		return productList,errProductQuery
	}

	for dataProductQuery.Next(){
		var OneProduct = ProdukData{}
		dataProductQuery.Scan(
			&OneProduct.IdProduk,
			&OneProduct.Nama,
			&OneProduct.Harga,
			&OneProduct.Satuan,
		)
		productList = append(productList,OneProduct)
	}



	return productList,nil

}

func (p *ProdukData) GetOneUserProduct(sr *RestfullServer) (*ProdukData,error){
	var OneProduct = &ProdukData{}

	db := sr.Db.connect()
	defer db.Close()

	errprodukQuery := db.QueryRow("select IdProduk,NamaProduk,harga,satuan from Produk where IdProduk=?",p.IdProduk).Scan(&OneProduct.IdProduk,&OneProduct.Nama,&OneProduct.Harga,&OneProduct.Satuan)
	if errprodukQuery  != nil {
		return OneProduct,errprodukQuery
	}


	return OneProduct,nil
}

func (u *UserData) GetAllUserTransaction(sr *RestfullServer) ([]TransaksiData,error){
	db := sr.Db.connect()
	defer db.Close()

	var transactionList []TransaksiData
	transactionList = []TransaksiData{}

	dataTransactionQuery,errTransactionQuery := db.Query("select IdTransaksi,TanggalTransaksi,WaktuTransaksi,keterangan,ProductFlow,SubTotal from Transaksi where IdUser=?",u.IdUser)
	if errTransactionQuery != nil {
		return transactionList,errTransactionQuery
	}

	for dataTransactionQuery.Next(){
		var OneTransaction = TransaksiData{}
		var TanggalTransaksi,waktuTransaksi string

		dataTransactionQuery.Scan(
			&OneTransaction.IdTransaksiData,
			&TanggalTransaksi,
			&waktuTransaksi,
			&OneTransaction.Keterangan,
			&OneTransaction.ProductFlow,
			&OneTransaction.SubTotal,
		)

		OneTransaction.TanggalTransaksi = SetTanggalFormat(TanggalTransaksi)
		OneTransaction.Jam = SetTimeFormat(waktuTransaksi)

		dataDetailTransaction,errGetDetailEachTrans := OneTransaction.GetAllDetailTransaction(sr)
		if errGetDetailEachTrans != nil {
			return transactionList,errGetDetailEachTrans
		}
		OneTransaction.ListDetail = dataDetailTransaction
		OneTransaction.IsThisValidTransaction = true


		transactionList = append(transactionList,OneTransaction)
	}


	return transactionList,nil
}

func (t *TransaksiData) GetAllDetailTransaction(sr *RestfullServer) ([]DetailTransaksi,error){
	db := sr.Db.connect()
	defer db.Close()

	var detailTransactionList []DetailTransaksi
	detailTransactionList = []DetailTransaksi{}

	detailTransactionListQuery,errdetailTransactionListQuery := db.Query("select IdTransaksi,IdProduk,IdDetailTransaksi from DetailTransaksi where IdTransaksi=?",t.IdTransaksiData)
	if errdetailTransactionListQuery != nil {
		return detailTransactionList,errdetailTransactionListQuery
	}

	for detailTransactionListQuery.Next() {
		var OnedetailTransactionList = DetailTransaksi{}
		var Product = &ProdukData{}

		detailTransactionListQuery.Scan(
			&OnedetailTransactionList.IdTransaksiData,
			&Product.IdProduk,
			&OnedetailTransactionList.IdDetailTransaksiData,
		)

		ProdukInDetail,errGetOneUserProduct := Product.GetOneUserProduct(sr)
		if errGetOneUserProduct != nil {
			return detailTransactionList,errGetOneUserProduct
		}

		OnedetailTransactionList.ProdukData = ProdukInDetail

		ListDaftarKuantitas,errGetAllDaftarKuantitas := OnedetailTransactionList.GetAllDaftarKuantitas(sr)
		if errGetAllDaftarKuantitas != nil {
			return detailTransactionList,errGetAllDaftarKuantitas
		}

		OnedetailTransactionList.ListKuantitas = ListDaftarKuantitas
		OnedetailTransactionList.IsThisValidDetailTransaction = true
		OnedetailTransactionList.ListPersediaanData = []PersediaanData{}

		detailTransactionList = append(detailTransactionList,OnedetailTransactionList)

	}

	return detailTransactionList,nil
}

func (d *DetailTransaksi) GetAllDaftarKuantitas(sr *RestfullServer) ([]KuantitasTransaksi,error){
	db := sr.Db.connect()
	defer db.Close()

	var DaftarKuantitasList []KuantitasTransaksi
	DaftarKuantitasList = []KuantitasTransaksi{}

	DaftarKuantitasListQuery,errDaftarKuantitasListQuery := db.Query("select IdDetailTransaksi,Kuantitas,Harga,Total from DaftarKuantitas where IdDetailTransaksi=?",d.IdDetailTransaksiData)
	if errDaftarKuantitasListQuery != nil {
		return DaftarKuantitasList,errDaftarKuantitasListQuery
	}

	for DaftarKuantitasListQuery.Next(){
		var OneDaftarKuantitasList = KuantitasTransaksi{}
		DaftarKuantitasListQuery.Scan(
			&OneDaftarKuantitasList.IdDetailTransaksiData,
			&OneDaftarKuantitasList.Quantity,
			&OneDaftarKuantitasList.Harga,
			&OneDaftarKuantitasList.Total,
		)

		DaftarKuantitasList = append(DaftarKuantitasList,OneDaftarKuantitasList)
	}

	return DaftarKuantitasList,nil
}
// ----------------------------- ----------- ----------------------------------------------

// ----------------------------- remove data ----------------------------------------------

func (user *UserData) RemoveUser(sr *RestfullServer) error  {
	db := sr.Db.connect()
	defer db.Close()

	_,err := db.Exec("delete from User where IdUser=?",user.IdUser)
	if err != nil{
		return err
	}

	return nil

}

func (user *UserData) RemoveProduk(sr *RestfullServer) error  {
	db := sr.Db.connect()
	defer db.Close()

	_,err := db.Exec("delete from Produk where IdUser=?",user.IdUser)
	if err != nil{
		return err
	}

	return nil
}

func (user *UserData) RemoveTransaksi(sr *RestfullServer) error  {

	db := sr.Db.connect()
	defer db.Close()

	var DataTransaksi []TransaksiData

	dataTransQuery,errGetIdTrans := db.Query("select IdTransaksi from Transaksi where IdUser=?",user.IdUser)
	if  errGetIdTrans != nil{
		return errGetIdTrans
	}

	for dataTransQuery.Next(){
		var OneDataTransaksi = TransaksiData{}
		dataTransQuery.Scan(&OneDataTransaksi.IdTransaksiData)
		DataTransaksi = append(DataTransaksi,OneDataTransaksi)
	}

	for _,EachTrans := range DataTransaksi{
		errRemoveDetail := EachTrans.RemoveDetailTransaksi(sr)
		if errRemoveDetail != nil {
			return  errRemoveDetail
		}
	}

	_,errRemoveTransQuery := db.Exec("delete from transaksi where IdUser=?",user.IdUser)
	if errRemoveTransQuery != nil{
		return errRemoveTransQuery
	}

	return nil
}

func (trans *TransaksiData) RemoveDetailTransaksi(sr *RestfullServer) error  {

	db := sr.Db.connect()
	defer db.Close()

	var DataDetailTransaksi []DetailTransaksi

	dataDetailTransQuery,errGetIdDetailTransQuery := db.Query("select IdDetailTransaksi from DetailTransaksi where IdTransaksi=?",trans.IdTransaksiData)
	if errGetIdDetailTransQuery != nil{
		return errGetIdDetailTransQuery
	}

	for dataDetailTransQuery.Next(){
		var OneDataDetailTransaksi = DetailTransaksi{}
		dataDetailTransQuery.Scan(&OneDataDetailTransaksi.IdDetailTransaksiData)
		DataDetailTransaksi = append(DataDetailTransaksi,OneDataDetailTransaksi)
	}

	for _,EachDetailTrans := range DataDetailTransaksi{
		errRemoveDaftarKuantitas := EachDetailTrans.RemoveDaftarKuantitas(sr)
		if errRemoveDaftarKuantitas != nil{
			return errRemoveDaftarKuantitas
		}
	}

	_,errRemoveDetailTransQuery := db.Exec("delete from DetailTransaksi where IdTransaksi=?",trans.IdTransaksiData)
	if errRemoveDetailTransQuery != nil {
		return errRemoveDetailTransQuery
	}

	return nil

}

func (detail *DetailTransaksi) RemoveDaftarKuantitas(sr *RestfullServer) error  {

	db := sr.Db.connect()
	defer db.Close()

	_,errRemoveDaftarKuantitas := db.Exec("delete from DaftarKuantitas where IdDetailTransaksi=?",detail.IdDetailTransaksiData)
	if errRemoveDaftarKuantitas != nil {
		return errRemoveDaftarKuantitas
	}

	return nil
}

// ----------------------------- ------------ ----------------------------------------------

// ----------------------------- insert data ----------------------------------------------

func (u *UserData) InsertUserData(sr *RestfullServer) error {
	db := sr.Db.connect()
	defer db.Close()

	_,errInsertUserData := db.Exec("insert into User (IdUser,NamaPemilik,NamaPerusahaan,Email,Username,Password) values (?,?,?,?,?,?)",u.IdUser,u.Name,u.CompanyName,u.Email,u.UserName,u.Password)
	if errInsertUserData != nil {
		return errInsertUserData
	}

	return nil
}

func (p *ProdukData) InsertProductData(sr *RestfullServer,u *UserData) error {

	db := sr.Db.connect()
	defer db.Close()

	_,errInsertProductData := db.Exec("insert into Produk (IdUser,IdProduk,NamaProduk,harga,satuan) values (?,?,?,?,?)",u.IdUser,p.IdProduk,p.Nama,p.Harga,p.Satuan)
	if errInsertProductData != nil {
		return errInsertProductData
	}

	return nil
}

func (t *TransaksiData) InsertTransData(sr *RestfullServer,u *UserData) error {

	db := sr.Db.connect()
	defer db.Close()

	_,errInsertUserData := db.Exec("insert into Transaksi (IdUser,IdTransaksi,TanggalTransaksi,WaktuTransaksi,keterangan,ProductFlow,SubTotal) values (?,?,?,?,?,?,?)",u.IdUser,t.IdTransaksiData,t.TanggalTransaksi.ToTanggalString(),t.Jam.ToWaktuString(),t.Keterangan,t.ProductFlow,t.SubTotal)
	if errInsertUserData != nil {
		return errInsertUserData
	}

	for _,EachDetail := range t.ListDetail {
		errInsertDetailTransData := EachDetail.InsertDetailTransData(sr)
		if errInsertDetailTransData != nil {
			return errInsertDetailTransData
		}
	}

	return nil
}

func (dt *DetailTransaksi) InsertDetailTransData(sr *RestfullServer) error {

	db := sr.Db.connect()
	defer db.Close()

	_,errInsertDetailTransData := db.Exec("insert into DetailTransaksi (IdTransaksi,IdProduk,IdDetailTransaksi) values (?,?,?)",dt.IdTransaksiData,dt.ProdukData.IdProduk,dt.IdDetailTransaksiData)
	if errInsertDetailTransData != nil {
		return errInsertDetailTransData
	}

	for _,EachDetail := range dt.ListKuantitas {
		errInsertDaftarKuantitasData := EachDetail.InsertDaftarKuantitasData(sr)
		if errInsertDaftarKuantitasData != nil {
			return errInsertDaftarKuantitasData
		}
	}

	return nil
}

func (dk *KuantitasTransaksi) InsertDaftarKuantitasData(sr *RestfullServer) error {
	db := sr.Db.connect()
	defer db.Close()

	_,errInsertDetailTransData := db.Exec("insert into DaftarKuantitas(IdDetailTransaksi,Kuantitas,Harga,Total) values (?,?,?,?)",dk.IdDetailTransaksiData,dk.Quantity,dk.Harga,dk.Total)
	if errInsertDetailTransData != nil {
		return errInsertDetailTransData
	}

	return nil
}
// ----------------------------- ------------ ----------------------------------------------