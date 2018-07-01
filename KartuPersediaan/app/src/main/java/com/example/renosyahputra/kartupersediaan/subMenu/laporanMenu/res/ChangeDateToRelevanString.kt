package com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.res

import android.content.Context
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.FormatTanggal
import com.example.renosyahputra.kartupersediaan.ui.lang.LangSetting
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj

class ChangeDateToRelevanString(ctx : Context,lang : LangObj){
    val l = lang
    val context = ctx

   companion object {
       val yyyyMMdd = "yyyyMMdd"
       val ddMMyyyy = "ddMMyyyy"
       val MMddyyyy = "MMddyyyy"
   }

    fun SetAndGetFormatSimple(d : FormatTanggal,firstCharSeperate : String,secondCharSeperate  :String) : String{
        var date = ""

        var format = ddMMyyyy

        when (LangSetting.load_data_for_public("Langsetting.txt",context)){
            LangSetting.SetInglisLang -> {
                format = MMddyyyy
            }

            LangSetting.SetIdoLang -> {
                format = ddMMyyyy
            }

        }


        when (format) {
            yyyyMMdd -> {
                date = d.Tahun.toString() + firstCharSeperate + d.Bulan.toString() + secondCharSeperate + d.Hari.toString()
            }

            ddMMyyyy -> {
                date = d.Hari.toString() + firstCharSeperate + d.Bulan.toString() + secondCharSeperate + d.Tahun.toString()
            }

            MMddyyyy -> {
                date = d.Bulan.toString() + firstCharSeperate + d.Hari.toString() + secondCharSeperate +  d.Tahun.toString()
            }
        }
        return date
    }

    fun SetAndGetFormat(d : FormatTanggal,firstCharSeperate : String,secondCharSeperate  :String) : String{
        var date = ""
        var montInString = ""

        when (d.Bulan){
            1 -> {
                montInString = l.monthInString.Januari
            }
            2 -> {
                montInString = l.monthInString.Februari
            }
            3 -> {
                montInString = l.monthInString.Maret
            }
            4 -> {
                montInString = l.monthInString.April
            }
            5 -> {
                montInString = l.monthInString.Mei
            }
            6 -> {
                montInString = l.monthInString.Juni
            }
            7 -> {
                montInString = l.monthInString.Juli
            }
            8 -> {
                montInString = l.monthInString.Agustus
            }
            9 -> {
                montInString = l.monthInString.September
            }
            10 -> {
                montInString = l.monthInString.Oktober
            }
            11 -> {
                montInString = l.monthInString.November
            }
            12 -> {
                montInString = l.monthInString.Desember
            }
        }

        var format = ddMMyyyy

        when (LangSetting.load_data_for_public("Langsetting.txt",context)){
            LangSetting.SetInglisLang -> {
                format = MMddyyyy
            }

            LangSetting.SetIdoLang -> {
                format = ddMMyyyy
            }
        }


        when (format) {
            yyyyMMdd -> {
                date = d.Tahun.toString() + firstCharSeperate + montInString + secondCharSeperate + d.Hari.toString()
            }

            ddMMyyyy -> {
                date = d.Hari.toString() + firstCharSeperate + montInString + secondCharSeperate + d.Tahun.toString()
            }

            MMddyyyy -> {
                date = montInString + firstCharSeperate + d.Hari.toString() + secondCharSeperate +  d.Tahun.toString()
            }
        }
        return date
    }
}