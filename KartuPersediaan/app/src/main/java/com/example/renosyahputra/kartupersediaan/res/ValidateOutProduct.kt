package com.example.renosyahputra.kartupersediaan.res

import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.metode.MetodePersediaan
import com.example.renosyahputra.kartupersediaan.res.obj.persediaanData.PersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.DetailTransaksi
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData

class ValidateOutProduct {



    companion object {

        fun InventoryFifoLifoMethod(TransData : TransaksiData,detail : DetailTransaksi,product: ProdukData, s: ArrayList<PersediaanData>,qty : Int,transData  : ArrayList<TransaksiData>): ArrayList<PersediaanData> {

            var qtyHolder = qty
            val newS = RefreshClone(s)
            var addNumber = 1


            for (dt in newS) {

                if (dt.Produk.IdProduk == product.IdProduk) {

                    if (dt.Jumlah - qtyHolder < 0 && dt.Jumlah > 0 && dt.Jumlah != qtyHolder) {

                            addNumber++

                            val id = IdGenerator()
                            id.CreateRandomString(15)

                            val clone = TransData.CloneTransData()
                            clone.Jam.Menit += addNumber
                            clone.IdTransaksiData += "Clone" + id.GetId()
                            clone.ListDetail.clear()

                            val newDetail = DetailTransaksi()
                            val Cloneproduct = ProdukData()

                            Cloneproduct.IdProduk = product.IdProduk
                            Cloneproduct.Nama = product.Nama
                            Cloneproduct.Harga = product.Harga

                            newDetail.IdTransaksiData = clone.IdTransaksiData
                            newDetail.ProdukData = Cloneproduct
                            newDetail.ListPersediaanData = ArrayList()

                            newDetail.Quantity = qtyHolder - dt.Jumlah
                            newDetail.Total = (newDetail.Quantity * newDetail.ProdukData.Harga)
                            clone.ListDetail.add(newDetail)

                            transData.add(clone)

                            detail.Quantity = dt.Jumlah
                            detail.ProdukData.Harga = dt.Produk.Harga

                            dt.Jumlah = 0

                            break

                    }

                    if (dt.Jumlah > 0 && dt.Jumlah == qtyHolder){

                        detail.ProdukData.Harga = dt.Produk.Harga
                        qtyHolder -= dt.Jumlah
                        dt.Jumlah = 0

                        break

                    }else if (dt.Jumlah - qtyHolder> 0) {

                        detail.ProdukData.Harga = dt.Produk.Harga
                        dt.Jumlah -= qtyHolder

                        break

                    }else if (dt.Jumlah - qtyHolder>= 0) {

                        detail.ProdukData.Harga = dt.Produk.Harga
                        dt.Jumlah -= qtyHolder

                        break
                    }


                }

            }

            val newDs = ArrayList<PersediaanData>()
            for (d in newS) {
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

        fun RefreshClone( s: ArrayList<PersediaanData>) : ArrayList<PersediaanData> {
            val clone = ArrayList<PersediaanData>()


            for (i in 0..(s.size)-1){

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

                clone.add(stk)
            }
            return clone
        }


        fun GenerateForEachTransaction(Maindata: KartuPersediaanData,transData : ArrayList<TransaksiData>, item: TransaksiData) {

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


                    Maindata.ListPersediaanData = InventoryFifoLifoMethod(item, itemInDetail, itemInDetail.ProdukData, Maindata.ListPersediaanData, itemInDetail.Quantity, transData)

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