package main

type SaveResponse struct {
	Response bool `json:"Response"`
	Message string `json:"Message"`
}

type LoadResponse struct {
	Response bool `json:"Response"`
	Message string `json:"Message"`
	Data *DataFromCloud `json:"Data"`
}

type DbVar struct {
	NameDB string
	Password string
	User string
}

type RestfullServer struct{
	Db *DbVar
}

type DataFromCloud struct {
	User *UserData `json:"User"`
	Transaksi []TransaksiData `json:"Transaksi"`
	Produk []ProdukData `json:"Produk"`
}

type UserData struct {
	IdUser string `json:"IdUser"`
	UserName string `json:"UserName"`
	Password string `json:"Password"`
	Name string `json:"Name"`
	Email string `json:"Email"`
	CompanyName string `json:"CompanyName"`
}

type TransaksiData struct {
	IdTransaksiData string `json:"IdTransaksiData"`
	TanggalTransaksi *FormatTanggal `json:"TanggalTransaksi"`
	Jam *FormatWaktu `json:"Jam"`
	ListDetail []DetailTransaksi `json:"ListDetail"`
	Keterangan string `json:"Keterangan"`
	ProductFlow string `json:"ProductFlow"`
	SubTotal int `json:"SubTotal"`
	IsThisValidTransaction bool `json:"IsThisValidTransaction"`
}

type DetailTransaksi struct {
	IdTransaksiData string `json:"IdTransaksiData"`
	IdDetailTransaksiData string `json:"IdDetailTransaksiData"`
	ProdukData *ProdukData `json:"ProdukData"`
	ListPersediaanData []PersediaanData `json:"ListPersediaanData"`
	ListKuantitas []KuantitasTransaksi `json:"ListKuantitas"`
	IsThisValidDetailTransaction bool `json:"IsThisValidDetailTransaction"`
}

type KuantitasTransaksi struct {
	IdDetailTransaksiData string `json:"IdDetailTransaksiData"`
	Quantity int `json:"Quantity"`
	Harga  int `json:"Harga"`
	Total int `json:"Total"`
}

type ProdukData struct {
	IdProduk string `json:"IdProduk"`
	Nama string `json:"Nama"`
	Harga int `json:"Harga"`
	Satuan string `json:"Satuan"`
}

type PersediaanData struct {
	TanggalMasuk *FormatTanggal `json:"PersediaanData"`
	Produk *ProdukData `json:"Produk"`
	Jumlah int `json:"Jumlah"`
}


type FormatTanggal struct {
	Hari int `json:"Hari"`
	Bulan int `json:"Bulan"`
	Tahun int `json:"Tahun"`
}

type FormatWaktu struct {
	Jam int `json:"Jam"`
	Menit int `json:"Menit"`
	PMorAM string `json:"PMorAM"`
}