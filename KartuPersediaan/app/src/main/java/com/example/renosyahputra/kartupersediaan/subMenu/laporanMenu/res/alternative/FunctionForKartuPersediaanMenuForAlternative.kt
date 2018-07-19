package com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.res.alternative

import com.example.renosyahputra.kartupersediaan.res.FindHowMuchGapBetweenTrans
import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.laporanKartuPersediaan.LaporanKartuPersediaanObj
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.FormatTanggal
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData
import com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.KartuPersediaanMenu
import com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.res.old.FunctionForKartuPersediaanMenu

class FunctionForKartuPersediaanMenuForAlternative {
    companion object {

        fun FromTransactionListToKartuPersediaanListWithoutFilter(ExtraLoop :Int, Maindata : KartuPersediaanData,duplicateListTransaksi :ArrayList<TransaksiData>, LaporanKartuPersediaan : ArrayList<LaporanKartuPersediaanObj>){
            val HolderLaporan =  ArrayList<LaporanKartuPersediaanObj>()

            for (dataTrans in duplicateListTransaksi){
                for (detail in dataTrans.ListDetail){
                    FunctionForKartuPersediaanMenu.AppendToCartuPersediaan(HolderLaporan, dataTrans.IdTransaksiData, dataTrans.TanggalTransaksi, detail.ProdukData, dataTrans.Keterangan, dataTrans.ProductFlow, detail.ListPersediaanData, detail.ListKuantitas)
                }
            }

            val ManyGap = FindHowMuchGapBetweenTrans.FindGap(duplicateListTransaksi)
            LaporanKartuPersediaan.clear()

            for (EachLaporan in HolderLaporan){
                if (EachLaporan.ProductFlow == TransaksiData.ProductIn){
                    FunctionForKartuPersediaanMenu.AppendToCartuPersediaan(LaporanKartuPersediaan, EachLaporan.IdTransaksiData, EachLaporan.TanggalTransaksi, EachLaporan.ProdukData, EachLaporan.Keterangan,EachLaporan.ProductFlow, EachLaporan.ListPersediaanData,EachLaporan.ListKuantitas)

                }else if (EachLaporan.ProductFlow == TransaksiData.ProductOut){

                    val ManyLoopMustDo = (ManyGap + FindHowMuchGapBetweenTrans.SizeDataPersediaan(Maindata.ListPersediaanData) + ExtraLoop)

                    FunctionForKartuPersediaanMenu.AppendToCartuPersediaan(LaporanKartuPersediaan, EachLaporan.IdTransaksiData, EachLaporan.TanggalTransaksi, EachLaporan.ProdukData, EachLaporan.Keterangan,EachLaporan.ProductFlow, EachLaporan.ListPersediaanData,EachLaporan.ListKuantitas)

                    for (i in 0..ManyLoopMustDo){
                        GenerateDataKartuPersediaanAternative.IsiSetiapDataKuantitasDanKartuPersediaan(true,Maindata,LaporanKartuPersediaan)
                        Maindata.ListPersediaanData.clear()
                    }
                }
            }

            HolderLaporan.clear()

        }

        fun CalculatingStockAndModifyListPersediaanDataAlternative(Maindata : KartuPersediaanData,LaporanKartuPersediaan : ArrayList<LaporanKartuPersediaanObj>){
            GenerateDataKartuPersediaanAternative.IsiSetiapDataKuantitasDanKartuPersediaan(false,Maindata,LaporanKartuPersediaan)
        }


        fun FromKartuPersediaanListToKartuPersediaanListByFilter(filter : KartuPersediaanMenu.FilterCard, OldKartuPersediaanList :ArrayList<LaporanKartuPersediaanObj>){
            val NewLaporanKartuPersediaan =  ArrayList<LaporanKartuPersediaanObj>()
            for (dataOld in OldKartuPersediaanList) {
                if (filter.TanggalAwal != null && filter.TanggalAkhir != null && FormatTanggal.BandingkanTanggalPatokanDenganYangLebihKecil(dataOld.TanggalTransaksi, filter.TanggalAkhir!!) && FormatTanggal.BandingkanTanggalPatokanDenganYangLebihBesar(dataOld.TanggalTransaksi, filter.TanggalAwal!!)) {

                    if (filter.p != null && (filter.p!!.IdProduk == dataOld.ProdukData.IdProduk)) {
                        FunctionForKartuPersediaanMenu.AppendToCartuPersediaan(NewLaporanKartuPersediaan, dataOld.IdTransaksiData, dataOld.TanggalTransaksi, dataOld.ProdukData, dataOld.Keterangan, dataOld.ProductFlow, dataOld.ListPersediaanData, dataOld.ListKuantitas)
                    } else if (filter.p == null) {
                        FunctionForKartuPersediaanMenu.AppendToCartuPersediaan(NewLaporanKartuPersediaan, dataOld.IdTransaksiData, dataOld.TanggalTransaksi, dataOld.ProdukData, dataOld.Keterangan, dataOld.ProductFlow, dataOld.ListPersediaanData, dataOld.ListKuantitas)
                    }

                } else if (filter.TanggalAwal == null && filter.TanggalAkhir == null) {

                    if (filter.p != null && (filter.p!!.IdProduk == dataOld.ProdukData.IdProduk)) {
                        FunctionForKartuPersediaanMenu.AppendToCartuPersediaan(NewLaporanKartuPersediaan, dataOld.IdTransaksiData, dataOld.TanggalTransaksi, dataOld.ProdukData, dataOld.Keterangan, dataOld.ProductFlow, dataOld.ListPersediaanData, dataOld.ListKuantitas)
                    } else if (filter.p == null) {
                        FunctionForKartuPersediaanMenu.AppendToCartuPersediaan(NewLaporanKartuPersediaan, dataOld.IdTransaksiData, dataOld.TanggalTransaksi, dataOld.ProdukData, dataOld.Keterangan, dataOld.ProductFlow, dataOld.ListPersediaanData, dataOld.ListKuantitas)
                    }
                }
            }
            OldKartuPersediaanList.clear()
            for (data in NewLaporanKartuPersediaan){
                OldKartuPersediaanList.add(data.CloneLaporanKartuPersediaan())
            }
        }
    }
}