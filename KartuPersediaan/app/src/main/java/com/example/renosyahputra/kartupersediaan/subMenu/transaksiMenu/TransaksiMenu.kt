package com.example.renosyahputra.kartupersediaan.subMenu.transaksiMenu

import android.app.Fragment
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.res.customAdapter.CustomAdapterTransaksi
import com.example.renosyahputra.kartupersediaan.res.customAlertDialog.transaksi.CustomAlertDialogEditTrans
import com.example.renosyahputra.kartupersediaan.res.customAlertDialog.transaksi.ResFunction
import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.example.renosyahputra.kartupersediaan.ui.theme.obj.ThemeObj

class TransaksiMenu : Fragment(), AdapterView.OnItemClickListener,TextWatcher,SwipeRefreshLayout.OnRefreshListener {



    lateinit var ctx : Context
    lateinit var v : View

    internal lateinit var lang : LangObj
    internal lateinit var theme: ThemeObj

    internal lateinit var CariTransaksi : EditText
    internal lateinit var TransaksiKosong : TextView

    internal lateinit var refreshTrans : SwipeRefreshLayout

    internal lateinit var ListViewTransaksi : ListView

    lateinit var ListTransaksi : ArrayList<TransaksiData>
    lateinit var ListTransaksiCari : ArrayList<TransaksiData>

    fun SetListTransaksi(ListTransaksi : ArrayList<TransaksiData>){
        this.ListTransaksi = ListTransaksi
    }


    fun SetLangTheme(lang : LangObj, theme: ThemeObj){
        this.lang = lang
        this.theme = theme
    }

    lateinit var MainData : KartuPersediaanData

    fun SetMainData(MainData : KartuPersediaanData){
        this.MainData = MainData
    }

    internal lateinit var FloatingButton : com.melnykov.fab.FloatingActionButton

    fun setFloatingButton(f  : com.melnykov.fab.FloatingActionButton){
        this.FloatingButton = f

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        v = inflater!!.inflate(R.layout.transaksi_menu,container,false)
        InitiationWidget(v)
        return v
    }

    internal fun InitiationWidget(v : View){
        ctx = activity

        ListTransaksiCari = ArrayList()

        refreshTrans = v.findViewById(R.id.refreshTrans)
        ListViewTransaksi = v.findViewById(R.id.LisviewTransaksi)
        TransaksiKosong = v.findViewById(R.id.StatusTransaksiListKosong)
        CariTransaksi = v.findViewById(R.id.CariTransaksiForm)


        CariTransaksi.setHint(lang.subMenuTransLang.CariTrans)
        TransaksiKosong.setTextColor(theme.BackGroundColor)

        SetAdapter(ListTransaksi)
        CheckTransKosong()

        ListViewTransaksi.setOnItemClickListener(this)
        FloatingButton.attachToListView(ListViewTransaksi)
        CariTransaksi.addTextChangedListener(this)
        refreshTrans.setOnRefreshListener(this)

    }

    internal fun CheckTransKosong(){

        if (ListTransaksi.size < 1){

            TransaksiKosong.visibility = View.VISIBLE
            TransaksiKosong.setText(lang.subMenuTransLang.TransKosong)
            ListViewTransaksi.visibility = View.GONE

        }else {

            TransaksiKosong.visibility = View.GONE
            ListViewTransaksi.visibility = View.VISIBLE
        }
    }

