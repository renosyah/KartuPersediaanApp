package com.example.renosyahputra.kartupersediaan.subMenu.transaksiMenu.res

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.res.customAlertDialog.transaksi.ResFunction
import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj

class TransaksiMenuRes {
    companion object {


        fun SortTransactionDataByDateAndTime(MainData : KartuPersediaanData){
            val newArrayTrans = ArrayList<TransaksiData>()
            for (data in MainData.ListTransaksiData.sortedWith(compareBy({ it.TanggalTransaksi.Tahun }, { it.TanggalTransaksi.Bulan }, { it.TanggalTransaksi.Hari }, { it.Jam.Jam }, { it.Jam.Menit }))){
                newArrayTrans.add(data.CloneTransData())
            }
            MainData.ListTransaksiData = newArrayTrans
        }


        fun dialogTransNotValid(ctx : Context, lang: LangObj){

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

        fun CheckAndMarkTransactionWithNonValidQty(l: ArrayList<TransaksiData>) {
            for (t in l) {
                var IsThisValid = true

                for (d in t.ListDetail) {

                    val checkDuluQty = (ResFunction.GetTotalQtyProductFromAllTrans(t, d.ProdukData, l))
                    if (((checkDuluQty - d.GetKuantitas()) < 0 && t.ProductFlow == TransaksiData.ProductOut)) {
                        IsThisValid = false
                    }

                    d.IsThisValidDetailTransaction = IsThisValid

                    if (!IsThisValid) {
                        break
                    }

                }
                t.IsThisValidTransaction = IsThisValid
            }

        }
    }
}