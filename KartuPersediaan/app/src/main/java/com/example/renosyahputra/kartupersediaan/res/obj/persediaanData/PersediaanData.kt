package com.example.renosyahputra.kartupersediaan.res.obj.persediaanData

import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.FormatTanggal
import java.io.Serializable

class PersediaanData : Serializable {
    val TanggalMasuk : FormatTanggal = FormatTanggal()
    lateinit var Produk : ProdukData
    var Jumlah : Int = 0
    var Total : Int = 0
}