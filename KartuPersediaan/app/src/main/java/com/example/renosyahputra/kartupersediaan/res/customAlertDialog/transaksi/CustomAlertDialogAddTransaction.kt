package com.example.renosyahputra.kartupersediaan.res.customAlertDialog.transaksi

import android.app.Activity
import android.app.FragmentManager
import android.content.Context
import android.content.DialogInterface
import android.support.design.widget.NavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.*
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.res.IdGenerator
import com.example.renosyahputra.kartupersediaan.res.customAdapter.CustomAdapterDetailTransaction
import com.example.renosyahputra.kartupersediaan.res.customAdapter.CustomAdapterListProduk
import com.example.renosyahputra.kartupersediaan.res.customAlertDialog.transaksi.ResFunction.Companion.CheckAndAddQtyIfSame
import com.example.renosyahputra.kartupersediaan.res.customAlertDialog.transaksi.ResFunction.Companion.FinalQtyCheckInNewTrans
import com.example.renosyahputra.kartupersediaan.res.customAlertDialog.transaksi.ResFunction.Companion.GetTotalQtyProductFromAllTrans
import com.example.renosyahputra.kartupersediaan.res.customAlertDialog.transaksi.ResFunction.Companion.getTotalFromDetail
import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.*
import com.example.renosyahputra.kartupersediaan.subMenu.transaksiMenu.TransaksiMenu
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.example.renosyahputra.kartupersediaan.ui.theme.obj.ThemeObj
import com.example.renosyahputra.quicktrans.ui.GetListViewTotalHeight
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import java.util.*
import kotlin.collections.ArrayList

class CustomAlertDialogAddTransaction(ctx : Context,res : Int,Data : KartuPersediaanData) : View.OnClickListener,AdapterView.OnItemClickListener {


    val context = ctx
    val resources = res
    val MainData = Data

    lateinit var dialog : AlertDialog

    internal lateinit var Toolbar: Toolbar
    internal lateinit var FragmentChanger : FragmentManager
    internal lateinit var transMenu : TransaksiMenu
    internal lateinit var nav_view : NavigationView


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

    lateinit var newTrans : TransaksiData

    fun SetOtherVar(nav_view : NavigationView, Toolbar: Toolbar, FragmentChanger : FragmentManager,transMenu : TransaksiMenu){
        this.nav_view = nav_view
        this.Toolbar = Toolbar
        this.FragmentChanger = FragmentChanger
        this.transMenu = transMenu
    }

    internal lateinit var lang : LangObj
    internal lateinit var theme: ThemeObj

    fun SetLangTheme(lang : LangObj, theme: ThemeObj){
        this.lang = lang
        this.theme = theme
    }

    internal fun InitiationWidget(v : View){

        val randId = IdGenerator()
        randId.CreateRandomString(15)

        newTrans = TransaksiData()
        newTrans.IdTransaksiData = randId.GetId()
        newTrans.TanggalTransaksi = FormatTanggal()
        newTrans.Jam = FormatWaktu()
        newTrans.ListDetail = ArrayList()
        newTrans.Keterangan = ""
        newTrans.SubTotal = 0
        newTrans.ProductFlow = ""

        LinearLayoutListViewDetailAddTrans = v.findViewById(R.id.LinearLayoutListViewDetailAddTrans)

        bar = v.findViewById(R.id.titleBarAddTrans)
        bar.setBackgroundColor(theme.BackGroundColor)

        title = v.findViewById(R.id.titleAddTrans)
        title.setText(lang.addTransDialogLang.title)

        butttonChooseType = v.findViewById(R.id.buttonSellectTypeTrans)
        butttonChooseType.setText(lang.addTransDialogLang.chooseType)
        butttonChooseType.setTextColor(theme.TextColor)
        butttonChooseType.setBackgroundColor(theme.BackGroundColor)

        chooseDate = v.findViewById(R.id.ChooseDateAddTrans)
        chooseDate.setText(lang.addTransDialogLang.chooseData)
        chooseDate.setTextColor(theme.BackGroundColor)

        chooseTime = v.findViewById(R.id.ChooseTimeAddTrans)
        chooseTime.setText(lang.addTransDialogLang.chooseTime)
        chooseTime.setTextColor(theme.BackGroundColor)

        inputInfo = v.findViewById(R.id.AddTransInformationForm)
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

        LinearLayoutListViewDetailAddTrans.visibility = View.GONE
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

                            chooseDate.setText(newTrans.TanggalTransaksi.toDateString())

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

                val option = arrayOf<CharSequence>(TransaksiData.ProductIn,TransaksiData.ProductOut)
                android.support.v7.app.AlertDialog.Builder(context)
                        .setTitle(lang.addTransDialogLang.chooseType)
                        .setItems(option, DialogInterface.OnClickListener { dialogInterface, i ->
                            if (i == 0){

                                newTrans.ProductFlow = TransaksiData.ProductIn

                            }else if (i == 1){

                                newTrans.ProductFlow = TransaksiData.ProductOut

                            }

                            butttonChooseType.setText(newTrans.ProductFlow)

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
                    if (FinalQtyCheckInNewTrans(newTrans,detailFinalCheck.ProdukData,newTrans.ListDetail,MainData.ListTransaksiData)){
                        finalCheckQtyIsMin = true
                        break
                    }

                }

                if (finalCheckQtyIsMin && newTrans.ProductFlow == TransaksiData.ProductOut){
                    Toast.makeText(context,lang.addTransDialogLang.warningThereIsProductQtyLow,Toast.LENGTH_SHORT).show()
                    return
                }


                MainData.ListTransaksiData.add(newTrans)


                Toolbar.setTitle(lang.mainMenuLang.subMenu1)


                FragmentChanger.beginTransaction().replace(R.id.MainMenuFrameLaout, transMenu).commit()
                FragmentChanger.beginTransaction().detach(transMenu).attach(transMenu).commit()


                nav_view.menu.getItem(1).setChecked(true)

                dialog.dismiss()
            }
            cancel -> {
                dialog.dismiss()
            }
        }
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


