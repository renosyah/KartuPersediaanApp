package com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.res.alternative

import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.laporanKartuPersediaan.LaporanKartuPersediaanObj
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.FormatTanggal
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData
import com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.KartuPersediaanMenu
import com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.res.FunctionForKartuPersediaanMenu

class FunctionForKartuPersediaanMenuForAlternative {
    companion object {

        fun FromTransactionListToKartuPersediaanListWithoutFilter(duplicateListTransaksi :ArrayList<TransaksiData>, LaporanKartuPersediaan : ArrayList<LaporanKartuPersediaanObj>){
            for (dataTrans in duplicateListTransaksi){
                for (detail in dataTrans.ListDetail){
                    FunctionForKartuPersediaanMenu.AppendToCartuPersediaan(LaporanKartuPersediaan, dataTrans.IdTransaksiData, dataTrans.TanggalTransaksi, detail.ProdukData, dataTrans.Keterangan, dataTrans.ProductFlow, detail.ListPersediaanData, detail.ListKuantitas)
                }
            }
        }

        fun CalculatingStockAndModifyListPersediaanDataAlternative(Maindata : KartuPersediaanData,Kartupersediaan : ArrayList<LaporanKartuPersediaanObj>){
            GenerateDataKartuPersediaanAternative.IsiSetiapDataKuantitasDanKartuPersediaan(Maindata,Kartupersediaan)
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