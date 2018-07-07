package com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.res.alternative

import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.laporanKartuPersediaan.LaporanKartuPersediaanObj
import com.example.renosyahputra.kartupersediaan.res.obj.metode.MetodePersediaan
import com.example.renosyahputra.kartupersediaan.res.obj.persediaanData.PersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData
import com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.res.old.GenerateDataInventoryCard

class GenerateDataKartuPersediaanAternative {
    companion object {

        fun TentukanBerapaBanyakListKuantitas(kartu : LaporanKartuPersediaanObj,MainData: KartuPersediaanData){


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


        fun IsiSetiapDataKuantitasDanKartuPersediaan(Maindata : KartuPersediaanData,LaporanKartupersediaan : ArrayList<LaporanKartuPersediaanObj>){

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


                    TentukanBerapaBanyakListKuantitas(EachKartu, Maindata)



                    if (Maindata.metodePersediaan.MetodeUse == MetodePersediaan.LIFO) {
                        Maindata.ListPersediaanData = GenerateDataInventoryCard.ReverseData(Maindata.ListPersediaanData)
                    }

                }
                var newStock = ArrayList<PersediaanData>()

                for (s in Maindata.ListPersediaanData) {

                    if (s.Produk.IdProduk == EachKartu.ProdukData.IdProduk) {
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
                }



                if (Maindata.metodePersediaan.MetodeUse == MetodePersediaan.AVERAGE) {
                    val holder =  GenerateDataKartuPersediaanAternative.JadikanSatuUntukAVERAGE(EachKartu.ProductFlow, EachKartu, EachKartu.ProdukData, newStock)
                    newStock = holder
                }

                EachKartu.ListPersediaanData = newStock
                
            }
        }
    }
}