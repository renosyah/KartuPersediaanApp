package com.example.renosyahputra.kartupersediaan.res.obj.transaksiData

import com.example.renosyahputra.kartupersediaan.res.obj.persediaanData.PersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import java.io.Serializable

class DetailTransaksi : Serializable {
    lateinit var IdTransaksiData : String
    lateinit var IdDetailTransaksiData : String
    lateinit var ProdukData : ProdukData
    lateinit var ListPersediaanData : ArrayList<PersediaanData>
    lateinit var ListKuantitas : ArrayList<KuantitasTransaksi>
    var IsThisValidDetailTransaction = true

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

    fun SetQuantityAll(q : Int){
        for (i in this.ListKuantitas.listIterator()){
            i.Quantity = q
            i.Total = i.Harga * i.Quantity
        }
    }

    fun SetHargaAll(h : Int){
        for (i in this.ListKuantitas.listIterator()){
            i.Harga = h
            i.Total = i.Harga * i.Quantity
        }

    }
}