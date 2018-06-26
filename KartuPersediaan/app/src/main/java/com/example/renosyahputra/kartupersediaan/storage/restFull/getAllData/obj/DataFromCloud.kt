package com.example.renosyahputra.kartupersediaan.storage.restFull.getAllData.obj

import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData
import com.example.renosyahputra.kartupersediaan.res.obj.user.UserData
import java.io.Serializable

class DataFromCloud : Serializable {
    lateinit var User : UserData
    lateinit var Transaksi : ArrayList<TransaksiData>
    lateinit var Produk : ArrayList<ProdukData>
}