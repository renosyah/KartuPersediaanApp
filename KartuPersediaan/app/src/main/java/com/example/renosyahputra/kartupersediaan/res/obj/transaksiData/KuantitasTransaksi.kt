package com.example.renosyahputra.kartupersediaan.res.obj.transaksiData

import java.io.Serializable

class KuantitasTransaksi : Serializable {
    lateinit var IdDetailTransaksiData : String
    var Quantity: Int = 0
    var Harga : Int = 0
    var Total: Int = 0

    fun ChangeQty(i : Int){
        this.Quantity = i
        this.Total = this.Harga * this.Quantity
    }


    fun CloneKuantitas() : KuantitasTransaksi {
        val k = KuantitasTransaksi()
        k.IdDetailTransaksiData  = this.IdDetailTransaksiData
        k.Quantity = this.Quantity
        k.Harga = this.Harga
        k.Total = this.Total
        return k
    }
}