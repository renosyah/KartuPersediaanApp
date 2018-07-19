package com.example.renosyahputra.kartupersediaan.storage.local

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Environment
import com.example.renosyahputra.kartupersediaan.res.ChangeDateToRelevanString
import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.laporanKartuPersediaan.LaporanKartuPersediaanObj
import com.example.renosyahputra.kartupersediaan.res.obj.metode.MetodePersediaan
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.FormatTanggal
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData
import com.example.renosyahputra.kartupersediaan.res.obj.user.UserData
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.itextpdf.text.Document
import com.itextpdf.text.PageSize
import com.itextpdf.text.html.simpleparser.HTMLWorker
import com.itextpdf.text.pdf.PdfWriter
import java.io.*
import java.text.DecimalFormat
import java.util.*


class SaveMainData(context: Context,MainData : KartuPersediaanData){
    val filename = "dataKartuPersediaan.data"
    val ctx = context
    val data = MainData

    fun Save() : Boolean {

        var save = false
        try {

            val fos = ctx.openFileOutput(filename, Context.MODE_PRIVATE)
            val os = ObjectOutputStream(fos)
            os.writeObject(data)
            os.close()
            fos.close()

            save = true

        }catch (e : IOException){
            e.printStackTrace()
        }

        return save
    }



    fun Load() : KartuPersediaanData? {

        var data : KartuPersediaanData? = null

        try {
            val fis = ctx.openFileInput(filename)
            val file = ObjectInputStream(fis)
            data = file.readObject() as KartuPersediaanData
            file.close()
            fis.close()

        }catch (e : IOException){
            e.printStackTrace()
        }
        return data
    }

