package com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.res

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.res.FindHowMuchGapBetweenTrans
import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.laporanKartuPersediaan.LaporanKartuPersediaanObj
import com.example.renosyahputra.kartupersediaan.res.obj.metode.MetodePersediaan
import com.example.renosyahputra.kartupersediaan.res.obj.persediaanData.PersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.FormatTanggal
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.KuantitasTransaksi
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData
import com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.KartuPersediaanMenu
import com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.res.old.GenerateDataForAverage
import com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.res.old.GenerateDataInventoryCard
import com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.res.old.ValidateOutProduct
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj

class FunctionForKartuPersediaanMenu {
    companion object {

        fun dialogTransNotValid(ctx : Context,lang : LangObj){

            AlertDialog.Builder(ctx).setTitle(lang.laporanMenuLang.WarningInvalidProduckInTransTitle)
                    .setMessage(lang.laporanMenuLang.WarningInvalidProduckInTrans)
                    .setIcon(R.drawable.warning)
                    .setPositiveButton(lang.mainMenuSettingLang.Back, DialogInterface.OnClickListener { dialogInterface, i ->
                        dialogInterface.dismiss()
                    })
                    .create()
                    .show()
        }

        fun CheckValidQuantityProductInAllTransaction(l : ArrayList<TransaksiData>) : Boolean {
            var isThisInValid = false
            for (t in l.listIterator()){
                for (d in t.ListDetail.listIterator()){
                    if (d.IsThisValidDetailTransaction ==  false || t.IsThisValidTransaction == false){
                        isThisInValid = true
                        break
                    }

                }
                if (isThisInValid){
                    break
                }
            }
            return isThisInValid
        }

        fun ModifyDataListKuantitasForFIFOAndLIFO(ExtraLoop : Int,MainData : KartuPersediaanData, duplicateListTransaksi :ArrayList<TransaksiData>){

            val ManyGap = FindHowMuchGapBetweenTrans.FindGap(duplicateListTransaksi)

            if (MainData.metodePersediaan.MetodeUse != MetodePersediaan.AVERAGE) {

                duplicateListTransaksi.clear()

                for (trs in MainData.ListTransaksiData.sortedWith(compareBy({ it.TanggalTransaksi.Tahun }, { it.TanggalTransaksi.Bulan }, { it.TanggalTransaksi.Hari }, { it.Jam.Jam }, { it.Jam.Menit }))){
                    if (trs.ProductFlow == TransaksiData.ProductIn){

                        duplicateListTransaksi.add(trs.CloneTransData())

                    }else if (trs.ProductFlow == TransaksiData.ProductOut) {

                        val ManyLoopMustDo = (ManyGap + FindHowMuchGapBetweenTrans.SizeDataPersediaan(MainData.ListPersediaanData) + ExtraLoop)

                        duplicateListTransaksi.add(trs.CloneTransData())
                        for (i in 0..ManyLoopMustDo) {
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
                if (filter.TanggalAwal != null && filter.TanggalAkhir != null && FormatTanggal.BandingkanTanggalPatokanDenganYangLebihKecil(dataTrans.TanggalTransaksi,filter.TanggalAkhir!!) && FormatTanggal.BandingkanTanggalPatokanDenganYangLebihBesar(dataTrans.TanggalTransaksi,filter.TanggalAwal!!)){
                    for (detail in dataTrans.ListDetail){
                        if (filter.p != null && (filter.p!!.IdProduk == detail.ProdukData.IdProduk)){
                            AppendToCartuPersediaan(LaporanKartuPersediaan,dataTrans.IdTransaksiData,dataTrans.TanggalTransaksi,detail.ProdukData,dataTrans.Keterangan,dataTrans.ProductFlow,detail.ListPersediaanData,detail.ListKuantitas)
                        }else if (filter.p == null){
                            AppendToCartuPersediaan(LaporanKartuPersediaan,dataTrans.IdTransaksiData,dataTrans.TanggalTransaksi,detail.ProdukData,dataTrans.Keterangan,dataTrans.ProductFlow,detail.ListPersediaanData,detail.ListKuantitas)
                        }

                    }
                }else if (filter.TanggalAwal == null && filter.TanggalAkhir == null){
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

            val Tanggal = FormatTanggal()
            Tanggal.Tahun = TanggalTransaksi.Tahun
            Tanggal.Hari = TanggalTransaksi.Hari
            Tanggal.Bulan = TanggalTransaksi.Bulan

            val Produk = ProdukData()
            Produk.IdProduk = ProdukData.IdProduk
            Produk.Harga = ProdukData.Harga
            Produk.Nama = ProdukData.Nama

            val ListKuantitas = ArrayList<KuantitasTransaksi>()
            for (data in listKuantitas){
                ListKuantitas.add(data.CloneKuantitas())
            }
            val Persediaan = ArrayList<PersediaanData>()
            for (data in ListPersediaanData){
                Persediaan.add(data)
            }
            laporanKartuPersediaanObj.IdTransaksiData = IdTransaksiData
            laporanKartuPersediaanObj.TanggalTransaksi = Tanggal
            laporanKartuPersediaanObj.ProdukData = Produk
            laporanKartuPersediaanObj.Keterangan = Keterangan
            laporanKartuPersediaanObj.ProductFlow = ProductFlow
            laporanKartuPersediaanObj.ListPersediaanData = Persediaan
            laporanKartuPersediaanObj.ListKuantitas = ListKuantitas
            LaporanKartuPersediaan.add(laporanKartuPersediaanObj)
        }

    }
}