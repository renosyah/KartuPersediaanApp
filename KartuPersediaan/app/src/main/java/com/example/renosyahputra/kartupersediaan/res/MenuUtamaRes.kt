package com.example.renosyahputra.kartupersediaan.res

import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import android.support.design.widget.NavigationView
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.res.customAlertDialog.produk.CustomAlertDialogAddProduk
import com.example.renosyahputra.kartupersediaan.res.customAlertDialog.transaksi.CustomAlertDialogAddTransaction
import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.subMenu.produkMenu.ProdukMenu
import com.example.renosyahputra.kartupersediaan.subMenu.transaksiMenu.TransaksiMenu
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.example.renosyahputra.kartupersediaan.ui.theme.obj.ThemeObj

class MenuUtamaRes{
    companion object {
       fun AddTransaksi(ctx : Context,MainData : KartuPersediaanData, lang : LangObj, theme: ThemeObj, nav_view : NavigationView, Toolbar: android.support.v7.widget.Toolbar, FragmentChanger : FragmentManager, transMenu : TransaksiMenu){
            val dialog = CustomAlertDialogAddTransaction(ctx, R.layout.custom_alert_dialog_add_trans,MainData)
           dialog.SetLangTheme(lang,theme)
           dialog.SetOtherVar(nav_view,Toolbar,FragmentChanger,transMenu)
           dialog.Initiated()
        }
        fun AddProduk(ctx : Context, ListProduk : ArrayList<ProdukData>, lang : LangObj, theme: ThemeObj, nav_view : NavigationView, Toolbar: android.support.v7.widget.Toolbar, FragmentChanger : FragmentManager, produkMenu : ProdukMenu){
            val dialog = CustomAlertDialogAddProduk(ctx, R.layout.custom_alert_dialog_add_produk, ListProduk)
            dialog.SetLangTheme(lang,theme)
            dialog.SetOtherVar(nav_view,Toolbar,FragmentChanger,produkMenu)
            dialog.Initiated()
        }
        class DummyFragment : Fragment(){

        }
    }
}