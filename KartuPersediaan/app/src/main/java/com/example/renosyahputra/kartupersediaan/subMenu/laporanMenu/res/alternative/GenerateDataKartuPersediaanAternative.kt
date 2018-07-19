package com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.res.alternative

import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.laporanKartuPersediaan.LaporanKartuPersediaanObj
import com.example.renosyahputra.kartupersediaan.res.obj.metode.MetodePersediaan
import com.example.renosyahputra.kartupersediaan.res.obj.persediaanData.PersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData
import com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.res.old.GenerateDataInventoryCard
import com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.res.old.ValidateOutProduct

class GenerateDataKartuPersediaanAternative {
    companion object {

        fun TentukanJumlahStok(kartu : LaporanKartuPersediaanObj,Persediaan  :ArrayList<PersediaanData>) :ArrayList<PersediaanData> {

            var qtyHolder = kartu.GetKuantitas()

            for (pos in 0..(Persediaan.size) - 1) {
                val dt = Persediaan.get(pos)
                if (dt.Produk.IdProduk == kartu.ProdukData.IdProduk) {

                    if (dt.Jumlah - qtyHolder < 0) {

                        qtyHolder = qtyHolder - dt.Jumlah
                        Persediaan.get(pos).Jumlah = 0

                    } else if (dt.Jumlah - qtyHolder > 0) {

                        Persediaan.get(pos).Jumlah = dt.Jumlah - qtyHolder

                        break
                    } else if (dt.Jumlah - qtyHolder >= 0) {

                        Persediaan.get(pos).Jumlah = dt.Jumlah - qtyHolder

                        break
                    }
                }

            }

            return ValidateOutProduct.RefreshClone(Persediaan)
        }

        fun TentukanJumlahKuantitas(kartu : LaporanKartuPersediaanObj,Persediaan  :ArrayList<PersediaanData>) :ArrayList<PersediaanData> {


            for (detailKuantitas in kartu.ListKuantitas) {

                var qtyHolder = detailKuantitas.Quantity


                for (dt in Persediaan) {

                    if (dt.Produk.IdProduk == kartu.ProdukData.IdProduk) {

                        if (dt.Jumlah - qtyHolder < 0 && dt.Jumlah > 0 && dt.Jumlah != qtyHolder) {


                            val clone = detailKuantitas.CloneKuantitas()
                            clone.Quantity = qtyHolder - dt.Jumlah
                            clone.Harga = dt.Produk.Harga
                            clone.Total = clone.Harga * clone.Quantity
                            kartu.ListKuantitas.add(clone)

                            detailKuantitas.Quantity = dt.Jumlah
                            detailKuantitas.Harga = dt.Produk.Harga
                            detailKuantitas.Total = detailKuantitas.Harga * detailKuantitas.Quantity

                            dt.Jumlah = 0

                            break

                        }

                        if (dt.Jumlah > 0 && dt.Jumlah == qtyHolder) {

                            kartu.ProdukData.Harga = dt.Produk.Harga
                            detailKuantitas.Harga = dt.Produk.Harga
                            detailKuantitas.Total = detailKuantitas.Harga * detailKuantitas.Quantity

                            qtyHolder -= dt.Jumlah
                            dt.Jumlah = 0

                            break

                        } else if (dt.Jumlah - qtyHolder > 0) {

                            kartu.ProdukData.Harga = dt.Produk.Harga
                            detailKuantitas.Harga = dt.Produk.Harga
                            detailKuantitas.Total = detailKuantitas.Harga * detailKuantitas.Quantity

                            dt.Jumlah -= qtyHolder

                            break

                        } else if (dt.Jumlah - qtyHolder >= 0) {

                            kartu.ProdukData.Harga = dt.Produk.Harga
                            detailKuantitas.Harga = dt.Produk.Harga
                            detailKuantitas.Total = detailKuantitas.Harga * detailKuantitas.Quantity

                            dt.Jumlah -= qtyHolder

                            break
                        }

                    }
                }
            }

            return ValidateOutProduct.RefreshClone(Persediaan)

        }

        fun JadikanSatuUntukAVERAGE(flow :String,item : LaporanKartuPersediaanObj, product: ProdukData, s: ArrayList<PersediaanData>): ArrayList<PersediaanData> {

            val newDs = ArrayList<PersediaanData>()
            val LastData = PersediaanData()
            LastData.Produk = ProdukData()


            for (data in s) {

                if (data.Produk.IdProduk == product.IdProduk){

                    LastData.TanggalMasuk.Hari = data.TanggalMasuk.Hari
                    LastData.TanggalMasuk.Bulan = data.TanggalMasuk.Bulan
                    LastData.TanggalMasuk.Tahun = data.TanggalMasuk.Tahun

                    LastData.Produk.Harga = 0
                    LastData.Jumlah += data.Jumlah
                    LastData.Total = 0

                }
            }

            if (flow == TransaksiData.ProductOut) {

                item.ProdukData.Harga = 0
            }

            LastData.Produk.IdProduk = product.IdProduk
            LastData.Produk.Nama = product.Nama

            newDs.add(LastData)

            return newDs
        }


        fun IsiSetiapDataKuantitasDanKartuPersediaan(ApakahIniMenentukanKuantitas : Boolean, Maindata: KartuPersediaanData,LaporanKartupersediaan : ArrayList<LaporanKartuPersediaanObj>){

            for (EachKartu in LaporanKartupersediaan){

                if (Maindata.metodePersediaan.MetodeUse != MetodePersediaan.AVERAGE){
                    Maindata.ListPersediaanData = GenerateDataInventoryCard.RefreshListPersediaan(Maindata.ListPersediaanData)
                }

                if (EachKartu.ProductFlow == TransaksiData.ProductIn){

                    val newStock = PersediaanData()
                    newStock.Produk = ProdukData()
                    newStock.Produk.IdProduk = EachKartu.ProdukData.IdProduk
                    newStock.Produk.Nama = EachKartu.ProdukData.Nama
                    newStock.Produk.Harga = EachKartu.ProdukData.Harga
                    newStock.Jumlah = EachKartu.GetKuantitas()
                    newStock.TanggalMasuk.Hari = EachKartu.TanggalTransaksi.Hari
                    newStock.TanggalMasuk.Bulan = EachKartu.TanggalTransaksi.Bulan
                    newStock.TanggalMasuk.Tahun = EachKartu.TanggalTransaksi.Tahun
                    newStock.Total = EachKartu.GetTotalListKuantitas()
                    Maindata.ListPersediaanData.add(newStock)
                    
                }else if (EachKartu.ProductFlow == TransaksiData.ProductOut){

                    if (Maindata.metodePersediaan.MetodeUse == MetodePersediaan.LIFO) {
                        Maindata.ListPersediaanData = GenerateDataInventoryCard.ReverseData(Maindata.ListPersediaanData)
                    }


                    if (ApakahIniMenentukanKuantitas && Maindata.metodePersediaan.MetodeUse != MetodePersediaan.AVERAGE){

                        Maindata.ListPersediaanData = TentukanJumlahKuantitas(EachKartu,Maindata.ListPersediaanData)

                    }else if (!ApakahIniMenentukanKuantitas) {

                        Maindata.ListPersediaanData = TentukanJumlahStok(EachKartu, Maindata.ListPersediaanData)

                    }

                    if (Maindata.metodePersediaan.MetodeUse == MetodePersediaan.LIFO) {
                        Maindata.ListPersediaanData = GenerateDataInventoryCard.ReverseData(Maindata.ListPersediaanData)
                    }

                }


                var newStock = ValidateOutProduct.RefreshCloneByProduk(EachKartu.ProdukData,Maindata.ListPersediaanData)

                if (Maindata.metodePersediaan.MetodeUse == MetodePersediaan.AVERAGE) {
                    val holder =  GenerateDataKartuPersediaanAternative.JadikanSatuUntukAVERAGE(EachKartu.ProductFlow, EachKartu, EachKartu.ProdukData, newStock)
                    newStock = holder
                }

                EachKartu.ListPersediaanData = newStock
                
            }
        }
    }
}