    fun Delete() : Boolean {
        val f = File(ctx.filesDir,filename)
        return f.deleteRecursively()
    }
companion object {


    fun generateNoteOnSD(sFileName: String, sBody: String) {
        try {
            val root = File(Environment.getExternalStorageDirectory(), "KartuPersediaan")
            if (!root.exists()) {
                root.mkdirs()
            }
            val gpxfile = File(root, sFileName)
            val writer = FileWriter(gpxfile)
            writer.append(sBody)
            writer.flush()
            writer.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun SaveAsPdf(landscape : Boolean,sFileName: String, sBody: String){

        try {
            val root = File(Environment.getExternalStorageDirectory(), "KartuPersediaan")
            if (!root.exists()) {
                root.mkdirs()
            }
            val file = FileOutputStream(File(root,sFileName))
            val document = Document()
            PdfWriter.getInstance(document, file)
            if (landscape) {
                document.pageSize = PageSize.A4.rotate()
            }
            val htmlWorker = HTMLWorker(document)

            document.open()
            htmlWorker.startDocument()
            htmlWorker.parse(StringReader(sBody))
            htmlWorker.endDocument()

            document.close()
            htmlWorker.close()
            file.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun OpenFileKartuPersediaan(ctx : Context,langObj: LangObj){
        android.support.v7.app.AlertDialog.Builder(ctx)
                .setTitle(langObj.laporanMenuLang.titleOpenFile)
                .setMessage(langObj.laporanMenuLang.messageOpenFile)
                .setPositiveButton(langObj.laporanMenuLang.ok, DialogInterface.OnClickListener { dialogInterface, i ->

                    val intent = Intent()
                    intent.setAction(Intent.ACTION_VIEW)
                    intent.setDataAndType(Uri.parse(Environment.getExternalStorageDirectory().absolutePath + "/KartuPersediaan"),"*/*")
                    ctx.startActivity(intent)
                    (ctx as Activity).finish()

                })
                .setNegativeButton(langObj.laporanMenuLang.no, DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
                .create()
                .show()

    }

    fun KartuPersediaanToHtml(ctx : Context,userData: UserData,IsThisPeriodFilterOn : Boolean, tglPeriod : ArrayList<FormatTanggal>,ProductDataToPrint : ArrayList<ProdukData>, methode : String, laporanKartuPersediaanObj: ArrayList<LaporanKartuPersediaanObj>, langObj: LangObj): String {


        val formatter = DecimalFormat("##,###")
        val now = Calendar.getInstance()
        val tgl_now = FormatTanggal()
        tgl_now.Hari = now.get(Calendar.DAY_OF_MONTH)
        tgl_now.Bulan = (now.get(Calendar.MONTH) + 1)
        tgl_now.Tahun = now.get(Calendar.YEAR)

        val dateInSring = ChangeDateToRelevanString(ctx, langObj)

        var opening = ""
        opening += "<p>${dateInSring.SetAndGetFormat(tgl_now,", "," ")}</p><br />"
        opening += "<div style='text-align:center;font-size:7px'><h1>${langObj.laporanMenuLang.titleLaporan}</h1>"
        if (tglPeriod.get(0).toDateString() == tglPeriod.get(tglPeriod.size-1).toDateString()){
            opening += "<h3>${dateInSring.SetAndGetFormat(tglPeriod.get(0),", "," ")}</h3>"

        }else {
            opening += "<h3>${dateInSring.SetAndGetFormat(tglPeriod.get(0),", "," ") + " " +langObj.laporanMenuLang.hinga + " " + dateInSring.SetAndGetFormat(tglPeriod.get(tglPeriod.size-1),", "," ")}</h3>"

        }
        opening += "<h3><b>${userData.CompanyName}</b></h3>"
        opening += "</div><br /><br /><h2>${langObj.printLaporanLang.metode + " : " + methode}</h2><br />"



        var str = ""

        for (perProduk in ProductDataToPrint){

            val header = "" +
                    "<h3>${langObj.printLaporanLang.namaP +" : "+perProduk.Nama}</h3>" +
                    "<h3>${langObj.printLaporanLang.defaultHargaP +" : "+formatter.format(perProduk.Harga)}</h3>" +
                    "<h3>${langObj.printLaporanLang.unitP +" : "+perProduk.Satuan}</h3>" +
                    "<br /><br />" +
                    "<div  style='text-align:center;font-size:8px'>" +
                    "<table border='1' id='MainTable'>\n" +
                    "<tr>\n" +
                    "<td rowspan='2' height='80'>${langObj.printLaporanLang.tgl}</td><td rowspan='2'>${langObj.printLaporanLang.keterangan}</td>\n" +
                    "<td colspan='3'>${langObj.printLaporanLang.Pembelian}</td>\n" +
                    "<td colspan='3'>${langObj.printLaporanLang.Penjualan}</td>\n" +
                    "<td rowspan='2'>${langObj.printLaporanLang.stok}</td>\n" +
                    "<td rowspan='2'>${langObj.printLaporanLang.hargaP}</td>\n" +
                    "<td rowspan='2'>${langObj.printLaporanLang.total}</td>\n" +
                    "</tr><tr>\n" +
                    "<td>${langObj.printLaporanLang.qtyP}</td><td>${langObj.printLaporanLang.hargaP}</td><td>${langObj.printLaporanLang.total}</td>\n" +
                    "<td>${langObj.printLaporanLang.qtyP}</td><td>${langObj.printLaporanLang.hargaP}</td><td>${langObj.printLaporanLang.total}</td>\n" +
                    "</tr>"

            var body = ""

            var StokPemasukan = 0
            var TotalPemasukan = 0

            var StokPengeluaran = 0
            var TotalPengeluaran = 0

            var StokPersediaan = 0
            var TotalPersediaan = 0


            for (d in laporanKartuPersediaanObj) {

                if (d.ProdukData.IdProduk == perProduk.IdProduk){

                    if (d.ProductFlow == TransaksiData.ProductIn) {

                        var ListKuantitas = ""
                        var ListHarga = ""
                        var ListTotal = ""

                        for (popKuantitas in d.ListKuantitas){
                            ListKuantitas += "${popKuantitas.Quantity}<br />"
                            ListHarga += "${formatter.format(popKuantitas.Harga)}<br />"
                            ListTotal += "${formatter.format(popKuantitas.Total)}<br />"
                        }

                        body += "<tr>\n" +
                                "<td >${dateInSring.SetAndGetFormatSimple(d.TanggalTransaksi,"/","/")}</td>\n" +
                                "<td >${d.Keterangan}</td>\n" +
                                "<td >${ListKuantitas}</td><td >${ListHarga}</td><td >${ListTotal}</td>\n" +
                                "<td > </td><td > </td><td > </td>\n"

                        var totalQtyLocal = 0
                        var TotalPersediaanLocal = 0

                        var qtyPersediaanToPrint = "<table border=0>"
                        var HargaPersediaanToPrint = "<table border=0>"
                        var TotalPersediaanToPrint = "<table border=0>"

                            for (dt in 0..(d.ListPersediaanData.size) - 1) {

                                qtyPersediaanToPrint += "<tr><td>" + if(d.ListPersediaanData.get(dt).Jumlah == 0 && methode != MetodePersediaan.AVERAGE) "&nbsp;" else d.ListPersediaanData.get(dt).Jumlah.toString() + "</td></tr>"
                                HargaPersediaanToPrint +=  "<tr><td>" + if (d.ListPersediaanData.get(dt).Jumlah == 0 && methode != MetodePersediaan.AVERAGE) "&nbsp;" else  formatter.format(d.ListPersediaanData.get(dt).Produk.Harga)+ "</td></tr>"
                                TotalPersediaanToPrint +=  "<tr><td>" + if (d.ListPersediaanData.get(dt).Jumlah == 0 && methode != MetodePersediaan.AVERAGE) "&nbsp;" else  formatter.format(d.ListPersediaanData.get(dt).Total)+ "</td></tr>"

                                totalQtyLocal += d.ListPersediaanData.get(dt).Jumlah
                                TotalPersediaanLocal += d.ListPersediaanData.get(dt).Total

                            }
                        qtyPersediaanToPrint += "</table>"
                        HargaPersediaanToPrint += "</table>"
                        TotalPersediaanToPrint += "</table>"

                        body += "<tr><td>${qtyPersediaanToPrint}</td><td>${HargaPersediaanToPrint}</td><td>${TotalPersediaanToPrint}</td></tr>\n"

                        StokPersediaan = totalQtyLocal
                        TotalPersediaan = TotalPersediaanLocal


                        StokPemasukan += d.GetKuantitas()
                        TotalPemasukan += d.GetTotalListKuantitas()


                    } else if (d.ProductFlow == TransaksiData.ProductOut) {

                        var ListKuantitas = "<table border=0>"
                        var ListHarga = "<table border=0>"
                        var ListTotal = "<table border=0>"

                        for (popKuantitas in d.ListKuantitas){
                            ListKuantitas += "<tr><td>${popKuantitas.Quantity}</td></tr>"
                            ListHarga += "<tr><td>${formatter.format(popKuantitas.Harga)}</td></tr>"
                            ListTotal += "<tr><td>${formatter.format(popKuantitas.Total)}</td></tr>"
                        }

                        ListKuantitas += "</table>"
                        ListHarga += "</table>"
                        ListTotal+= "</table>"

                        body += "<tr>\n" +
                                "<td >${dateInSring.SetAndGetFormatSimple(d.TanggalTransaksi,"/","/")}</td>\n" +
                                "<td >${d.Keterangan}</td>\n" +
                                "<td > </td><td > </td><td > </td>\n" +
                                "<td >${ListKuantitas}</td><td >${ListHarga}</td><td >${ListTotal}</td>\n"


                        var totalQtyLocal = 0
                        var TotalPersediaanLocal = 0

                        var qtyPersediaanToPrint = "<table border=0>"
                        var HargaPersediaanToPrint = "<table border=0>"
                        var TotalPersediaanToPrint = "<table border=0>"

                            for (dt in 0..(d.ListPersediaanData.size) - 1) {

                                qtyPersediaanToPrint += "<tr><td>" + if(d.ListPersediaanData.get(dt).Jumlah == 0 && methode!= MetodePersediaan.AVERAGE) "&nbsp;" else d.ListPersediaanData.get(dt).Jumlah.toString() + "</td></tr>"
                                HargaPersediaanToPrint +=  "<tr><td>" + if (d.ListPersediaanData.get(dt).Jumlah == 0 && methode != MetodePersediaan.AVERAGE) "&nbsp;" else  formatter.format(d.ListPersediaanData.get(dt).Produk.Harga)+ "</td></tr>"
                                TotalPersediaanToPrint +=  "<tr><td>" + if (d.ListPersediaanData.get(dt).Jumlah == 0 && methode != MetodePersediaan.AVERAGE) "&nbsp;" else  formatter.format(d.ListPersediaanData.get(dt).Total)+ "</td></tr>"

                                totalQtyLocal += d.ListPersediaanData.get(dt).Jumlah
                                TotalPersediaanLocal += d.ListPersediaanData.get(dt).Total
                            }

                        qtyPersediaanToPrint += "</table>"
                        HargaPersediaanToPrint += "</table>"
                        TotalPersediaanToPrint += "</table>"

                        body += "<tr><td>${qtyPersediaanToPrint}</td><td>${HargaPersediaanToPrint}</td><td>${TotalPersediaanToPrint}</td></tr>\n"


                        StokPersediaan = totalQtyLocal
                        TotalPersediaan = TotalPersediaanLocal

                        StokPengeluaran += d.GetKuantitas()
                        TotalPengeluaran += d.GetTotalListKuantitas()

                    }

                }

            }

            val total = "<tr>\n" +
                    "<td colspan='2'><h3>Total</h3></td>\n" +
                    "<td>${StokPemasukan}</td><td> </td><td>${formatter.format(TotalPemasukan)}</td>\n" +
                    "<td>${StokPengeluaran}</td><td> </td><td>${formatter.format(TotalPengeluaran)}</td>\n" +
                    "<td>${StokPersediaan}</td><td> </td><td>${formatter.format(TotalPersediaan)}</td>\n" +
                    "</tr>"

            body += "</tr>"
            val foot = "</table></div><br /><br /><br />"
            if (IsThisPeriodFilterOn){
                str += header + body  + foot
            }else {
                str += header + body + total + foot
            }


        }


        val finalLap = "<html style='size: landscape'><body>${opening} ${str}</body></html>"
        return finalLap
    }
}
}