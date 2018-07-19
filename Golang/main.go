package main

import (
	"net/http"
	"fmt"
	"encoding/json"
)

var dbSet *DbVar

func (sr *RestfullServer) SaveDataFromClient(res http.ResponseWriter, req *http.Request){

	var productJson = req.FormValue("ProductList")
	var TransJson = req.FormValue("TransactionList")
	var UserJson = req.FormValue("UserData")

	var dataFromCloud *DataFromCloud

	var User *UserData
	var Trans []TransaksiData
	var Produk []ProdukData

	var response = &SaveResponse{
		Response:true,
		Message:"OK",
	}

	errUser := json.Unmarshal([]byte(UserJson),&User)
	if errUser != nil{
		fmt.Println(errUser)
		response.Response = false
		response.Message = errUser.Error()
	}

	errTrans := json.Unmarshal([]byte(TransJson),&Trans)
	if errTrans != nil{
		fmt.Println(errTrans)
		response.Response = false
		response.Message = errTrans.Error()
	}

	errProduk := json.Unmarshal([]byte(productJson),&Produk)
	if errProduk != nil{
		fmt.Println(errProduk)
		response.Response = false
		response.Message = errProduk.Error()
	}

	dataFromCloud = &DataFromCloud{
		User:User,
		Transaksi:Trans,
		Produk:Produk,
	}

	errSave := dataFromCloud.SaveDataFromCloud(sr)
	if errSave != nil {
		fmt.Println(errSave)
		response.Response = false
		response.Message = errSave.Error()
	}

	json.NewEncoder(res).Encode(response)
}

func (sr *RestfullServer) GetAllDataToClient(res http.ResponseWriter, req *http.Request)  {

	var Username = req.FormValue("Username")
	var Password = req.FormValue("Password")

	var user = &UserData{UserName:Username,Password:Password}
	var dataFromCloud *DataFromCloud

	var LoadResponse = &LoadResponse{
		Response:true,
		Message:"",
	}
	var errLoad error


	dataFromCloud,errLoad = user.LoadDataFromCloud(sr)
	if errLoad != nil {
		fmt.Println(errLoad)
		LoadResponse.Response = false
		LoadResponse.Message = errLoad.Error()

	}

	LoadResponse.Data = dataFromCloud

	json.NewEncoder(res).Encode(LoadResponse)

}


func main()  {

	dbSet = &DbVar{
		NameDB:"KartuPersediaan",
		User:"root",
		Password:"",
	}

	var serverRestfull = &RestfullServer{
		Db:dbSet,
	}

	fmt.Println("server is running...")

	http.HandleFunc("/saveData", serverRestfull.SaveDataFromClient)
	http.HandleFunc("/getData", serverRestfull.GetAllDataToClient)
	http.ListenAndServe(":7890",nil)
}