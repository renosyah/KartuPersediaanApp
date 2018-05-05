package com.example.renosyahputra.kartupersediaan.res.obj.laporanKartuPersediaan

import com.example.renosyahputra.kartupersediaan.res.obj.persediaanData.PersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.FormatTanggal
import java.io.Serializable

class LaporanKartuPersediaanObj : Serializable {
    lateinit var IdTransaksiData : String
    lateinit var TanggalTransaksi : FormatTanggal
    lateinit var ProdukData : ProdukData
    lateinit var Keterangan : String
    lateinit var ProductFlow :String
    lateinit var ListPersediaanData : ArrayList<PersediaanData>
    var Quantity: Int = 0
    var Total: Int = 0

}