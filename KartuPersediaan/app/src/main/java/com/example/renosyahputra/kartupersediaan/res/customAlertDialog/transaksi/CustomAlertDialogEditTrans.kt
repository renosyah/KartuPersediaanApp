package com.example.renosyahputra.kartupersediaan.res.customAlertDialog.transaksi

import android.app.Activity
import android.app.FragmentManager
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.*
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.res.IdGenerator
import com.example.renosyahputra.kartupersediaan.res.customAdapter.CustomAdapterDetailTransaction
import com.example.renosyahputra.kartupersediaan.res.customAdapter.CustomAdapterListProduk
import com.example.renosyahputra.kartupersediaan.res.customAdapter.CustomAdapterTransaksi
import com.example.renosyahputra.kartupersediaan.res.customAlertDialog.transaksi.ResFunction.Companion.CheckAndAddQtyIfSame
import com.example.renosyahputra.kartupersediaan.res.customAlertDialog.transaksi.ResFunction.Companion.getTotalFromDetail
import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.DetailTransaksi
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.FormatWaktu
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.KuantitasTransaksi
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData
import com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.res.ChangeDateToRelevanString
import com.example.renosyahputra.kartupersediaan.subMenu.transaksiMenu.TransaksiMenu
import com.example.renosyahputra.kartupersediaan.subMenu.transaksiMenu.TransaksiMenu.Companion.CheckAndMarkTransactionWithNonValidQty
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.example.renosyahputra.kartupersediaan.ui.theme.obj.ThemeObj
import com.example.renosyahputra.quicktrans.ui.GetListViewTotalHeight
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import java.util.*

class CustomAlertDialogEditTrans(ctx : Context, res : Int, Data : KartuPersediaanData,trans : TransaksiData,posTrans : Int) : View.OnClickListener, AdapterView.OnItemClickListener {


    val context = ctx
    val resources = res
    val MainData = Data

    lateinit var dialog : AlertDialog

    lateinit var dateInSring : ChangeDateToRelevanString

    internal lateinit var FragmentChanger : FragmentManager


    lateinit var LinearLayoutListViewDetailAddTrans : LinearLayout

    lateinit var bar : RelativeLayout
    lateinit var title : TextView

    internal lateinit var butttonChooseType : Button
    internal lateinit var add : Button
    internal lateinit var cancel : Button
    internal lateinit var addProductToList : Button

    lateinit var chooseDate : TextView
    lateinit var chooseTime : TextView
    lateinit var inputInfo : EditText

    lateinit var ListDetail : ListView

    val newTrans = trans
    val pos = posTrans

    fun SetOtherVar(FragmentChanger : FragmentManager){
        this.FragmentChanger = FragmentChanger
    }

    lateinit var listViewTransaction : ListView
    lateinit var listViewTransactionDatas : ArrayList<TransaksiData>

    fun RefreshAdapter(l : ListView,datas : ArrayList<TransaksiData>){
        this.listViewTransaction = l
        this.listViewTransactionDatas = datas
    }

    internal lateinit var lang : LangObj
    internal lateinit var theme: ThemeObj

    fun SetLangTheme(lang : LangObj, theme: ThemeObj){
        this.lang = lang
        this.theme = theme
    }


