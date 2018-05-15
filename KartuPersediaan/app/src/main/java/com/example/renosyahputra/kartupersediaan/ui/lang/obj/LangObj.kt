package com.example.renosyahputra.kartupersediaan.ui.lang.obj

import com.example.renosyahputra.kartupersediaan.ui.lang.obj.addProdukFormLang.AddProdukFormLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.addTransDialogLang.AddTransDialogLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.editProdukFormLang.EditProdukFormLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.laporanMenuLang.LaporanMenuLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.loginLang.LoginLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.mainMenuAddTaskLang.MainMenuAddTaskLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.mainMenuLang.MainMenuLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.mainMenuSettingLang.MainMenuSettingLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.printLaporanLang.PrintLaporanLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.subMenuProdukLang.SubMenuProdukLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.subMenuTransLang.SubMenuTransLang
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
    lateinit var loginLang : LoginLang
}