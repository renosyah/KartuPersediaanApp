package com.example.renosyahputra.kartupersediaan.res

import android.app.Activity
import android.app.FragmentManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.design.widget.NavigationView
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.register.Register
import com.example.renosyahputra.kartupersediaan.res.customAlertDialog.produk.CustomAlertDialogAddProduk
import com.example.renosyahputra.kartupersediaan.res.customAlertDialog.transaksi.CustomAlertDialogAddTransaction
import com.example.renosyahputra.kartupersediaan.res.customAlertDialog.userSetting.CustomAlertDialogUserSetting
import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.res.obj.user.UserData
import com.example.renosyahputra.kartupersediaan.storage.local.SaveMainData
import com.example.renosyahputra.kartupersediaan.storage.local.SaveUserData
import com.example.renosyahputra.kartupersediaan.storage.restFull.saveAllData.SaveAllData
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


        fun DialogSaveDataOnline(context: Context,userData: UserData,nav_view: NavigationView,kartuPersediaanData: KartuPersediaanData,lang: LangObj,theme: ThemeObj){

            AlertDialog.Builder(context)
                    .setTitle(lang.saveOnlineDialogLang.title)
                    .setMessage(lang.saveOnlineDialogLang.message)
                    .setPositiveButton(lang.saveOnlineDialogLang.save, DialogInterface.OnClickListener { dialogInterface, i ->
                        if (userData.UserName == "" || userData.Password == ""){

                            Toast.makeText(context,lang.mainMenuLang.SaveDataOnlineButAccountNotValid, Toast.LENGTH_SHORT).show()
                            OpenUserProfilSetting(context,userData,nav_view,lang,theme)

                        } else {

                            SaveAllData(context,kartuPersediaanData,userData,lang,theme).execute()
                        }
                        dialogInterface.dismiss()
                    })
                    .setNegativeButton(lang.saveOnlineDialogLang.cancel, DialogInterface.OnClickListener { dialogInterface, i ->
                        dialogInterface.dismiss()
                    })
                    .create()
                    .show()

        }


        fun OpenUserProfilSetting(context: Context,userData: UserData,nav_view: NavigationView,lang: LangObj,theme: ThemeObj){
            val userSetting = CustomAlertDialogUserSetting(context,userData)
            userSetting.SetNavBar(nav_view)
            userSetting.SetLangTheme(lang,theme)
            userSetting.InitiationDialog()
        }

        fun OpenDialogConfirmLogout(context: Context,userData: UserData,kartuPersediaanData: KartuPersediaanData,lang: LangObj,theme: ThemeObj){

            AlertDialog.Builder(context)
                    .setTitle(lang.logoutDialogLang.title)
                    .setMessage(lang.logoutDialogLang.message)
                    .setPositiveButton(lang.logoutDialogLang.logout, DialogInterface.OnClickListener { dialogInterface, i ->

                        dialogInterface.dismiss()

                        val save = SaveMainData(context,kartuPersediaanData)
                        val user = SaveUserData(context,userData)
                        if (save.Delete() && user.Delete()){

                            val intent = Intent(context, Register::class.java)
                            context.startActivity(intent)
                            (context as Activity).finish()

                        }


                    })
                    .setNegativeButton(lang.logoutDialogLang.cancel, DialogInterface.OnClickListener { dialogInterface, i ->
                        dialogInterface.dismiss()
                    })
                    .create()
                    .show()
        }
    }
}