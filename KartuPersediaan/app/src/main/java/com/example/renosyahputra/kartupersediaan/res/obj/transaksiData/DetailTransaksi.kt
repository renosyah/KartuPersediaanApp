package com.example.renosyahputra.kartupersediaan.res.obj.transaksiData

import com.example.renosyahputra.kartupersediaan.res.obj.persediaanData.PersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import java.io.Serializable

class DetailTransaksi : Serializable {
    lateinit var IdTransaksiData : String
    lateinit var ProdukData : ProdukData
    lateinit var ListPersediaanData : ArrayList<PersediaanData>
    var Quantity: Int = 0
    var Total: Int = 0
}