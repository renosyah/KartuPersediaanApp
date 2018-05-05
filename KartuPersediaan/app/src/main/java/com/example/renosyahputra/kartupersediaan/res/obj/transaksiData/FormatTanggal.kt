package com.example.renosyahputra.kartupersediaan.res.obj.transaksiData

import java.io.Serializable

class FormatTanggal  :Serializable {
    var Hari : Int = 0
    var Bulan : Int = 0
    var Tahun: Int = 0

    fun toDateString() : String{
        return Hari.toString() +"/"+Bulan+"/"+ Tahun
    }

    fun BandingkanTanggalYangLebihKecil(tanggal: FormatTanggal) : Boolean {
        return (this.Hari >= tanggal.Hari && this.Bulan >= tanggal.Bulan && this.Tahun >= tanggal.Tahun)
    }
}