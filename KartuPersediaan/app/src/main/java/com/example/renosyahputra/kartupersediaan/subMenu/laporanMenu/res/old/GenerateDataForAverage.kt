package com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.res.old

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

                            data.SetTotalAll(data.ProdukData.Harga,data.GetKuantitas())
                            TotalHolder += data.GetTotalListKuantitas()
                            HargaHolder = TotalHolder / kartu.Jumlah

                            kartu.Total = TotalHolder
                            kartu.Produk.Harga = HargaHolder

                        }else if (data.ProductFlow == TransaksiData.ProductOut){


                            kartu.Produk.Harga = HargaHolder
                            kartu.Total = TotalHolder

                            data.ProdukData.Harga = HargaHolder
                            data.SetHargaAll(HargaHolder)
                            data.SetTotalAll(data.ProdukData.Harga,data.GetKuantitas())

                            TotalHolder -= data.GetTotalListKuantitas()
                            kartu.Total = TotalHolder
                        }

                    }

                }
            }

        }
    }
}