package com.example.renosyahputra.kartupersediaan.res.customAlertDialog.cariTanggalLaporan

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.res.ChangeDateToRelevanString
import com.example.renosyahputra.kartupersediaan.res.customAlertDialog.cariTanggalLaporan.res.CariTanggalLaporanRes
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.FormatTanggal
import com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.KartuPersediaanMenu
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.example.renosyahputra.kartupersediaan.ui.theme.obj.ThemeObj
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog

class CariTanggalLaporan(ctx : Context)  :View.OnClickListener{


    val context = ctx
    lateinit var dialog : AlertDialog

    lateinit var dateInSring : ChangeDateToRelevanString

    lateinit var filter: KartuPersediaanMenu.FilterCard


    fun SetTanggalAwalDanAkhir(filter: KartuPersediaanMenu.FilterCard){
       this.filter = filter
    }

    lateinit var onCariTanggalLaporan : CariTanggalLaporanRes.OnCariTanggalLaporan

    fun SetonCariTanggalLaporan(onCariTanggalLaporan : CariTanggalLaporanRes.OnCariTanggalLaporan){
        this.onCariTanggalLaporan = onCariTanggalLaporan
    }


    internal lateinit var lang : LangObj
    internal lateinit var theme: ThemeObj

    fun SetLangTheme(lang : LangObj, theme: ThemeObj){
        this.lang = lang
        this.theme = theme
    }


    lateinit var bar  :LinearLayout
    lateinit var title : TextView
    lateinit var TitleCariTanggalAwalText : TextView
    lateinit var TitleCariTanggalAkhirText : TextView
    lateinit var awalTanggalText : TextView
    lateinit var akhirTanggalText : TextView

    fun InitiationDialog(){

        dateInSring = ChangeDateToRelevanString(context, lang)

        dialog = AlertDialog.Builder(context)
                .setPositiveButton(lang.cariTanggalLaporanLang.Tampilkan, DialogInterface.OnClickListener { dialogInterface, i ->

                    if(FormatTanggal.CheckJikaTanggalTerbalik(filter.TanggalAwal!!,filter.TanggalAkhir!!)){

                        filter.TanggalAwal = null
                        filter.TanggalAkhir = null
                        Toast.makeText(context,lang.cariTanggalLaporanLang.InputTanggalSalah,Toast.LENGTH_SHORT).show()

                        onCariTanggalLaporan.OnFinishSelectFirstAndLastDate(lang.laporanMenuLang.filterPilihPeriode,filter)

                    }else {

                        onCariTanggalLaporan.OnFinishSelectFirstAndLastDate(if (filter.TanggalAwal!!.Tahun == filter.TanggalAkhir!!.Tahun) filter.TanggalAwal!!.Tahun.toString() else lang.laporanMenuLang.filterPilihPeriode,filter)

                       dialog.dismiss()
                    }

                })
                .setNegativeButton(lang.cariTanggalLaporanLang.Batal, DialogInterface.OnClickListener { dialogInterface, i ->

                    dialog.dismiss()
                })
                .create()

        val inflater = (context as Activity).layoutInflater
        val v = inflater.inflate(R.layout.custom_alert_dialog_cari_tanggal,null)

        bar = v.findViewById(R.id.barCariTanggal)
        title = v.findViewById(R.id.TitleCariTanggal)
        TitleCariTanggalAwalText = v.findViewById(R.id.TitleCariTanggalAwal)
        TitleCariTanggalAkhirText = v.findViewById(R.id.TitleCariTanggalAKhir)
        awalTanggalText = v.findViewById(R.id.textTanggalAwal)
        akhirTanggalText = v.findViewById(R.id.textTanggalAkhir)

        awalTanggalText.setOnClickListener(this)
        akhirTanggalText.setOnClickListener(this)

        SetLangAndTheme()

        dialog.setView(v)
        dialog.show()
    }

    internal fun SetLangAndTheme(){
        bar.setBackgroundColor(theme.BackGroundColor)
        title.setTextColor(theme.TextColor)
        title.setText(lang.cariTanggalLaporanLang.title)

        awalTanggalText.setTextColor(theme.BackGroundColor)
        akhirTanggalText.setTextColor(theme.BackGroundColor)

        TitleCariTanggalAwalText.setText(lang.cariTanggalLaporanLang.awal)
        TitleCariTanggalAkhirText.setText(lang.cariTanggalLaporanLang.akhir)

        awalTanggalText.setText(dateInSring.SetAndGetFormatSimple(filter.TanggalAwal!!,"/","/"))
        akhirTanggalText.setText(dateInSring.SetAndGetFormatSimple(filter.TanggalAkhir!!,"/","/"))
    }

    internal fun OpenCalendarDialog(target: TextView,tanggal : FormatTanggal){

        val dpl = DatePickerDialog.newInstance(
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    tanggal.Hari = dayOfMonth
                    tanggal.Bulan = (monthOfYear + 1)
                    tanggal.Tahun = year

                    target.setText(dateInSring.SetAndGetFormatSimple(tanggal,"/","/"))

                },
                tanggal.Tahun,
                (tanggal.Bulan-1),
                tanggal.Hari
        )
        dpl.setVersion(DatePickerDialog.Version.VERSION_2)
        dpl.setAccentColor(theme.BackGroundColor)
        dpl.show((context as Activity).fragmentManager,"Datepickerdialog")
    }

    override fun onClick(p0: View?) {
        when (p0){
            awalTanggalText -> {
                OpenCalendarDialog(awalTanggalText,filter.TanggalAwal!!)
            }
            akhirTanggalText -> {
                OpenCalendarDialog(akhirTanggalText,filter.TanggalAkhir!!)
            }
        }

    }
}