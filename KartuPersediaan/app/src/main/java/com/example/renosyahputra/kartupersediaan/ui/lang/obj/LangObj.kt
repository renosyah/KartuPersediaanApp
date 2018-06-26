package com.example.renosyahputra.kartupersediaan.ui.lang.obj

import com.example.renosyahputra.kartupersediaan.ui.lang.obj.addProdukFormLang.AddProdukFormLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.addTransDialogLang.AddTransDialogLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.editProdukFormLang.EditProdukFormLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.guideLang.GuideLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.laporanMenuLang.LaporanMenuLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.loginDialogLang.LoginDialogLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.mainMenuAddTaskLang.MainMenuAddTaskLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.mainMenuLang.MainMenuLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.mainMenuSettingLang.MainMenuSettingLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.montInString.MonthInString
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.printLaporanLang.PrintLaporanLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.registerLang.RegisterLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.subMenuProdukLang.SubMenuProdukLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.subMenuTransLang.SubMenuTransLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.userDataSettingLang.UserDataSettingLang
import java.io.Serializable

class LangObj : Serializable {
    lateinit var mainMenuLang : MainMenuLang
    lateinit var mainMenuSettingLang : MainMenuSettingLang
    lateinit var mainMenuAddTaskLang : MainMenuAddTaskLang
    lateinit var subMenuProdukLang : SubMenuProdukLang
    lateinit var addProdukFormLang : AddProdukFormLang
    lateinit var editProdukFormLang : EditProdukFormLang
    lateinit var subMenuTransLang : SubMenuTransLang
    lateinit var addTransDialogLang : AddTransDialogLang
    lateinit var laporanMenuLang : LaporanMenuLang
    lateinit var printLaporanLang : PrintLaporanLang
    lateinit var registerLang : RegisterLang
    lateinit var monthInString : MonthInString
    lateinit var userDataSettingLang : UserDataSettingLang
    lateinit var loginDialogLang : LoginDialogLang
    lateinit var guideLang : GuideLang
}