    internal fun InitiationWidget(v : View){

        dateInSring = ChangeDateToRelevanString(context,lang)

        LinearLayoutListViewDetailAddTrans = v.findViewById(R.id.LinearLayoutListViewDetailAddTrans)

        bar = v.findViewById(R.id.titleBarAddTrans)
        bar.setBackgroundColor(theme.BackGroundColor)

        title = v.findViewById(R.id.titleAddTrans)
        title.setText(lang.addTransDialogLang.titleForEdit)
        title.setTextColor(theme.TextColor)

        butttonChooseType = v.findViewById(R.id.buttonSellectTypeTrans)
        butttonChooseType.setText(if (newTrans.ProductFlow == TransaksiData.ProductIn) lang.addTransDialogLang.TypeIn else lang.addTransDialogLang.TypeOUT)
        butttonChooseType.setTextColor(theme.TextColor)
        butttonChooseType.setBackgroundColor(theme.BackGroundColor)
        butttonChooseType.setBackgroundColor(ResFunction.SetBackgroundColor(context,newTrans))

        chooseDate = v.findViewById(R.id.ChooseDateAddTrans)
        chooseDate.setText(dateInSring.SetAndGetFormatSimple(newTrans.TanggalTransaksi,"/","/"))
        chooseDate.setTextColor(theme.BackGroundColor)

        chooseTime = v.findViewById(R.id.ChooseTimeAddTrans)
        chooseTime.setText(newTrans.Jam.MakeJamString())
        chooseTime.setTextColor(theme.BackGroundColor)

        inputInfo = v.findViewById(R.id.AddTransInformationForm)
        inputInfo.setText(newTrans.Keterangan)
        inputInfo.setHint(lang.addTransDialogLang.addInformation)

        ListDetail = v.findViewById(R.id.ListViewDetailAddTrans)

        add = v.findViewById(R.id.buttonAddTrans)
        add.setText(lang.addTransDialogLang.add)
        add.setTextColor(theme.TextColor)
        add.setBackgroundColor(theme.BackGroundColor)

        cancel  = v.findViewById(R.id.buttonCancelTrans)
        cancel.setText(lang.addTransDialogLang.cancel)
        cancel.setTextColor(theme.TextColor)
        cancel.setBackgroundColor(theme.BackGroundColor)

        addProductToList  = v.findViewById(R.id.buttonAddProductTrans)
        addProductToList.setText(lang.addTransDialogLang.addProduct)
        addProductToList.setTextColor(theme.TextColor)
        addProductToList.setBackgroundColor(theme.BackGroundColor)


        butttonChooseType.setOnClickListener(this)
        add.setOnClickListener(this)
        cancel.setOnClickListener(this)
        addProductToList.setOnClickListener(this)
        chooseDate.setOnClickListener(this)
        chooseTime.setOnClickListener(this)

        ListDetail.setOnItemClickListener(this)

        setDetailAdapter(ListDetail,newTrans.ListDetail)
    }

    fun Initiated(){

        dialog = AlertDialog.Builder(context).create()
        val inflater = (context as Activity).layoutInflater
        val v = inflater.inflate(resources,null)

        InitiationWidget(v)

        dialog.setView(v)
        dialog.show()
    }



