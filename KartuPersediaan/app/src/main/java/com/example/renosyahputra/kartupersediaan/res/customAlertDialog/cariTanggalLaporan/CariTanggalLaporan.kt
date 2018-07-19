package com.example.renosyahputra.kartupersediaan.res.customAlertDialog.cariTanggalLaporan

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.res.ChangeDateToRelevanString
import com.example.renosyahputra.kartupersediaan.res.customAdapter.CustomAdapterLaporanKartuPersediaan
import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.laporanKartuPersediaan.LaporanKartuPersediaanObj
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.FormatTanggal
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData
import com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.KartuPersediaanMenu
import com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.res.alternative.FunctionForKartuPersediaanMenuForAlternative
import com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.res.old.FunctionForKartuPersediaanMenu
import com.example.renosyahputra.kartupersediaan.ui.developerMode.DataDevMod
import com.example.renosyahputra.kartupersediaan.ui.developerMode.DevMod
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


    internal lateinit var lang : LangObj
    internal lateinit var theme: ThemeObj

    fun SetLangTheme(lang : LangObj, theme: ThemeObj){
        this.lang = lang
        this.theme = theme
    }

    lateinit var ListViewKartuPersediaan : ListView
    lateinit var KartuPersediaanKosongStatus : TextView
    lateinit var MainData : KartuPersediaanData
    lateinit var LaporanKartuPersediaan: ArrayList<LaporanKartuPersediaanObj>

    fun SetVariabelNeedForGenerateData(ListViewKartuPersediaan : ListView, KartuPersediaanKosongStatus : TextView, MainData : KartuPersediaanData,LaporanKartuPersediaan: ArrayList<LaporanKartuPersediaanObj>){
        this.ListViewKartuPersediaan = ListViewKartuPersediaan
        this.KartuPersediaanKosongStatus  = KartuPersediaanKosongStatus
        this.MainData = MainData
        this.LaporanKartuPersediaan = LaporanKartuPersediaan
    }

    lateinit var SetPeriodeLap  : TextView

    fun SetSetPeriodeLap(SetPeriodeLap :TextView){
        this.SetPeriodeLap = SetPeriodeLap
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

                    if(FormatTanggal.BandingkanTanggalPatokanDenganYangLebihBesar(filter.TanggalAwal!!,filter.TanggalAkhir!!)){
                        SetPeriodeLap.setText(lang.laporanMenuLang.filterPilihPeriode)
                        filter.TanggalAwal = null
                        filter.TanggalAkhir = null
                        Toast.makeText(context,lang.cariTanggalLaporanLang.InputTanggalSalah,Toast.LENGTH_SHORT).show()

                    }else {
                        if (FunctionForKartuPersediaanMenu.CheckValidQuantityProductInAllTransaction(MainData.ListTransaksiData)) {
                            FunctionForKartuPersediaanMenu.dialogTransNotValid(context,lang)
                        }else {
                            SetPeriodeLap.setText(if (filter.TanggalAwal!!.Tahun == filter.TanggalAkhir!!.Tahun) filter.TanggalAwal!!.Tahun.toString() else lang.laporanMenuLang.filterPilihPeriode)
                            GenerateData(context,ListViewKartuPersediaan,KartuPersediaanKosongStatus,filter,MainData,LaporanKartuPersediaan)
                        }
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




    fun GenerateData(ctx: Context,ListViewKartuPersediaan : ListView,KartuPersediaanKosongStatus : TextView,filter : KartuPersediaanMenu.FilterCard,MainData : KartuPersediaanData,LaporanKartuPersediaan: ArrayList<LaporanKartuPersediaanObj>){

        val load = DataDevMod(context)
        val Data = load.Load()!!

        MainData.ListPersediaanData.clear()
        LaporanKartuPersediaan.clear()

        val duplicateListTransaksi = ArrayList<TransaksiData>()

        for (trs in MainData.ListTransaksiData.sortedWith(compareBy({ it.TanggalTransaksi.Tahun }, { it.TanggalTransaksi.Bulan }, { it.TanggalTransaksi.Hari }, { it.Jam.Jam }, { it.Jam.Menit }))){
            duplicateListTransaksi.add(trs.CloneTransData())
        }

        if (Data.CovertMode == DevMod.NEW){

            FunctionForKartuPersediaanMenuForAlternative.FromTransactionListToKartuPersediaanListWithoutFilter(Data.LoopForFilter1,MainData,duplicateListTransaksi,LaporanKartuPersediaan)

            FunctionForKartuPersediaanMenuForAlternative.CalculatingStockAndModifyListPersediaanDataAlternative(MainData,LaporanKartuPersediaan)

            FunctionForKartuPersediaanMenu.FillEmptyVariabelForAVERAGE(MainData,LaporanKartuPersediaan)

            FunctionForKartuPersediaanMenuForAlternative.FromKartuPersediaanListToKartuPersediaanListByFilter(filter,LaporanKartuPersediaan)

        }else if (Data.CovertMode == DevMod.OLD) {

            FunctionForKartuPersediaanMenu.ModifyDataListKuantitasForFIFOAndLIFO(Data.LoopForFilter1, MainData, duplicateListTransaksi)

            FunctionForKartuPersediaanMenu.CalculatingStockAndModifyListPersediaanData(MainData, duplicateListTransaksi)

            FunctionForKartuPersediaanMenu.FromTransactionListToKartuPersediaanList(filter, duplicateListTransaksi, LaporanKartuPersediaan)

            FunctionForKartuPersediaanMenu.FillEmptyVariabelForAVERAGE(MainData, LaporanKartuPersediaan)
        }

        SetAdapter(ctx,ListViewKartuPersediaan,KartuPersediaanKosongStatus,LaporanKartuPersediaan)
    }



    fun SetAdapter(ctx: Context,ListViewKartuPersediaan : ListView,KartuPersediaanKosongStatus : TextView,l : ArrayList<LaporanKartuPersediaanObj>){

        val adapter = CustomAdapterLaporanKartuPersediaan(ctx,R.layout.custom_adapter_laporan_kartu_persediaan,l)
        adapter.SetLangTheme(lang,theme)
        ListViewKartuPersediaan.adapter = adapter
        ListViewKartuPersediaan.divider = null

        if (l.size < 1){
            KartuPersediaanKosongStatus.setText(lang.laporanMenuLang.KartuPersediaanKosong)
            KartuPersediaanKosongStatus.setTextColor(theme.BackGroundColor)
            KartuPersediaanKosongStatus.visibility = View.VISIBLE
            ListViewKartuPersediaan.visibility = View.GONE
        }else {
            KartuPersediaanKosongStatus.visibility = View.GONE
            ListViewKartuPersediaan.visibility = View.VISIBLE
        }
    }
}