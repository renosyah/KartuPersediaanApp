package com.example.renosyahputra.kartupersediaan.res

import com.example.renosyahputra.kartupersediaan.res.obj.laporanKartuPersediaan.LaporanKartuPersediaanObj
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData

class GenerateDataForAverage{
    companion object {
        fun FillZeroNumber(p :ProdukData,item : ArrayList<LaporanKartuPersediaanObj>){
            var TotalHolder = 0
            var HargaHolder = 0

            for (data in item){
                if (p.IdProduk == data.ProdukData.IdProduk){



                    for (kartu in data.ListPersediaanData){

                        if (data.ProductFlow == TransaksiData.ProductIn) {

                            data.Total = data.ProdukData.Harga * data.Quantity
                            TotalHolder += data.Total
                            HargaHolder = TotalHolder / kartu.Jumlah

                            kartu.Total = TotalHolder
                            kartu.Produk.Harga = HargaHolder

                        }else if (data.ProductFlow == TransaksiData.ProductOut){


                            kartu.Produk.Harga = HargaHolder
                            kartu.Total = TotalHolder

                            data.ProdukData.Harga = HargaHolder
                            data.Total = data.ProdukData.Harga * data.Quantity

                            TotalHolder -= data.Total
                            kartu.Total = TotalHolder
                        }

                    }

                }
            }

        }
    }
}