    override fun onClick(p0: View?) {
        when (p0){
            chooseTime -> {
                val now = Calendar.getInstance()
                val dpl = TimePickerDialog.newInstance(TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute, second ->

                    val waktu = FormatWaktu()
                    waktu.Jam = hourOfDay
                    waktu.Menit = minute
                    waktu.PMorAM = ""

                    newTrans.Jam = waktu
                    chooseTime.setText(waktu.MakeJamString())
                },
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        true
                )

                dpl.setVersion(TimePickerDialog.Version.VERSION_1)
                dpl.setAccentColor(theme.BackGroundColor)
                dpl.show(FragmentChanger,"Datepickerdialog")
            }
            chooseDate -> {
                val now = Calendar.getInstance()
                val dpl = DatePickerDialog.newInstance(
                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                            newTrans.TanggalTransaksi.Hari = dayOfMonth
                            newTrans.TanggalTransaksi.Bulan = (monthOfYear + 1)
                            newTrans.TanggalTransaksi.Tahun = year

                            chooseDate.setText(dateInSring.SetAndGetFormatSimple(newTrans.TanggalTransaksi,"/","/"))


                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                )
                dpl.setVersion(DatePickerDialog.Version.VERSION_2)
                dpl.setAccentColor(theme.BackGroundColor)
                dpl.show(FragmentChanger,"Datepickerdialog")
            }
            butttonChooseType -> {

                val option = arrayOf<CharSequence>(lang.addTransDialogLang.TypeIn,lang.addTransDialogLang.TypeOUT)
                android.support.v7.app.AlertDialog.Builder(context)
                        .setTitle(lang.addTransDialogLang.chooseType)
                        .setItems(option, DialogInterface.OnClickListener { dialogInterface, i ->
                            if (i == 0){

                                newTrans.ProductFlow = TransaksiData.ProductIn
                                butttonChooseType.setText(lang.addTransDialogLang.TypeIn)

                            }else if (i == 1){

                                newTrans.ProductFlow = TransaksiData.ProductOut
                                butttonChooseType.setText(lang.addTransDialogLang.TypeOUT)

                            }

                            butttonChooseType.setBackgroundColor(ResFunction.SetBackgroundColor(context,newTrans))

                            dialogInterface.dismiss()
                        })
                        .setNegativeButton(lang.addTransDialogLang.cancel, DialogInterface.OnClickListener { dialogInterface, i ->
                            dialogInterface.dismiss()
                        })
                        .create().show()
            }
            addProductToList -> {

                OpenProductList()

            }
            add -> {


                newTrans.Keterangan = inputInfo.text.toString()
                newTrans.SubTotal = getTotalFromDetail(newTrans,newTrans.ListDetail)

                if (newTrans.ProductFlow == "" ||newTrans.ListDetail.size < 1 || newTrans.Keterangan == "" || (newTrans.TanggalTransaksi.Hari == 0 ||newTrans.TanggalTransaksi.Bulan == 0 ||newTrans.TanggalTransaksi.Tahun == 0)){
                    Toast.makeText(context,lang.addTransDialogLang.CheckAgain,Toast.LENGTH_SHORT).show()
                    return
                }

                var finalCheckQtyIsMin = false


                for (detailFinalCheck in newTrans.ListDetail){
                    if (ResFunction.FinalQtyCheckInNewTrans(newTrans,detailFinalCheck.ProdukData, newTrans.ListDetail, MainData.ListTransaksiData)){
                        finalCheckQtyIsMin = true
                        break
                    }

                }


                if (finalCheckQtyIsMin && newTrans.ProductFlow == TransaksiData.ProductOut){
                    Toast.makeText(context,lang.addTransDialogLang.warningThereIsProductQtyLow,Toast.LENGTH_SHORT).show()
                    return
                }


                ApplyChangeToMainData(newTrans,pos)

                SetAdapter()

                dialog.dismiss()
            }
            cancel -> {
                dialog.dismiss()
            }
        }
    }

    fun ApplyChangeToMainData(transData : TransaksiData,pos: Int){

        MainData.ListTransaksiData.get(pos).ProductFlow = transData.ProductFlow
        MainData.ListTransaksiData.get(pos).Keterangan = transData.Keterangan
        MainData.ListTransaksiData.get(pos).Jam.Jam = transData.Jam.Jam
        MainData.ListTransaksiData.get(pos).Jam.Menit = transData.Jam.Menit
        MainData.ListTransaksiData.get(pos).TanggalTransaksi.Hari = transData.TanggalTransaksi.Hari
        MainData.ListTransaksiData.get(pos).TanggalTransaksi.Bulan = transData.TanggalTransaksi.Bulan
        MainData.ListTransaksiData.get(pos).TanggalTransaksi.Tahun = transData.TanggalTransaksi.Tahun
        MainData.ListTransaksiData.get(pos).ListDetail = transData.ListDetail
        MainData.ListTransaksiData.get(pos).SubTotal = transData.SubTotal



    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (newTrans.ListDetail.size >= 1){

            val optionLang = arrayOf<CharSequence>("Edit "+lang.addTransDialogLang.qty,"Edit "+lang.addTransDialogLang.price,lang.addTransDialogLang.hapus)
            android.support.v7.app.AlertDialog.Builder(context)
                    .setTitle(lang.addTransDialogLang.titleEditDetail)
                    .setItems(optionLang, DialogInterface.OnClickListener { dialogInterface, i ->
                        if (i == 0){

                            EditQtyDetailTrans(newTrans.ListDetail,p2)
                            dialogInterface.dismiss()

                        }else if (i == 1){

                            EditHargaDetailTrans(newTrans.ListDetail,p2)
                            dialogInterface.dismiss()

                        }else if (i == 2){
                            newTrans.ListDetail.removeAt(p2)

                            setDetailAdapter(ListDetail,newTrans.ListDetail)
                            dialogInterface.dismiss()
                        }

                        dialogInterface.dismiss()
                    })
                    .setNegativeButton(lang.addTransDialogLang.cancel, DialogInterface.OnClickListener { dialogInterface, i ->
                        dialogInterface.dismiss()
                    })
                    .create().show()

        }
    }

    internal fun EditQtyDetailTrans(detail : ArrayList<DetailTransaksi>,pos : Int){
        val inflater = (context as Activity).layoutInflater
        val v = inflater.inflate(R.layout.custom_alert_dialog_edit_qty,null)

        val checkDuluQty = (ResFunction.GetTotalQtyProductFromAllTrans(newTrans, detail.get(pos).ProdukData, MainData.ListTransaksiData))

        val qty : EditText = v.findViewById(R.id.editQtyDetail)
        qty.setText(detail.get(pos).GetKuantitas().toString())

        val dialogEditQty = android.support.v7.app.AlertDialog.Builder(context)
                .setTitle(lang.addTransDialogLang.Stok + " "+ detail.get(pos).ProdukData.Nama +" : "+checkDuluQty)
                .setPositiveButton(lang.addTransDialogLang.Ok, DialogInterface.OnClickListener { dialogInterface, i ->

                    if ((checkDuluQty - Integer.parseInt(qty.text.toString())) < 0 && newTrans.ProductFlow == TransaksiData.ProductOut){

                        Toast.makeText(context,lang.addTransDialogLang.warningProductQtyLow + " = " + checkDuluQty,Toast.LENGTH_SHORT).show()

                    }else {

                        val ListKuantitas = ArrayList<KuantitasTransaksi>()
                        val KuantitasData = KuantitasTransaksi()

                        val id = IdGenerator()
                        id.CreateRandomString(15)
                        val idDetail = id.GetId()

                        detail.get(pos).IdDetailTransaksiData = idDetail

                        KuantitasData.IdDetailTransaksiData = idDetail
                        KuantitasData.Quantity = Integer.parseInt(qty.text.toString())
                        KuantitasData.Harga = detail.get(pos).ProdukData.Harga
                        KuantitasData.Total = KuantitasData.Harga * KuantitasData.Quantity
                        ListKuantitas.add(KuantitasData)

                        detail.get(pos).ListKuantitas = ListKuantitas

                        setDetailAdapter(ListDetail,newTrans.ListDetail)
                        dialogInterface.dismiss()

                    }
                })
                .setNegativeButton(lang.addTransDialogLang.cancel,null)
                .create()




        dialogEditQty.setView(v)
        dialogEditQty.show()
    }
    internal fun EditHargaDetailTrans(detail : ArrayList<DetailTransaksi>,pos : Int){
        val inflater = (context as Activity).layoutInflater
        val v = inflater.inflate(R.layout.custom_alert_dialog_edit_qty,null)

        val qty : EditText = v.findViewById(R.id.editQtyDetail)
        qty.setText(detail.get(pos).ProdukData.Harga.toString())

        val dialogEditQty = android.support.v7.app.AlertDialog.Builder(context)
                .setTitle("Edit "+ detail.get(pos).ProdukData.Nama +" "+lang.addTransDialogLang.price)
                .setPositiveButton(lang.addTransDialogLang.Ok, DialogInterface.OnClickListener { dialogInterface, i ->

                    detail.get(pos).ProdukData.Harga = Integer.parseInt(if (qty.text.toString() == "") "0" else qty.text.toString())
                    detail.get(pos).SetHargaAll(Integer.parseInt(if (qty.text.toString() == "") "0" else qty.text.toString()))

                    setDetailAdapter(ListDetail,newTrans.ListDetail)

                    dialogInterface.dismiss()
                })
                .setNegativeButton(lang.addTransDialogLang.cancel,null)
                .create()




        dialogEditQty.setView(v)
        dialogEditQty.show()
    }

    internal fun OpenProductList(){

        val productdialog = AlertDialog.Builder(context)
                .setTitle(lang.addTransDialogLang.addProduct)
                .setNegativeButton(lang.addTransDialogLang.tutup, DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                }).create()

        val inflater = (context as Activity).layoutInflater
        val v = inflater.inflate(R.layout.custom_alert_dialog_add_product_trans,null)

        val list : ListView = v.findViewById(R.id.ListView_dialog_add_product_trans)
        val textEmpty : TextView = v.findViewById(R.id.ListView_dialog_add_product_trans_isEmpty)
        textEmpty.setText(lang.subMenuProdukLang.ProdukKosong)
        textEmpty.setTextColor(theme.BackGroundColor)

        val adapter = CustomAdapterListProduk(context, R.layout.custom_adapter_listproduk,MainData.ListProdukData)
        adapter.SetLangTheme(lang,theme)
        list.adapter = adapter

        if (MainData.ListProdukData.size < 1){
            textEmpty.visibility = View.VISIBLE
            list.visibility = View.GONE
        }

        list.setOnItemClickListener { adapterView, view, i, l ->

            LinearLayoutListViewDetailAddTrans.visibility = View.VISIBLE

            val detailAdded = DetailTransaksi()
            val productAdded = ProdukData()
            productAdded.IdProduk = MainData.ListProdukData.get(i).IdProduk
            productAdded.Nama = MainData.ListProdukData.get(i).Nama
            productAdded.Harga = MainData.ListProdukData.get(i).Harga

            val id = IdGenerator()
            id.CreateRandomString(15)
            val idDetail = id.GetId()

            detailAdded.IdDetailTransaksiData = idDetail

            detailAdded.IdTransaksiData = newTrans.IdTransaksiData

            detailAdded.ProdukData = productAdded

            val ListKuantitas = ArrayList<KuantitasTransaksi>()
            val KuantitasData = KuantitasTransaksi()
            KuantitasData.IdDetailTransaksiData = idDetail
            KuantitasData.Quantity = 1
            KuantitasData.Harga = detailAdded.ProdukData.Harga
            KuantitasData.Total = KuantitasData.Harga * KuantitasData.Quantity
            ListKuantitas.add(KuantitasData)

            detailAdded.ListKuantitas = ListKuantitas

            if (CheckAndAddQtyIfSame(newTrans.ListDetail,detailAdded)){
                newTrans.ListDetail.add(detailAdded)
            }

            setDetailAdapter(ListDetail,newTrans.ListDetail)

            productdialog.dismiss()
        }

        productdialog.setView(v)
        productdialog.show()


    }

    fun setDetailAdapter(l : ListView, datas : ArrayList<DetailTransaksi>){

        for (d in datas) {
            d.IsThisValidDetailTransaction = true
            val checkDuluQty = (ResFunction.GetTotalQtyProductFromAllTrans(newTrans,d.ProdukData, MainData.ListTransaksiData))
            if (((checkDuluQty - d.GetKuantitas()) < 0 && newTrans.ProductFlow == TransaksiData.ProductOut)) {
                d.IsThisValidDetailTransaction = false
            }
        }

        val adapter = CustomAdapterDetailTransaction(context, R.layout.custom_adapter_detail_trans,datas)
        adapter.SetLangTheme(lang,theme)
        l.adapter = adapter

        LinearLayoutListViewDetailAddTrans.layoutParams.height = GetListViewTotalHeight.getListViewTotalHeight(l)
    }

    internal fun SetAdapter(){
        CheckAndMarkTransactionWithNonValidQty(listViewTransactionDatas)
        val adapter = CustomAdapterTransaksi(context,R.layout.custom_adapter_transaksi,listViewTransactionDatas)
        adapter.SetLangTheme(lang,theme)
        listViewTransaction.adapter = adapter
        if (TransaksiMenu.CheckValidQuantityProductInAllTransaction(listViewTransactionDatas)){
            TransaksiMenu.dialogTransNotValid(context, lang)
        }
    }
}