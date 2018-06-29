package com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.res.FindHowMuchGapBetweenTrans
import com.example.renosyahputra.kartupersediaan.res.GenerateDataForAverage.Companion.FillZeroNumber
import com.example.renosyahputra.kartupersediaan.res.GenerateDataInventoryCard
import com.example.renosyahputra.kartupersediaan.res.SortData.Companion.GetAllPeriodeInGroup
import com.example.renosyahputra.kartupersediaan.res.ValidateOutProduct
import com.example.renosyahputra.kartupersediaan.res.customAdapter.CustomAdapterLaporanKartuPersediaan
import com.example.renosyahputra.kartupersediaan.res.customAdapter.CustomAdapterListProduk
import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.laporanKartuPersediaan.LaporanKartuPersediaanObj
import com.example.renosyahputra.kartupersediaan.res.obj.metode.MetodePersediaan
import com.example.renosyahputra.kartupersediaan.res.obj.persediaanData.PersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.FormatTanggal
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.KuantitasTransaksi
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData
import com.example.renosyahputra.kartupersediaan.res.obj.user.UserData
import com.example.renosyahputra.kartupersediaan.storage.local.SaveMainData
import com.example.renosyahputra.kartupersediaan.storage.local.SaveMainData.Companion.OpenFileKartuPersediaan
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.example.renosyahputra.kartupersediaan.ui.theme.obj.ThemeObj

class KartuPersediaanMenu : Fragment(),AdapterView.OnItemClickListener,View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{

    lateinit var ctx : Context
    lateinit var v : View

    lateinit var MainData : KartuPersediaanData

    lateinit var LaporanKartuPersediaan : ArrayList<LaporanKartuPersediaanObj>


    fun SetMainData(MainData : KartuPersediaanData){
        this.MainData = MainData
    }

    lateinit var user : UserData

    fun SetUserData(userData: UserData){
        this.user = userData
    }

    internal lateinit var lang : LangObj
    internal lateinit var theme: ThemeObj

    fun SetLangTheme(lang : LangObj, theme: ThemeObj){
        this.lang = lang
        this.theme = theme
    }

    internal lateinit var FloatingButton : com.melnykov.fab.FloatingActionButton

    fun setFloatingButton(f  : com.melnykov.fab.FloatingActionButton){
        this.FloatingButton = f

    }

    internal lateinit var ListViewKartuPersediaan : ListView
    internal lateinit var refreshLayout: SwipeRefreshLayout

    lateinit var LinearLayoutLapOption : LinearLayout
    lateinit var SetPeriodeLap : TextView
    lateinit var SetMethodeLap : TextView
    lateinit var sortInventoryCard : TextView
    lateinit var PrintNowButton : ImageView

    lateinit var KartuPersediaanKosongStatus  :TextView

    class FilterCard{
        var p : ProdukData? = null
        var tahun = 0
    }

