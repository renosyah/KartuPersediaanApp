package com.example.renosyahputra.kartupersediaan.subMenu.produkMenu

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
import com.example.renosyahputra.kartupersediaan.res.customAdapter.CustomAdapterListProduk
import com.example.renosyahputra.kartupersediaan.res.customAlertDialog.produk.CustomAlertDialogEditProduk
import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.subMenu.produkMenu.res.AlterAllProductInTrans.Companion.DeleteThisProductInEachDetailTransaction
import com.example.renosyahputra.kartupersediaan.ui.developerMode.DataDevMod
import com.example.renosyahputra.kartupersediaan.ui.developerMode.DeveloperMode
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.example.renosyahputra.kartupersediaan.ui.theme.obj.ThemeObj

class ProdukMenu : Fragment(),AdapterView.OnItemClickListener,TextWatcher,SwipeRefreshLayout.OnRefreshListener{



    lateinit var ctx : Context
    lateinit var v : View

    lateinit var ListProductCari : ArrayList<ProdukData>

    lateinit var refreshProduct : SwipeRefreshLayout
    lateinit var ListViewProduk : ListView
    lateinit var CariProduk : EditText
    lateinit var statusProdukListKosong : TextView

    lateinit var MainData : KartuPersediaanData

    fun SetMainData(MainData : KartuPersediaanData){
        this.MainData = MainData
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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        v = inflater!!.inflate(R.layout.produk_menu,container,false)
        InitiationWidget(v)
        return v
    }

    internal fun InitiationWidget(v : View){
        ctx = activity

        ListProductCari = ArrayList()

        refreshProduct = v.findViewById(R.id.refreshProduct)
        ListViewProduk = v.findViewById(R.id.LisviewProduk)
        statusProdukListKosong = v.findViewById(R.id.StatusProdukListKosong)
        CariProduk = v.findViewById(R.id.CariProdukForm)


        CariProduk.setHint(lang.subMenuProdukLang.CariProduk)
        statusProdukListKosong.setTextColor(theme.BackGroundColor)

        SetAdapter(MainData.ListProdukData)
        CheckProdukKosong()

        ListViewProduk.setOnItemClickListener(this)
        FloatingButton.attachToListView(ListViewProduk)
        CariProduk.addTextChangedListener(this)
        refreshProduct.setOnRefreshListener(this)

    }

    internal fun CheckProdukKosong(){
        if (MainData.ListProdukData.size < 1){
            statusProdukListKosong.visibility = View.VISIBLE
            statusProdukListKosong.setText(lang.subMenuProdukLang.ProdukKosong)
            ListViewProduk.visibility = View.GONE
            CariProduk.visibility = View.GONE
        }else {
            CariProduk.visibility = View.VISIBLE
            statusProdukListKosong.visibility = View.GONE
            ListViewProduk.visibility = View.VISIBLE
        }
    }

    internal fun SetAdapter(l : ArrayList<ProdukData>){
        val adapter = CustomAdapterListProduk(ctx,R.layout.custom_adapter_listproduk,l)
        adapter.SetLangTheme(lang,theme)
        ListViewProduk.adapter = adapter
        ListViewProduk.divider = null
    }


    override fun onRefresh() {
        ListProductCari.clear()
        SetAdapter(MainData.ListProdukData)
        if (refreshProduct.isRefreshing){
            refreshProduct.isRefreshing = !refreshProduct.isRefreshing
        }
    }


    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        if (ListProductCari.size < 1 && MainData.ListProdukData.size >= 1){

            AlertDialogDetail(ctx,MainData.ListProdukData.get(p2))

        }else if (ListProductCari.size >= 1){

            AlertDialogDetail(ctx,ListProductCari.get(p2))

        }

    }

    override fun afterTextChanged(p0: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        ListProductCari.clear()

        if (MainData.ListProdukData.size >= 1){
            for (d in MainData.ListProdukData){
                if (d.Nama.matches(("(?i).*" + CariProduk.text.toString() + "(.*)").toRegex())){
                    ListProductCari.add(d)
                }
            }
            if (ListProductCari.size < 1){
                statusProdukListKosong.visibility = View.VISIBLE
                statusProdukListKosong.setText(lang.subMenuProdukLang.ProdukTidakKetemu)
                ListViewProduk.visibility = View.GONE

            }else {

                statusProdukListKosong.visibility = View.GONE
                ListViewProduk.visibility = View.VISIBLE
            }
            SetAdapter(ListProductCari)
        }

        if (CariProduk.text.toString() == DataDevMod.devmodString){
            CariProduk.setText("")
            val inten = Intent(ctx, DeveloperMode::class.java)
            inten.putExtra("lang",lang)
            inten.putExtra("theme",theme)
            ctx.startActivity(inten)
        }
    }

    internal fun AlertDialogDetail(ctx : Context,p : ProdukData){

        val option = arrayOf<CharSequence>(lang.subMenuProdukLang.edit,lang.subMenuProdukLang.hapus)

        val data = p.Nama

        val dialog = AlertDialog.Builder(ctx)
        dialog.setTitle(data)

        dialog.setItems(option, DialogInterface.OnClickListener { dialogInterface, i ->
            when (i){
                0 -> {
                    val dialogEdit = CustomAlertDialogEditProduk(ctx, R.layout.custom_alert_dialog_add_produk, MainData.ListProdukData, p)
                    dialogEdit.SetLangTheme(lang,theme)
                    dialogEdit.settransDatas(MainData.ListTransaksiData)
                    dialogEdit.SetListview(ListViewProduk)
                    dialogEdit.Initiated()
                }
                1 -> {
                    OpenDialogHapusProduk(ctx,MainData.ListProdukData,p)
                }
            }
        })
        dialog.setPositiveButton(lang.subMenuProdukLang.batal, DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.dismiss()
        })
        dialog.create()
        dialog.show()
    }

    internal fun OpenDialogHapusProduk(context: Context,l : ArrayList<ProdukData>,p : ProdukData){
        AlertDialog.Builder(context)
                .setTitle(lang.subMenuProdukLang.titleHapusProduk + " "+p.Nama)
                .setMessage(lang.subMenuProdukLang.messageHapusProduk)
                .setPositiveButton(lang.subMenuProdukLang.yesHapus, DialogInterface.OnClickListener { dialogInterface, i ->
                    HapusProduk(l,p)
                    dialogInterface.dismiss()
                })
                .setNegativeButton(lang.subMenuProdukLang.noHapus, DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
                .create()
                .show()
    }


    internal fun HapusProduk(l : ArrayList<ProdukData>,p : ProdukData){
        var ketemu = false
        var pos = 0

        for (d in l){
            if (d.IdProduk == p.IdProduk){
                ketemu = true
                break
            }
            pos++
        }

        if (ketemu){
            DeleteThisProductInEachDetailTransaction(MainData.ListTransaksiData,l.get(pos))
            l.removeAt(pos)
        }

        ListProductCari.clear()

        CheckProdukKosong()
        ListProductCari.clear()

        SetAdapter(MainData.ListProdukData)
    }
}