package com.example.renosyahputra.kartupersediaan.res.obj.transaksiData

import java.io.Serializable
import java.text.SimpleDateFormat

class FormatTanggal  :Serializable {
    var Hari : Int = 0
    var Bulan : Int = 0
    var Tahun: Int = 0

    fun toDateString() : String{
        return Hari.toString() +"/"+Bulan+"/"+ Tahun
    }

    fun BandingkanTanggalYangLebihKecil(j1 : FormatWaktu,j2  :FormatWaktu,tanggal: FormatTanggal) : Boolean {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val tgl1 = sdf.parse(tanggal.toDateString()+" "+j1.MakeJamString())
        val tgl2 = sdf.parse(this.toDateString()+" "+j2.MakeJamString())
        return (tgl2 >= tgl1)
    }

    companion object {

        fun BandingkanTanggalPatokanDenganYangLebihKecil(TanggalYgSeharusnyaLebihKecil: FormatTanggal, TanggalPatokan: FormatTanggal): Boolean {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val tgl1 = sdf.parse(TanggalYgSeharusnyaLebihKecil.toDateString())
            val tgl2 = sdf.parse(TanggalPatokan.toDateString())
            return (tgl2 >= tgl1)
        }

        fun BandingkanTanggalPatokanDenganYangLebihBesar(TanggalYgSeharusnyaLebihBesar: FormatTanggal, TanggalPatokan: FormatTanggal): Boolean {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val tgl1 = sdf.parse(TanggalYgSeharusnyaLebihBesar.toDateString())
            val tgl2 = sdf.parse(TanggalPatokan.toDateString())
            return (tgl2 <= tgl1)
        }
    }
}