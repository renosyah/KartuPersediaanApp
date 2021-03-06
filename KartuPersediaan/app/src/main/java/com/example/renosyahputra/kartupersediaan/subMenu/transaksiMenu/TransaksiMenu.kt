package com.example.renosyahputra.kartupersediaan.subMenu.transaksiMenu

import android.app.Fragment
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
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
import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData
import com.example.renosyahputra.kartupersediaan.subMenu.transaksiMenu.res.TransaksiMenuRes
import com.example.renosyahputra.kartupersediaan.ui.developerMode.DataDevMod
import com.example.renosyahputra.kartupersediaan.ui.developerMode.DeveloperMode
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

    lateinit var ListTransaksiCari : ArrayList<TransaksiData>

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

        SetAdapter(true,MainData.ListTransaksiData)
        CheckTransKosong()

        ListViewTransaksi.setOnItemClickListener(this)
        FloatingButton.attachToListView(ListViewTransaksi)
        CariTransaksi.addTextChangedListener(this)
        refreshTrans.setOnRefreshListener(this)

    }

    internal fun CheckTransKosong(){

        if (MainData.ListTransaksiData.size < 1){

            TransaksiKosong.visibility = View.VISIBLE
            TransaksiKosong.setText(lang.subMenuTransLang.TransKosong)
            ListViewTransaksi.visibility = View.GONE
            CariTransaksi.visibility = View.GONE
        }else {
            CariTransaksi.visibility = View.VISIBLE
            TransaksiKosong.visibility = View.GONE
            ListViewTransaksi.visibility = View.VISIBLE
        }
    }

    override fun onRefresh() {
        ListTransaksiCari.clear()
        TransaksiMenuRes.SortTransactionDataByDateAndTime(MainData)
        SetAdapter(true,MainData.ListTransaksiData)
        if (refreshTrans.isRefreshing){
            refreshTrans.isRefreshing = !refreshTrans.isRefreshing
        }
    }
    internal fun SetAdapter(IsThisNotSearch  :Boolean,l : ArrayList<TransaksiData>){

        if (IsThisNotSearch ){
            TransaksiMenuRes.CheckAndMarkTransactionWithNonValidQty(l)
        }

        val adapter = CustomAdapterTransaksi(ctx,R.layout.custom_adapter_transaksi,l)
        adapter.SetLangTheme(lang,theme)
        ListViewTransaksi.adapter = adapter
        ListViewTransaksi.divider = null

        if (TransaksiMenuRes.CheckValidQuantityProductInAllTransaction(l) && IsThisNotSearch ){
            TransaksiMenuRes.dialogTransNotValid(ctx,lang)
        }
    }



    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        if (ListTransaksiCari.size < 1 && MainData.ListTransaksiData.size >= 1){

            OpenDialog(MainData.ListTransaksiData.get(p2))

        }else if (ListTransaksiCari.size >= 1){

            OpenDialog(ListTransaksiCari.get(p2))

        }
    }

    internal fun FindRealTransDataAndEdit(MainData: KartuPersediaanData, Transaksi: TransaksiData){
        var ketemu = false
        var pos = 0

        for (i in 0..(MainData.ListTransaksiData.size)-1){
            if (MainData.ListTransaksiData.get(i).IdTransaksiData == Transaksi.IdTransaksiData){
                ketemu = true
                pos = i
                break
            }
        }

        if (ketemu){
            OpenDialogEdit(MainData,MainData.ListTransaksiData,pos)
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

    internal fun DialogHapusTransaksi(context: Context,MainlistTrans : ArrayList<TransaksiData>,Transaksi: TransaksiData){
        AlertDialog.Builder(context)
                .setTitle(lang.subMenuTransLang.titleHapusTransaksi)
                .setMessage(lang.subMenuTransLang.messageHapusTransaksi)
                .setPositiveButton(lang.subMenuTransLang.yesHapus, DialogInterface.OnClickListener { dialogInterface, i ->
                    HapusTransaksi(MainlistTrans,Transaksi)
                    dialogInterface.dismiss()
                })
                .setNegativeButton(lang.subMenuTransLang.noHapus, DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
                .create()
                .show()
    }

    internal fun HapusTransaksi(MainlistTrans : ArrayList<TransaksiData>,Transaksi: TransaksiData){
        var ketemu = false
        var pos = 0

        for (i in 0..(MainlistTrans.size)-1){
            if (MainlistTrans.get(i).IdTransaksiData == Transaksi.IdTransaksiData){
                ketemu = true
                pos = i
                break
            }
        }

        if (ketemu){
            MainlistTrans.removeAt(pos)
            ListTransaksiCari.clear()
            CheckTransKosong()
            SetAdapter(true,MainlistTrans)
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
                            DialogHapusTransaksi(ctx,MainData.ListTransaksiData,transData)
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


        if (MainData.ListTransaksiData.size >= 1){
            for (d in MainData.ListTransaksiData){
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
            SetAdapter(false,ListTransaksiCari)
        }

        if (CariTransaksi.text.toString() == DataDevMod.devmodString){
            CariTransaksi.setText("")
            val inten = Intent(ctx, DeveloperMode::class.java)
            inten.putExtra("lang",lang)
            inten.putExtra("theme",theme)
            ctx.startActivity(inten)
        }
    }
}