    lateinit var filterSearch : FilterCard


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        v = inflater!!.inflate(R.layout.kartu_persediaan_menu,container,false)
        InitiationWidget(v)
        return v
    }


    fun GenerateData(filter : FilterCard){

        MainData.ListPersediaanData.clear()
        LaporanKartuPersediaan.clear()

        val duplicateListTransaksi = ArrayList<TransaksiData>()

        for (trs in MainData.ListTransaksiData.sortedWith(compareBy({ it.TanggalTransaksi.Tahun }, { it.TanggalTransaksi.Bulan }, { it.TanggalTransaksi.Hari }, { it.Jam.Jam }, { it.Jam.Menit }))){
            duplicateListTransaksi.add(trs.CloneTransData())
        }

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

        for (dataTrans in duplicateListTransaksi.sortedWith(compareBy({ it.TanggalTransaksi.Tahun }, { it.TanggalTransaksi.Bulan }, { it.TanggalTransaksi.Hari }, { it.Jam.Jam }, { it.Jam.Menit }))) {
            GenerateDataInventoryCard.GenerateForEachTransaction(MainData, dataTrans)
        }


        for (dataTrans in duplicateListTransaksi.sortedWith(compareBy({it.TanggalTransaksi.Tahun},{it.TanggalTransaksi.Bulan},{it.TanggalTransaksi.Hari},{it.Jam.Jam},{it.Jam.Menit}))){
            if (filter.tahun > 0 && (dataTrans.TanggalTransaksi.Tahun == filter.tahun)){
                for (detail in dataTrans.ListDetail){
                    if (filter.p != null && (filter.p!!.IdProduk == detail.ProdukData.IdProduk)){
                        AppendToCartuPersediaan(dataTrans.IdTransaksiData,dataTrans.TanggalTransaksi,detail.ProdukData,dataTrans.Keterangan,dataTrans.ProductFlow,detail.ListPersediaanData,detail.ListKuantitas)
                    }else if (filter.p == null){
                        AppendToCartuPersediaan(dataTrans.IdTransaksiData,dataTrans.TanggalTransaksi,detail.ProdukData,dataTrans.Keterangan,dataTrans.ProductFlow,detail.ListPersediaanData,detail.ListKuantitas)
                    }

                }
            }else if (filter.tahun == 0){
                for (detail in dataTrans.ListDetail){
                    if (filter.p != null && (filter.p!!.IdProduk == detail.ProdukData.IdProduk)){
                        AppendToCartuPersediaan(dataTrans.IdTransaksiData,dataTrans.TanggalTransaksi,detail.ProdukData,dataTrans.Keterangan,dataTrans.ProductFlow,detail.ListPersediaanData,detail.ListKuantitas)
                    }else if (filter.p == null){
                        AppendToCartuPersediaan(dataTrans.IdTransaksiData,dataTrans.TanggalTransaksi,detail.ProdukData,dataTrans.Keterangan,dataTrans.ProductFlow,detail.ListPersediaanData,detail.ListKuantitas)
                    }
                }
            }
        }

        if (MainData.metodePersediaan.MetodeUse == MetodePersediaan.AVERAGE) {
            for (p in MainData.ListProdukData) {
                FillZeroNumber(p, LaporanKartuPersediaan)
            }
        }


        SetAdapter(LaporanKartuPersediaan)
    }

    internal fun AppendToCartuPersediaan(IdTransaksiData : String, TanggalTransaksi : FormatTanggal, ProdukData : ProdukData, Keterangan : String, ProductFlow :String, ListPersediaanData : ArrayList<PersediaanData>,listKuantitas : ArrayList<KuantitasTransaksi>){
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

    fun SetAdapter(l : ArrayList<LaporanKartuPersediaanObj>){

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


    internal fun InitiationWidget(v : View){

        ctx = activity

        LaporanKartuPersediaan = ArrayList()
        filterSearch = FilterCard()

        LinearLayoutLapOption = v.findViewById(R.id.LinearLayoutLapOption)
        LinearLayoutLapOption.setBackgroundColor(theme.BackGroundColor)

        SetPeriodeLap = v.findViewById(R.id.SetPeriode)
        SetPeriodeLap.setTextColor(theme.BackGroundColor)
        SetPeriodeLap.setText(lang.laporanMenuLang.filterPilihPeriode)

        SetMethodeLap = v.findViewById(R.id.setMethod)
        SetMethodeLap.setTextColor(theme.BackGroundColor)
        SetMethodeLap.setText(MainData.metodePersediaan.MetodeUse)

        sortInventoryCard = v.findViewById(R.id.sortInventoryCard)
        sortInventoryCard.setTextColor(theme.BackGroundColor)
        sortInventoryCard.setText(lang.laporanMenuLang.filterPilihProduk)

        PrintNowButton = v.findViewById(R.id.PrintNowButton)

        ListViewKartuPersediaan = v.findViewById(R.id.KartuPersediaanListview)

        KartuPersediaanKosongStatus = v.findViewById(R.id.KartuPersediaanKosong)

        refreshLayout = v.findViewById(R.id.KartuPersediaanRefresh)
        refreshLayout.setOnRefreshListener(this)

        ListViewKartuPersediaan.setOnItemClickListener(this)
        FloatingButton.attachToListView(ListViewKartuPersediaan)
        SetPeriodeLap.setOnClickListener(this)
        SetMethodeLap.setOnClickListener(this)
        sortInventoryCard.setOnClickListener(this)
        PrintNowButton.setOnClickListener(this)

        SetAdapter(ArrayList())

        if (CheckValidQuantityProductInAllTransaction(MainData.ListTransaksiData)) {
            dialogTransNotValid()
        }else {
            GenerateData(filterSearch)
        }



    }



    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        OpenDialogDetail(LaporanKartuPersediaan.get(p2))
    }

    override fun onClick(p0: View?) {
        when (p0) {
            PrintNowButton-> {

                if (LaporanKartuPersediaan.size < 1){
                    return
                }

                var ProductToPrint = ArrayList<ProdukData>()
                if (filterSearch.p != null){
                    ProductToPrint.add(filterSearch.p!!)
                }else {
                    ProductToPrint = MainData.ListProdukData
                }


                val allTgl = ArrayList<FormatTanggal>()
                for (tgl in LaporanKartuPersediaan){
                    if (filterSearch.tahun !=0 && tgl.TanggalTransaksi.Tahun == filterSearch.tahun){
                        allTgl.add(tgl.TanggalTransaksi)
                    }else if (filterSearch.tahun == 0){
                        allTgl.add(tgl.TanggalTransaksi)
                    }
                }


                val option = arrayOf<CharSequence>(lang.laporanMenuLang.toPDF,lang.laporanMenuLang.toPDFLANDSCAPE)

                if (CheckValidQuantityProductInAllTransaction(MainData.ListTransaksiData)) {
                    dialogTransNotValid()
                }else {

                    MainData.metodePersediaan.MetodeUse = MetodePersediaan.FIFO
                    GenerateData(filterSearch)
                    val dataStringFIFO = SaveMainData.KartuPersediaanToHtml(ctx, user, allTgl, ProductToPrint, MainData.metodePersediaan.MetodeUse, LaporanKartuPersediaan, lang)

                    MainData.metodePersediaan.MetodeUse = MetodePersediaan.LIFO
                    GenerateData(filterSearch)
                    val dataStringLIFO = SaveMainData.KartuPersediaanToHtml(ctx, user, allTgl, ProductToPrint, MainData.metodePersediaan.MetodeUse, LaporanKartuPersediaan, lang)

                    MainData.metodePersediaan.MetodeUse = MetodePersediaan.AVERAGE
                    GenerateData(filterSearch)
                    val dataStringAVERAGE = SaveMainData.KartuPersediaanToHtml(ctx, user, allTgl, ProductToPrint, MainData.metodePersediaan.MetodeUse, LaporanKartuPersediaan, lang)

                    AlertDialog.Builder(ctx)
                            .setTitle(lang.laporanMenuLang.exportTitle)
                            .setNegativeButton(lang.laporanMenuLang.cancel, DialogInterface.OnClickListener { dialogInterface, i ->
                                dialogInterface.dismiss()
                            }).setItems(option, DialogInterface.OnClickListener { dialogInterface, i ->
                                when (i) {
                                    0 -> {
                                        SaveMainData.SaveAsPdf(false,"KartuPersediaanFIFO.pdf", dataStringFIFO)
                                        SaveMainData.SaveAsPdf(false,"KartuPersediaanLIFO.pdf", dataStringLIFO)
                                        SaveMainData.SaveAsPdf(false,"KartuPersediaanAVERAGE.pdf", dataStringAVERAGE)
                                    }
                                    1 -> {
                                        SaveMainData.SaveAsPdf(true,"KartuPersediaanFIFO.pdf", dataStringFIFO)
                                        SaveMainData.SaveAsPdf(true,"KartuPersediaanLIFO.pdf", dataStringLIFO)
                                        SaveMainData.SaveAsPdf(true,"KartuPersediaanAVERAGE.pdf", dataStringAVERAGE)
                                    }
                                }
                                OpenFileKartuPersediaan(ctx,lang)

                                MainData.metodePersediaan.MetodeUse = MetodePersediaan.FIFO
                                if (CheckValidQuantityProductInAllTransaction(MainData.ListTransaksiData)) {
                                    GenerateData(filterSearch)
                                }

                            }).create().show()
                }

            }
            SetMethodeLap -> {
                val optionLang = arrayOf<CharSequence>(MetodePersediaan.FIFO,MetodePersediaan.LIFO,MetodePersediaan.AVERAGE)
                AlertDialog.Builder(ctx)
                        .setTitle(lang.laporanMenuLang.filterPilihMethod)
                        .setItems(optionLang, DialogInterface.OnClickListener { dialogInterface, i ->
                            if (i == 0){
                                MainData.metodePersediaan.MetodeUse = MetodePersediaan.FIFO

                            }else if (i == 1){
                                MainData.metodePersediaan.MetodeUse = MetodePersediaan.LIFO

                            }else if (i == 2){
                                MainData.metodePersediaan.MetodeUse = MetodePersediaan.AVERAGE

                            }
                            SetMethodeLap.setText(MainData.metodePersediaan.MetodeUse)

                            GenerateAfterFilterChoose()

                            dialogInterface.dismiss()
                        })
                        .setNegativeButton(lang.subMenuTransLang.batal, DialogInterface.OnClickListener { dialogInterface, i ->
                            dialogInterface.dismiss()
                        })
                        .create().show()
            }
            SetPeriodeLap -> {

                val Allperiode = GetAllPeriodeInGroup(MainData.ListTransaksiData)

                val dialog = AlertDialog.Builder(ctx)
                        .setTitle(lang.laporanMenuLang.filterPilihPeriode)
                        .setNeutralButton(lang.laporanMenuLang.FiterChooseAllPeriod, DialogInterface.OnClickListener { dialogInterface, i ->

                            SetPeriodeLap.setText(lang.laporanMenuLang.filterPilihPeriode)
                            filterSearch.tahun = 0

                            GenerateAfterFilterChoose()

                            dialogInterface.dismiss()
                        })
                        .setNegativeButton(lang.addTransDialogLang.tutup, DialogInterface.OnClickListener { dialogInterface, i ->

                            dialogInterface.dismiss()
                        })
                        .create()
                val inflater = (ctx as Activity).layoutInflater
                val v = inflater.inflate(R.layout.custom_alert_dialog_add_product_trans, null)

                val list: ListView = v.findViewById(R.id.ListView_dialog_add_product_trans)
                val teksKosong : TextView = v.findViewById(R.id.ListView_dialog_add_product_trans_isEmpty)
                teksKosong.setText(lang.subMenuTransLang.TransKosong)

                if (Allperiode.size < 1){
                    teksKosong.visibility = View.VISIBLE
                    list.visibility = View.GONE
                }else {
                    teksKosong.visibility = View.GONE
                    list.visibility = View.VISIBLE
                }

                list.adapter = ArrayAdapter<Int>(ctx, android.R.layout.simple_dropdown_item_1line, Allperiode)

                list.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->
                    SetPeriodeLap.setText(Allperiode.get(i).toString())
                    filterSearch.tahun = Allperiode.get(i)

                    GenerateAfterFilterChoose()

                    dialog.dismiss()
                })

                dialog.setView(v)
                dialog.show()
            }
            sortInventoryCard -> {

                val productdialog = AlertDialog.Builder(ctx)
                        .setTitle(lang.laporanMenuLang.filterPilihProduk)
                        .setNeutralButton(lang.laporanMenuLang.FilterChooseAllProduct, DialogInterface.OnClickListener { dialogInterface, i ->
                            sortInventoryCard.setText(lang.laporanMenuLang.filterPilihProduk)

                            filterSearch.p = null

                            GenerateAfterFilterChoose()

                            dialogInterface.dismiss()
                        })
                        .setNegativeButton(lang.addTransDialogLang.tutup, DialogInterface.OnClickListener { dialogInterface, i ->
                            dialogInterface.dismiss()
                        })
                        .create()

                val inflater = (ctx as Activity).layoutInflater
                val v = inflater.inflate(R.layout.custom_alert_dialog_add_product_trans,null)

                val list : ListView = v.findViewById(R.id.ListView_dialog_add_product_trans)
                val textEmpty : TextView = v.findViewById(R.id.ListView_dialog_add_product_trans_isEmpty)
                textEmpty.setText(lang.subMenuProdukLang.ProdukKosong)
                textEmpty.setTextColor(theme.BackGroundColor)

                val adapter = CustomAdapterListProduk(ctx,R.layout.custom_adapter_listproduk,MainData.ListProdukData)
                adapter.SetLangTheme(lang,theme)
                list.adapter = adapter

                if (MainData.ListProdukData.size < 1){
                    textEmpty.visibility = View.VISIBLE
                    list.visibility = View.GONE
                }

                list.setOnItemClickListener { adapterView, view, i, l ->

                    sortInventoryCard.setText(MainData.ListProdukData.get(i).Nama)
                    filterSearch.p = MainData.ListProdukData.get(i)

                    GenerateAfterFilterChoose()

                    productdialog.dismiss()
                }

                productdialog.setView(v)
                productdialog.show()

            }
        }

    }

    internal fun GenerateAfterFilterChoose(){
        if (CheckValidQuantityProductInAllTransaction(MainData.ListTransaksiData)) {
            dialogTransNotValid()
        }else {
            GenerateData(filterSearch)
        }
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

    fun dialogTransNotValid(){

        AlertDialog.Builder(ctx).setTitle(lang.laporanMenuLang.WarningInvalidProduckInTransTitle)
                .setMessage(lang.laporanMenuLang.WarningInvalidProduckInTrans)
                .setIcon(R.drawable.warning)
                .setPositiveButton(lang.mainMenuSettingLang.Back, DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
                .create()
                .show()
    }

    override fun onRefresh() {
        if (CheckValidQuantityProductInAllTransaction(MainData.ListTransaksiData)) {
            dialogTransNotValid()
        }else {
            GenerateData(filterSearch)
        }
        if (refreshLayout.isRefreshing){
            refreshLayout.isRefreshing = !refreshLayout.isRefreshing
        }
    }

    internal fun OpenDialogDetail(dt: LaporanKartuPersediaanObj){
        val intent = Intent(ctx,DetailKartuPersediaan::class.java)
        intent.putExtra("dataDetail",dt)
        intent.putExtra("lang",lang)
        intent.putExtra("theme",theme)
        ctx.startActivity(intent)

    }
}