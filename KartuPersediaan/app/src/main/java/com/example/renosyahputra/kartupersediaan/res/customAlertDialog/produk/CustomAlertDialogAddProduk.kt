package com.example.renosyahputra.kartupersediaan.res.customAlertDialog.produk

import android.app.Activity
import android.app.AlertDialog
import android.app.FragmentManager
import android.content.Context
import android.support.design.widget.NavigationView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.res.IdGenerator
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.subMenu.produkMenu.ProdukMenu
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.example.renosyahputra.kartupersediaan.ui.theme.obj.ThemeObj

class CustomAlertDialogAddProduk(ctx : Context,res : Int,List : ArrayList<ProdukData>) : View.OnClickListener{

    internal val context = ctx
    lateinit var dialog : AlertDialog
    var resources: Int = res
    var ListProduk : ArrayList<ProdukData> = List

    internal lateinit var lang : LangObj
    internal lateinit var theme: ThemeObj


    internal lateinit var bar : RelativeLayout
    internal lateinit var title : TextView
    internal lateinit var nama : EditText
    internal lateinit var harga : EditText
    internal lateinit var tambah : Button
    internal lateinit var batal : Button
    internal lateinit var Toolbar: Toolbar
    internal lateinit var FragmentChanger : FragmentManager
    internal lateinit var produkMenu : ProdukMenu
    internal lateinit var nav_view : NavigationView

    fun SetOtherVar(nav_view : NavigationView,Toolbar: Toolbar, FragmentChanger : FragmentManager, produkMenu : ProdukMenu){
        this.nav_view = nav_view
        this.Toolbar = Toolbar
        this.FragmentChanger = FragmentChanger
        this.produkMenu = produkMenu
    }

    fun SetLangTheme(lang : LangObj, theme: ThemeObj){
        this.lang = lang
        this.theme = theme
    }

    fun Initiated(){

        dialog = AlertDialog.Builder(context)
                .create()
        val inflater = (context as Activity).layoutInflater
        val v = inflater.inflate(resources,null)

        bar = v.findViewById(R.id.titleBarAddProduk)
        title = v.findViewById(R.id.titleAddProduk)
        nama = v.findViewById(R.id.ProdukNameForm)
        harga = v.findViewById(R.id.ProdukPriceForm)

        bar.setBackgroundColor(theme.BackGroundColor)
        title.setText(lang.addProdukFormLang.title)
        nama.setHint(lang.addProdukFormLang.nameHint)
        harga.setHint(lang.addProdukFormLang.price)

        tambah = v.findViewById(R.id.buttonAddProduk)
        batal = v.findViewById(R.id.buttonCancelAddProduk)

        tambah.setText(lang.addProdukFormLang.tambah)
        batal.setText(lang.addProdukFormLang.batal)

        tambah.setBackgroundColor(theme.BackGroundColor)
        batal.setBackgroundColor(theme.BackGroundColor)

        tambah.setTextColor(theme.TextColor)
        batal.setTextColor(theme.TextColor)

        tambah.setOnClickListener(this)
        batal.setOnClickListener(this)

        dialog.setView(v)
        dialog.show()
    }

    override fun onClick(p0: View?) {
        if (p0 == tambah){

            val produk = ProdukData()
            val id = IdGenerator()
            id.CreateRandomString(10)

            produk.Nama = nama.text.toString()
            produk.Harga = Integer.parseInt(harga.text.toString())
            produk.IdProduk = id.GetId()

            ListProduk.add(produk)

            Toolbar.setTitle(lang.mainMenuLang.subMenu3)

            FragmentChanger.beginTransaction().replace(R.id.MainMenuFrameLaout,produkMenu).commit()
            FragmentChanger.beginTransaction().detach(produkMenu).attach(produkMenu).commit()


            nav_view.menu.getItem(2).setChecked(true)

            dialog.dismiss()

        }else if (p0 == batal){

            dialog.dismiss()

        }
    }

}