    override fun onRefresh() {
        SetAdapter(ListTransaksi)
        if (refreshTrans.isRefreshing){
            refreshTrans.isRefreshing = !refreshTrans.isRefreshing
        }
    }
    internal fun SetAdapter(l : ArrayList<TransaksiData>){
        val newL = CheckAndMarkTransactionWithNonValidQty(l)
        val adapter = CustomAdapterTransaksi(ctx,R.layout.custom_adapter_transaksi,newL)
        adapter.SetLangTheme(lang,theme)
        ListViewTransaksi.adapter = adapter
        ListViewTransaksi.divider = null
    }



    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        if (ListTransaksiCari.size < 1 && ListTransaksi.size >= 1){

            OpenDialog(ListTransaksi.get(p2))

        }else if (ListTransaksiCari.size >= 1){

            OpenDialog(ListTransaksiCari.get(p2))

        }
    }

    internal fun FindRealTransDataAndEdit(MainData: KartuPersediaanData, Transaksi: TransaksiData){
        var ketemu = false
        var pos = 0

        for (i in 0..(ListTransaksi.size)-1){
            if (ListTransaksi.get(i).IdTransaksiData == Transaksi.IdTransaksiData){
                ketemu = true
                pos = i
                break
            }
        }

        if (ketemu){
            OpenDialogEdit(MainData,ListTransaksi,pos)
        }
        CheckTransKosong()
    }

    internal fun OpenDialogEdit(MainData: KartuPersediaanData, ListTransaksi: ArrayList<TransaksiData>, pos : Int){


        val dialogEdit = CustomAlertDialogEditTrans(ctx,R.layout.custom_alert_dialog_add_trans,MainData,ListTransaksi.get(pos).CloneTransData(),pos)
        dialogEdit.SetLangTheme(lang,theme)
        dialogEdit.SetOtherVar(fragmentManager)
        dialogEdit.RefreshAdapter(ListViewTransaksi,ListTransaksi)
        dialogEdit.Initiated()
    }

    internal fun OpenDialogHapus(Transaksi: TransaksiData){
        var ketemu = false
        var pos = 0

        for (i in 0..(ListTransaksi.size)-1){
            if (ListTransaksi.get(i).IdTransaksiData == Transaksi.IdTransaksiData){
                ketemu = true
                pos = i
                break
            }
        }

        if (ketemu){
            ListTransaksi.removeAt(pos)
            SetAdapter(ListTransaksi)
        }
    }

    internal fun OpenDialog(transData : TransaksiData){

        val optionLang = arrayOf<CharSequence>(lang.subMenuTransLang.edit,lang.subMenuTransLang.hapus)
        AlertDialog.Builder(ctx)
                .setTitle(transData.Keterangan)
                .setItems(optionLang, DialogInterface.OnClickListener { dialogInterface, i ->
                    when (i){
                        0 -> {
                            FindRealTransDataAndEdit(MainData,transData)
                        }
                        1 -> {
                            OpenDialogHapus(transData)
                        }

                    }


                    dialogInterface.dismiss()
                })
                .setNegativeButton(lang.subMenuTransLang.batal, DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
                .create().show()
    }

    override fun afterTextChanged(p0: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        ListTransaksiCari.clear()

        if (ListTransaksi.size >= 1){
            for (d in ListTransaksi){
                if (d.Keterangan.matches(("(?i).*" + CariTransaksi.text.toString() + "(.*)").toRegex())){
                    ListTransaksiCari.add(d)
                }
            }
            if (ListTransaksiCari.size < 1){

                TransaksiKosong.visibility = View.VISIBLE
                TransaksiKosong.setText(lang.subMenuTransLang.TransTidakKetemu)
                ListViewTransaksi.visibility = View.GONE

            }else {

                TransaksiKosong.visibility = View.GONE
                ListViewTransaksi.visibility = View.VISIBLE
            }
            SetAdapter(ListTransaksiCari)
        }
    }

    fun CheckAndMarkTransactionWithNonValidQty(l : ArrayList<TransaksiData>) : ArrayList<TransaksiData>{
        for (t in l.listIterator()){
            for (d in t.ListDetail.listIterator()){
                val checkDuluQty = (ResFunction.GetTotalQtyProductFromAllTrans(t,d.ProdukData, l))
                t.IsThisValidTransaction = !((checkDuluQty - d.GetKuantitas()) < 0 && t.ProductFlow == TransaksiData.ProductOut)
                d.IsThisValidDetailTransaction = !((checkDuluQty - d.GetKuantitas()) < 0 && t.ProductFlow == TransaksiData.ProductOut)
            }

        }
        return l
    }
}