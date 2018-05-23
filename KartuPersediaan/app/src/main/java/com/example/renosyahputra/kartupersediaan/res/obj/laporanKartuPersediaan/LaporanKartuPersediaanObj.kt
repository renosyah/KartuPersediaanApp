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