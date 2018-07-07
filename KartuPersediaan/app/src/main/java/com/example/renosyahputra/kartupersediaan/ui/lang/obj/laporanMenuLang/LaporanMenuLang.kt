package com.example.renosyahputra.kartupersediaan.ui.lang.obj.laporanMenuLang

import java.io.Serializable

class LaporanMenuLang : Serializable {
    lateinit var filterPilihProduk : String
    lateinit var filterPilihPeriode : String
    lateinit var filterPilihMethod : String

    lateinit var Kartupersediaan : String

    lateinit var KartuPersediaanKosong : String


    lateinit var namaProductDetail :String
    lateinit var price :String
    lateinit var qty :String
    lateinit var total :String

    lateinit var stokKe :String
    lateinit var KuantitasKe :String

    lateinit var titleTransForListQty : String
    lateinit var titleStockForListStock : String

    lateinit var exportTitle : String
    lateinit var toPDF :String
    lateinit var toPDFLANDSCAPE : String

    lateinit var save : String
    lateinit var cancel : String

    lateinit var saved : String


    lateinit var titleLaporan : String
    lateinit var dateAwalLap : String
    lateinit var dateAkhirLap : String
    lateinit var hinga : String

    lateinit var WarningInvalidProduckInTrans : String
    lateinit var WarningInvalidProduckInTransTitle : String

    lateinit var titleOpenFile : String
    lateinit var messageOpenFile : String
    lateinit var ok  :String
    lateinit var no : String


    lateinit var FilterChooseAllProduct : String
    lateinit var FiterChooseAllPeriod : String


    lateinit var titlePeriodeTahun : String
}