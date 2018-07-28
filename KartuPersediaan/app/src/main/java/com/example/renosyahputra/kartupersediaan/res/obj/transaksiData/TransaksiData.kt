package com.example.renosyahputra.kartupersediaan.res.obj.transaksiData

import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import java.io.Serializable

class TransaksiData : Serializable {

    lateinit var IdTransaksiData : String
    lateinit var TanggalTransaksi : FormatTanggal
    var Jam : FormatWaktu = FormatWaktu()
    lateinit var ListDetail : ArrayList<DetailTransaksi>
    lateinit var Keterangan : String
    lateinit var ProductFlow :String
    var SubTotal : Int = 0
    var IsThisValidTransaction = true


    companion object {
        val ProductOut = "OUT"
        val ProductIn = "IN"
    }

    fun CloneTransData() : TransaksiData {
        val newData = TransaksiData()

        val detail = ArrayList<DetailTransaksi>()
        for (d in this.ListDetail){
            val newDetail = DetailTransaksi()
            val product = ProdukData()
            product.IdProduk = d.ProdukData.IdProduk
            product.Nama = d.ProdukData.Nama
            product.Harga = d.ProdukData.Harga

            newDetail.IdTransaksiData = this.IdTransaksiData
            newDetail.IdDetailTransaksiData = d.IdDetailTransaksiData
            newDetail.ListPersediaanData = ArrayList()

            val ListKuantitas = ArrayList<KuantitasTransaksi>()
            for (lk in d.ListKuantitas) {

                val KuantitasData = KuantitasTransaksi()
                KuantitasData.Quantity = lk.Quantity
                KuantitasData.Harga = lk.Harga
                product.Harga = lk.Harga
                KuantitasData.Total = lk.Total
                KuantitasData.IdDetailTransaksiData = lk.IdDetailTransaksiData
                ListKuantitas.add(KuantitasData)
            }

            newDetail.ListKuantitas = ListKuantitas
            newDetail.ProdukData = product

            detail.add(newDetail)
        }


        newData.Keterangan = this.Keterangan
        newData.ProductFlow = this.ProductFlow
        newData.SubTotal = this.SubTotal
        newData.TanggalTransaksi = this.TanggalTransaksi.CloneTanggal()
        newData.Jam = this.Jam.CloneTime()
        newData.IdTransaksiData = this.IdTransaksiData
        newData.ListDetail = detail

        return newData
    }
}