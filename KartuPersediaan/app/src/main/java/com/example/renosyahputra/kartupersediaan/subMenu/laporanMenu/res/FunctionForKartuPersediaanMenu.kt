package com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.res

import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.laporanKartuPersediaan.LaporanKartuPersediaanObj
import com.example.renosyahputra.kartupersediaan.res.obj.metode.MetodePersediaan
import com.example.renosyahputra.kartupersediaan.res.obj.persediaanData.PersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.FormatTanggal
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.KuantitasTransaksi
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData
import com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.KartuPersediaanMenu

class FunctionForKartuPersediaanMenu {
    companion object {

        fun ModifyDataListKuantitasForFIFOAndLIFO(MainData : KartuPersediaanData, duplicateListTransaksi :ArrayList<TransaksiData>){


            val GapInTransaction = FindHowMuchGapBetweenTrans.FindGap(duplicateListTransaksi)


            if (MainData.metodePersediaan.MetodeUse != MetodePersediaan.AVERAGE) {

                duplicateListTransaksi.clear()

                for (trs in MainData.ListTransaksiData.sortedWith(compareBy({ it.TanggalTransaksi.Tahun }, { it.TanggalTransaksi.Bulan }, { it.TanggalTransaksi.Hari }, { it.Jam.Jam }, { it.Jam.Menit }))){
                    if (trs.ProductFlow == TransaksiData.ProductIn){

                        duplicateListTransaksi.add(trs.CloneTransData())


                    }else {

                        duplicateListTransaksi.add(trs.CloneTransData())
                        for (i in 0..GapInTransaction) {
                            for (dataTrans in duplicateListTransaksi.sortedWith(compareBy({ it.TanggalTransaksi.Tahun }, { it.TanggalTransaksi.Bulan }, { it.TanggalTransaksi.Hari }, { it.Jam.Jam }, { it.Jam.Menit }))) {
                                ValidateOutProduct.GenerateForEachTransaction(MainData, duplicateListTransaksi, dataTrans)
                            }

                            MainData.ListPersediaanData.clear()
                        }

                    }
                }
            }
        }

        fun CalculatingStockAndModifyListPersediaanData(MainData : KartuPersediaanData, duplicateListTransaksi :ArrayList<TransaksiData>){

            for (dataTrans in duplicateListTransaksi.sortedWith(compareBy({ it.TanggalTransaksi.Tahun }, { it.TanggalTransaksi.Bulan }, { it.TanggalTransaksi.Hari }, { it.Jam.Jam }, { it.Jam.Menit }))) {
                GenerateDataInventoryCard.GenerateForEachTransaction(MainData, dataTrans)
            }

        }


        fun FillEmptyVariabelForAVERAGE(MainData: KartuPersediaanData,LaporanKartuPersediaan : ArrayList<LaporanKartuPersediaanObj>){
            if (MainData.metodePersediaan.MetodeUse == MetodePersediaan.AVERAGE) {
                for (p in MainData.ListProdukData) {
                    GenerateDataForAverage.FillZeroNumber(p, LaporanKartuPersediaan)
                }
            }

        }



        fun FromTransactionListToKartuPersediaanList(filter : KartuPersediaanMenu.FilterCard, duplicateListTransaksi :ArrayList<TransaksiData>,LaporanKartuPersediaan : ArrayList<LaporanKartuPersediaanObj>){
            for (dataTrans in duplicateListTransaksi.sortedWith(compareBy({it.TanggalTransaksi.Tahun},{it.TanggalTransaksi.Bulan},{it.TanggalTransaksi.Hari},{it.Jam.Jam},{it.Jam.Menit}))){
                if (filter.tahun > 0 && (dataTrans.TanggalTransaksi.Tahun == filter.tahun)){
                    for (detail in dataTrans.ListDetail){
                        if (filter.p != null && (filter.p!!.IdProduk == detail.ProdukData.IdProduk)){
                            AppendToCartuPersediaan(LaporanKartuPersediaan,dataTrans.IdTransaksiData,dataTrans.TanggalTransaksi,detail.ProdukData,dataTrans.Keterangan,dataTrans.ProductFlow,detail.ListPersediaanData,detail.ListKuantitas)
                        }else if (filter.p == null){
                            AppendToCartuPersediaan(LaporanKartuPersediaan,dataTrans.IdTransaksiData,dataTrans.TanggalTransaksi,detail.ProdukData,dataTrans.Keterangan,dataTrans.ProductFlow,detail.ListPersediaanData,detail.ListKuantitas)
                        }

                    }
                }else if (filter.tahun == 0){
                    for (detail in dataTrans.ListDetail){
                        if (filter.p != null && (filter.p!!.IdProduk == detail.ProdukData.IdProduk)){
                            AppendToCartuPersediaan(LaporanKartuPersediaan,dataTrans.IdTransaksiData,dataTrans.TanggalTransaksi,detail.ProdukData,dataTrans.Keterangan,dataTrans.ProductFlow,detail.ListPersediaanData,detail.ListKuantitas)
                        }else if (filter.p == null){
                            AppendToCartuPersediaan(LaporanKartuPersediaan,dataTrans.IdTransaksiData,dataTrans.TanggalTransaksi,detail.ProdukData,dataTrans.Keterangan,dataTrans.ProductFlow,detail.ListPersediaanData,detail.ListKuantitas)
                        }
                    }
                }
            }
        }


        internal fun AppendToCartuPersediaan(LaporanKartuPersediaan : ArrayList<LaporanKartuPersediaanObj>,IdTransaksiData : String, TanggalTransaksi : FormatTanggal, ProdukData : ProdukData, Keterangan : String, ProductFlow :String, ListPersediaanData : ArrayList<PersediaanData>, listKuantitas : ArrayList<KuantitasTransaksi>){
            val laporanKartuPersediaanObj = LaporanKartuPersediaanObj()
            laporanKartuPersediaanObj.IdTransaksiData = IdTransaksiData
            laporanKartuPersediaanObj.TanggalTransaksi = TanggalTransaksi
            laporanKartuPersediaanObj.ProdukData = ProdukData
            laporanKartuPersediaanObj.Keterangan = Keterangan
            laporanKartuPersediaanObj.ProductFlow = ProductFlow
            laporanKartuPersediaanObj.ListPersediaanData = ListPersediaanData
            laporanKartuPersediaanObj.ListKuantitas = listKuantitas
            LaporanKartuPersediaan.add(laporanKartuPersediaanObj)
        }

    }
}