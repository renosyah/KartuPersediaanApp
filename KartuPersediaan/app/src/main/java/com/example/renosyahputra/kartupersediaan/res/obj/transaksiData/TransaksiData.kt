package com.example.renosyahputra.kartupersediaan.res.obj.transaksiData

import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import java.io.Serializable

class TransaksiData : Serializable {
    companion object {
        val ProductOut = "OUT"
        val ProductIn = "IN"
    }

    lateinit var IdTransaksiData : String
    lateinit var TanggalTransaksi : FormatTanggal
    var Jam : FormatWaktu = FormatWaktu()
    lateinit var ListDetail : ArrayList<DetailTransaksi>
    lateinit var Keterangan : String
    lateinit var ProductFlow :String
    var SubTotal : Int = 0


    fun CloneTransData() : TransaksiData {
        val newData = TransaksiData()

        val tgl = FormatTanggal()
        tgl.Hari = this.TanggalTransaksi.Hari
        tgl.Bulan = this.TanggalTransaksi.Bulan
        tgl.Tahun = this.TanggalTransaksi.Tahun

        val waktu = FormatWaktu()
        waktu.Jam = this.Jam.Jam
        waktu.Menit = this.Jam.Menit

        val detail = ArrayList<DetailTransaksi>()
        for (d in this.ListDetail){
            val newDetail = DetailTransaksi()
            val product = ProdukData()
            product.IdProduk = d.ProdukData.IdProduk
            product.Nama = d.ProdukData.Nama
            product.Harga = d.ProdukData.Harga

            newDetail.IdTransaksiData = this.IdTransaksiData
            newDetail.ProdukData = product
            newDetail.ListPersediaanData = ArrayList()
            newDetail.Quantity = d.Quantity
            newDetail.Total = d.Total
            detail.add(newDetail)
        }


        newData.Keterangan = this.Keterangan
        newData.ProductFlow = this.ProductFlow
        newData.SubTotal = this.SubTotal
        newData.TanggalTransaksi = tgl
        newData.Jam = waktu
        newData.IdTransaksiData = this.IdTransaksiData
        newData.ListDetail = detail

        return newData
    }
}