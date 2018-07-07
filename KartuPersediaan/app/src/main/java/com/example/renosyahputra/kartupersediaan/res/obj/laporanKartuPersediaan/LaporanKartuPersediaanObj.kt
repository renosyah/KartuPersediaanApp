package com.example.renosyahputra.kartupersediaan.res.obj.laporanKartuPersediaan

import com.example.renosyahputra.kartupersediaan.res.obj.persediaanData.PersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.FormatTanggal
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.KuantitasTransaksi
import java.io.Serializable

class LaporanKartuPersediaanObj : Serializable {
    lateinit var IdTransaksiData : String
    lateinit var TanggalTransaksi : FormatTanggal
    lateinit var ProdukData : ProdukData
    lateinit var Keterangan : String
    lateinit var ProductFlow :String
    lateinit var ListPersediaanData : ArrayList<PersediaanData>
    lateinit var ListKuantitas : ArrayList<KuantitasTransaksi>

    fun CloneLaporanKartuPersediaan() : LaporanKartuPersediaanObj{
        val newOne = LaporanKartuPersediaanObj()

        val tanggal = FormatTanggal()
        tanggal.Hari = this.TanggalTransaksi.Hari
        tanggal.Bulan = this.TanggalTransaksi.Bulan
        tanggal.Tahun = this.TanggalTransaksi.Tahun

        val produk = ProdukData()
        produk.IdProduk = this.ProdukData.IdProduk
        produk.Nama = this.ProdukData.Nama
        produk.Harga = this.ProdukData.Harga

        val persediaan = ArrayList<PersediaanData>()
        for (data in this.ListPersediaanData){
            persediaan.add(data)
        }
        val kuantitas = ArrayList<KuantitasTransaksi>()
        for (data in this.ListKuantitas){
            kuantitas.add(data.CloneKuantitas())
        }

        newOne.IdTransaksiData  = this.IdTransaksiData
        newOne.TanggalTransaksi = tanggal
        newOne.ProdukData = produk
        newOne.Keterangan = this.Keterangan
        newOne.ProductFlow = this.ProductFlow
        newOne.ListPersediaanData = persediaan
        newOne.ListKuantitas = kuantitas

        return newOne
    }

    fun GetTotalListKuantitas() : Int{
        var total = 0
        for (i in this.ListKuantitas){
            total += i.Harga * i.Quantity
        }

        return total
    }

    fun GetKuantitas() : Int{
        var qty = 0
        for (i in this.ListKuantitas){
            qty += i.Quantity
        }

        return qty
    }

    fun SetHargaAll(h : Int){
        for (i in this.ListKuantitas.listIterator()){
            i.Harga = h
            i.Total = i.Harga * i.Quantity
        }

    }

    fun SetQtyAll(qty : Int){
        for (i in this.ListKuantitas.listIterator()){
            i.Quantity = qty
            i.Total = i.Harga * i.Quantity
        }

    }

    fun SetTotalAll(h : Int,qty : Int){
        for (i in this.ListKuantitas.listIterator()){
            i.Total = h * qty
        }

    }
}