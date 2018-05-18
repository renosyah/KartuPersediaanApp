package com.example.renosyahputra.kartupersediaan.storage.local

import android.content.Context
import android.os.Environment
import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.laporanKartuPersediaan.LaporanKartuPersediaanObj
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.FormatTanggal
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData
import com.example.renosyahputra.kartupersediaan.res.obj.user.UserData
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.itextpdf.text.Document
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

    fun SaveAsPdf(sFileName: String, sBody: String){
        try {
            val root = File(Environment.getExternalStorageDirectory(), "KartuPersediaan")
            if (!root.exists()) {
                root.mkdirs()
            }
            val file = FileOutputStream(File(root,sFileName))
            val document = Document()
            PdfWriter.getInstance(document, file)
            document.open()
            val htmlWorker = HTMLWorker(document)
            htmlWorker.parse(StringReader(sBody))
            document.close()
            file.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun KartuPersediaanToHtml(userData: UserData, tglPeriod : ArrayList<FormatTanggal>, MainData: KartuPersediaanData, methode : String, laporanKartuPersediaanObj: ArrayList<LaporanKartuPersediaanObj>, langObj: LangObj): String {

        val formatter = DecimalFormat("##,###")
        val now = Calendar.getInstance()
        val tgl_now = FormatTanggal()
        tgl_now.Hari = now.get(Calendar.DAY_OF_MONTH)
        tgl_now.Bulan = now.get(Calendar.MONTH)
        tgl_now.Tahun = now.get(Calendar.YEAR)

        var opening = ""
        opening += "<p>${tgl_now.toDateString()}</p><br />"
        opening += "<div style='text-align:center;font-size:8px'><h1>${langObj.laporanMenuLang.titleLaporan}</h1>"
        if (tglPeriod.get(0).toDateString() == tglPeriod.get(tglPeriod.size-1).toDateString()){
            opening += "<h3>${tglPeriod.get(0).toDateString()}</h3>"

        }else {
            opening += "<h3>${tglPeriod.get(0).toDateString() + " " +langObj.laporanMenuLang.hinga + " " + tglPeriod.get(tglPeriod.size-1).toDateString() }</h3>"

        }
        opening += "<h3><b>${userData.CompanyName}</b></h3>"
        opening += "</div><br /><br /><h2>${langObj.printLaporanLang.metode + " : " + methode}</h2><br />"



        var str = ""

        for (perProduk in MainData.ListProdukData){

            val header = "" +
                    "<h3>${langObj.printLaporanLang.namaP +" : "+perProduk.Nama}</h3><br /><br />" +
                    "<div  style='text-align:center;font-size:8px'>" +
                    "<table border='1'>\n" +
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

            for (d in laporanKartuPersediaanObj) {

                if (d.ProdukData.IdProduk == perProduk.IdProduk){

                    val sizeStok = d.ListPersediaanData.size

                    if (d.ProductFlow == TransaksiData.ProductIn) {

                        body += "<tr>\n" +
                                "<td rowspan='$sizeStok'>${d.TanggalTransaksi.toDateString()}</td>\n" +
                                "<td rowspan='$sizeStok'>${d.Keterangan}</td>\n" +
                                "<td rowspan='$sizeStok'>${d.Quantity}</td><td rowspan='$sizeStok'>${formatter.format(d.ProdukData.Harga)}</td><td rowspan='$sizeStok'>${formatter.format(d.ProdukData.Harga * d.Quantity)}</td>\n" +
                                "<td rowspan='$sizeStok'> </td><td rowspan='$sizeStok'> </td><td rowspan='$sizeStok'> </td>\n"


                            for (dt in 0..(d.ListPersediaanData.size) - 1) {
                                if (dt > 0) {
                                    body += "<tr>\n"
                                }
                                body += "<td>${d.ListPersediaanData.get(dt).Jumlah}</td><td>${formatter.format(d.ListPersediaanData.get(dt).Produk.Harga)}</td><td>${formatter.format(d.ListPersediaanData.get(dt).Produk.Harga * d.ListPersediaanData.get(dt).Jumlah)}</td>\n"
                                body += "</tr>"

                        }



                    } else if (d.ProductFlow == TransaksiData.ProductOut) {
                        body += "<tr>\n" +
                                "<td rowspan='$sizeStok'>${d.TanggalTransaksi.toDateString()}</td>\n" +
                                "<td rowspan='$sizeStok'>${d.Keterangan}</td>\n" +
                                "<td rowspan='$sizeStok'> </td><td rowspan='$sizeStok'> </td><td rowspan='$sizeStok'> </td>\n" +
                                "<td rowspan='$sizeStok'>${d.Quantity}</td><td rowspan='$sizeStok'>${formatter.format(d.ProdukData.Harga)}</td><td rowspan='$sizeStok'>${formatter.format(d.ProdukData.Harga * d.Quantity)}</td>\n"



                            for (dt in 0..(d.ListPersediaanData.size) - 1) {
                                if (dt > 0) {
                                    body += "<tr>\n"
                                }
                                body += "<td>${d.ListPersediaanData.get(dt).Jumlah}</td><td>${formatter.format(d.ListPersediaanData.get(dt).Produk.Harga)}</td><td>${formatter.format(d.ListPersediaanData.get(dt).Produk.Harga * d.ListPersediaanData.get(dt).Jumlah)}</td>\n"
                                body += "</tr>"

                            }

                    }
                }

            }

            body += "</tr>"
            val foot = "</table></div><br /><br /><br />"
            str += header + body + foot

        }


        val finalLap = "<html><body>" +opening + str + "</body></html>"
        return finalLap
    }
}
}