        val qty : EditText = v.findViewById(R.id.editQtyDetail)
        qty.setText(detail.get(pos).GetKuantitas().toString())

        val dialogEditQty = AlertDialog.Builder(context)
                .setTitle("Edit "+ detail.get(pos).ProdukData.Nama +" "+lang.addTransDialogLang.qty)
                .setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i ->

                    val checkDuluQty = (GetTotalQtyProductFromAllTrans(newTrans,detail.get(pos).ProdukData,MainData.ListTransaksiData))

                    if ((checkDuluQty - Integer.parseInt(qty.text.toString())) < 0 && newTrans.ProductFlow == TransaksiData.ProductOut){

                        Toast.makeText(context,lang.addTransDialogLang.warningProductQtyLow + " = " + checkDuluQty,Toast.LENGTH_SHORT).show()

                    }else {

                        val ListKuantitas = ArrayList<KuantitasTransaksi>()
                        val KuantitasData = KuantitasTransaksi()

                        val id = IdGenerator()
                        id.CreateRandomString(15)
                        detail.get(pos).IdDetailTransaksiData = id.GetId()

                        KuantitasData.IdDetailTransaksiData = detail.get(pos).IdDetailTransaksiData
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

        val dialogEditQty = AlertDialog.Builder(context)
                .setTitle("Edit "+ detail.get(pos).ProdukData.Nama +" "+lang.addTransDialogLang.price)
                .setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i ->

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

        val adapter = CustomAdapterListProduk(context,R.layout.custom_adapter_listproduk,MainData.ListProdukData)
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

            detailAdded.IdTransaksiData = newTrans.IdTransaksiData
            detailAdded.IdDetailTransaksiData = id.GetId()

            detailAdded.ProdukData = productAdded

            val ListKuantitas = ArrayList<KuantitasTransaksi>()
            val KuantitasData = KuantitasTransaksi()
            KuantitasData.IdDetailTransaksiData = detailAdded.IdDetailTransaksiData
            KuantitasData.Quantity = 1
            KuantitasData.Harga = detailAdded.ProdukData.Harga
            KuantitasData.Total = KuantitasData.Harga * KuantitasData.Quantity
            ListKuantitas.add(KuantitasData)

            detailAdded.ListKuantitas = ListKuantitas

            detailAdded.ListPersediaanData = ArrayList()

            if (CheckAndAddQtyIfSame(newTrans.ListDetail,detailAdded)){
                newTrans.ListDetail.add(detailAdded)
            }

            setDetailAdapter(ListDetail,newTrans.ListDetail)

            productdialog.dismiss()
        }

        productdialog.setView(v)
        productdialog.show()


    }

    fun setDetailAdapter(l : ListView,datas : ArrayList<DetailTransaksi>){
        val adapter = CustomAdapterDetailTransaction(context,R.layout.custom_adapter_detail_trans,datas)
        adapter.SetLangTheme(lang,theme)
        l.adapter = adapter

        LinearLayoutListViewDetailAddTrans.layoutParams.height = GetListViewTotalHeight.getListViewTotalHeight(l)
    }



}