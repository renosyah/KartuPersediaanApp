package com.example.renosyahputra.kartupersediaan.res.obj.produkData

import java.io.Serializable

class ProdukData : Serializable {
    lateinit var IdProduk : String
    lateinit var Nama : String
    var Harga : Int = 0
}