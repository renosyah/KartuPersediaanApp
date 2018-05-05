package com.example.renosyahputra.kartupersediaan.res

import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.metode.MetodePersediaan
import com.example.renosyahputra.kartupersediaan.res.obj.persediaanData.PersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData

class GenerateDataInventoryCard {
    companion object {

        fun InventoryFifoLifoMethod(product: ProdukData, s: ArrayList<PersediaanData>, qty: Int): ArrayList<PersediaanData> {

            var qtyHolder = qty
            val newDs = ArrayList<PersediaanData>()

            for (pos in 0..(s.size) - 1) {
                val dt = s.get(pos)
                if (dt.Produk.IdProduk == product.IdProduk) {
                    if (dt.Jumlah - qtyHolder < 0) {

                        qtyHolder = qtyHolder - dt.Jumlah
                        s.get(pos).Jumlah = 0

                    } else if (dt.Jumlah - qtyHolder > 0) {

                        s.get(pos).Jumlah = dt.Jumlah - qtyHolder

                        break
                    } else if (dt.Jumlah - qtyHolder >= 0) {

                        s.get(pos).Jumlah = dt.Jumlah - qtyHolder

                        break
                    }
                }

            }
            for (d in s) {
                val stk = PersediaanData()
                stk.Produk = ProdukData()

                stk.TanggalMasuk.Hari = d.TanggalMasuk.Hari
                stk.TanggalMasuk.Bulan = d.TanggalMasuk.Bulan
                stk.TanggalMasuk.Tahun = d.TanggalMasuk.Tahun
                stk.Jumlah = d.Jumlah
                stk.Produk.Harga = d.Produk.Harga
                stk.Produk.Nama = d.Produk.Nama
                stk.Produk.IdProduk = d.Produk.IdProduk
                stk.Total = d.Total

                newDs.add(stk)
            }

            return newDs

        }


        fun ReverseData(s: ArrayList<PersediaanData>): ArrayList<PersediaanData> {

            val newDs = ArrayList<PersediaanData>()

            for (i in (s.size - 1) downTo 0) {

                val stk = PersediaanData()
                stk.Produk = ProdukData()

                stk.TanggalMasuk.Hari = s.get(i).TanggalMasuk.Hari
                stk.TanggalMasuk.Bulan = s.get(i).TanggalMasuk.Bulan
                stk.TanggalMasuk.Tahun = s.get(i).TanggalMasuk.Tahun
                stk.Jumlah = s.get(i).Jumlah
                stk.Produk.Harga = s.get(i).Produk.Harga
                stk.Produk.Nama = s.get(i).Produk.Nama
                stk.Produk.IdProduk = s.get(i).Produk.IdProduk
                stk.Total = s.get(i).Total

                newDs.add(stk)

            }

            return newDs
        }


        fun SetToOne(product: ProdukData,s: ArrayList<PersediaanData>): ArrayList<PersediaanData> {

            val newDs = ArrayList<PersediaanData>()
            val LastData = PersediaanData()
            LastData.Produk = ProdukData()

            var CollectAllPrice = 0
            var ukuranArray = 0

            for (data in s) {

                if (data.Produk.IdProduk == product.IdProduk){

                    LastData.TanggalMasuk.Hari = data.TanggalMasuk.Hari
                    LastData.TanggalMasuk.Bulan = data.TanggalMasuk.Bulan
                    LastData.TanggalMasuk.Tahun = data.TanggalMasuk.Tahun

                    LastData.Jumlah += data.Jumlah
                    CollectAllPrice += data.Produk.Harga
                    ukuranArray++
                }
            }

            LastData.Produk.IdProduk = product.IdProduk
            LastData.Produk.Nama = product.Nama
            LastData.Produk.Harga = (CollectAllPrice.toDouble() / ukuranArray).toInt()
            LastData.Total = (LastData.Jumlah * LastData.Produk.Harga)

            newDs.add(LastData)
            return newDs
        }


        fun GenerateForEachTransaction(Maindata: KartuPersediaanData, item: TransaksiData) {

            for (itemInDetail in item.ListDetail){

                if (item.ProductFlow == TransaksiData.ProductIn) {

                    val newStock = PersediaanData()
                    newStock.Produk = ProdukData()
                    newStock.Produk.IdProduk = itemInDetail.ProdukData.IdProduk
                    newStock.Produk.Nama = itemInDetail.ProdukData.Nama
                    newStock.Produk.Harga = itemInDetail.ProdukData.Harga
                    newStock.Jumlah = itemInDetail.Quantity
                    newStock.TanggalMasuk.Hari = item.TanggalTransaksi.Hari
                    newStock.TanggalMasuk.Bulan = item.TanggalTransaksi.Bulan
                    newStock.TanggalMasuk.Tahun = item.TanggalTransaksi.Tahun
                    newStock.Total = itemInDetail.Total

                    Maindata.ListPersediaanData.add(newStock)



                } else if (item.ProductFlow == TransaksiData.ProductOut) {

                    if (Maindata.metodePersediaan.MetodeUse == MetodePersediaan.LIFO) {
                        Maindata.ListPersediaanData = ReverseData(Maindata.ListPersediaanData)
                    }


                    Maindata.ListPersediaanData = InventoryFifoLifoMethod(itemInDetail.ProdukData, Maindata.ListPersediaanData, itemInDetail.Quantity)


                    if (Maindata.metodePersediaan.MetodeUse == MetodePersediaan.LIFO) {
                        Maindata.ListPersediaanData = ReverseData(Maindata.ListPersediaanData)
                    }
                }

                var newStock = ArrayList<PersediaanData>()

                for (s in Maindata.ListPersediaanData) {

                    if (s.Produk.IdProduk == itemInDetail.ProdukData.IdProduk) {
                        val stk = PersediaanData()
                        stk.Produk = ProdukData()

                        stk.TanggalMasuk.Hari = s.TanggalMasuk.Hari
                        stk.TanggalMasuk.Bulan = s.TanggalMasuk.Bulan
                        stk.TanggalMasuk.Tahun = s.TanggalMasuk.Tahun
                        stk.Jumlah = s.Jumlah
                        stk.Produk.Harga = s.Produk.Harga
                        stk.Produk.Nama = s.Produk.Nama
                        stk.Produk.IdProduk = s.Produk.IdProduk
                        stk.Total = s.Total

                        newStock.add(stk)

                    }

                    if (Maindata.metodePersediaan.MetodeUse == MetodePersediaan.AVERAGE) {
                        val holder = SetToOne(itemInDetail.ProdukData,newStock)
                        newStock = holder
                    }

                    val intPosWhenNolFound = ArrayList<Int>()
                    for (finNol in 0..(newStock.size)-1){
                        if (newStock.get(finNol).Jumlah == 0){
                            intPosWhenNolFound.add(finNol)
                        }
                    }

                    for (getRidNol in 0..(intPosWhenNolFound.size)-1){
                        newStock.removeAt(intPosWhenNolFound.get(getRidNol))
                    }
                    itemInDetail.ListPersediaanData = newStock
                }

            }
        }

    }
}