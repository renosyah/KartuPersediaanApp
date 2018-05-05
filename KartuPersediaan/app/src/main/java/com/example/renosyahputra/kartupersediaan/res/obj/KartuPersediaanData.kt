package com.example.renosyahputra.kartupersediaan.res.obj

import com.example.renosyahputra.kartupersediaan.res.obj.metode.MetodePersediaan
import com.example.renosyahputra.kartupersediaan.res.obj.persediaanData.PersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData
import java.io.Serializable

class KartuPersediaanData : Serializable {
    lateinit var metodePersediaan  : MetodePersediaan
    lateinit var ListTransaksiData : ArrayList<TransaksiData>
    lateinit var ListProdukData : ArrayList<ProdukData>
    lateinit var ListPersediaanData : ArrayList<PersediaanData>

}