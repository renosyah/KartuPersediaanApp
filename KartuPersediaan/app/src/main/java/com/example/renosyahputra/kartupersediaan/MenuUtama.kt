package com.example.renosyahputra.kartupersediaan

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.renosyahputra.kartupersediaan.loginAndRegister.Login
import com.example.renosyahputra.kartupersediaan.res.MenuUtamaRes.Companion.AddProduk
import com.example.renosyahputra.kartupersediaan.res.MenuUtamaRes.Companion.AddTransaksi
import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.laporanKartuPersediaan.FormatLaporan
import com.example.renosyahputra.kartupersediaan.res.obj.user.UserData
import com.example.renosyahputra.kartupersediaan.storage.local.SaveMainData
import com.example.renosyahputra.kartupersediaan.storage.local.SaveUserData
import com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.KartuPersediaanMenu
import com.example.renosyahputra.kartupersediaan.subMenu.produkMenu.ProdukMenu
import com.example.renosyahputra.kartupersediaan.subMenu.transaksiMenu.TransaksiMenu
import com.example.renosyahputra.kartupersediaan.ui.lang.LangSetting
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.example.renosyahputra.kartupersediaan.ui.theme.ThemeSetting
import kotlinx.android.synthetic.main.activity_menu_utama.*



class MenuUtama : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    lateinit var context : Context
    lateinit var IntentData : Intent
    lateinit var fab : FloatingActionButton
    lateinit var Toolbar : android.support.v7.widget.Toolbar
    lateinit var OptionMenu  : Menu

    val FragmentChanger = fragmentManager

    lateinit var kartuPersediaanData : KartuPersediaanData

    lateinit var userData : UserData

    lateinit var langSetting : LangSetting
    lateinit var themeSetting : ThemeSetting


    lateinit var kartuPersediaanMenu: KartuPersediaanMenu
    lateinit var produkMenu : ProdukMenu
    lateinit var transaksiMenu : TransaksiMenu

    lateinit var formatLaporan : FormatLaporan


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_utama)
        InitiationWidget()

    }

    fun InitiationWidget(){

        context = this@MenuUtama
        IntentData = intent

        userData = IntentData.getSerializableExtra("user") as UserData
        kartuPersediaanData = IntentData.getSerializableExtra("data") as KartuPersediaanData

        langSetting = LangSetting(context)
        langSetting.CheckLangSetting()

        themeSetting = ThemeSetting(context)
        themeSetting.CheckThemeSetting()

        fab = findViewById(R.id.fab)
        Toolbar = findViewById(R.id.toolbar)

        formatLaporan = FormatLaporan()
        formatLaporan.TypeFormat = FormatLaporan.BIASA

        setSupportActionBar(Toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, Toolbar , R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        nav_view.setNavigationItemSelectedListener(this)
        fab.setOnClickListener(this)


        AddContentToFrame()
        SetSlideMenuTitle(nav_view.menu,langSetting.GetlangObj())
        SetSideProfile()
        setDefault()
    }

    fun setDefault(){

        nav_view.menu.getItem(0).setChecked(true)
        supportActionBar!!.setTitle(langSetting.GetlangObj().mainMenuLang.subMenu1)
        setTheme()

        FragmentChanger.beginTransaction().replace(R.id.MainMenuFrameLaout,kartuPersediaanMenu).detach(kartuPersediaanMenu).attach(kartuPersediaanMenu).commit()
    }

    fun AddContentToFrame(){

        kartuPersediaanMenu = KartuPersediaanMenu()
        kartuPersediaanMenu.SetMainData(kartuPersediaanData)
        kartuPersediaanMenu.SetUserData(userData)
        kartuPersediaanMenu.setFormatLaporan(formatLaporan)
        kartuPersediaanMenu.SetLangTheme(langSetting.GetlangObj(),themeSetting.GetThemeSetting())


        produkMenu = ProdukMenu()
        produkMenu.SetListProduct(kartuPersediaanData.ListProdukData)
        produkMenu.settransDatas(kartuPersediaanData.ListTransaksiData)
        produkMenu.SetLangTheme(langSetting.GetlangObj(),themeSetting.GetThemeSetting())


        transaksiMenu =  TransaksiMenu()
        transaksiMenu.SetListTransaksi(kartuPersediaanData.ListTransaksiData)
        transaksiMenu.SetMainData(kartuPersediaanData)
        transaksiMenu.SetLangTheme(langSetting.GetlangObj(),themeSetting.GetThemeSetting())


    }

    fun SetSlideMenuTitle(mn : Menu,lang : LangObj){

        val LaporanMenu = mn.findItem(R.id.LaporanMenu)
        LaporanMenu.setTitle(lang.mainMenuLang.subMenu1)


        val TransaksiMenu = mn.findItem(R.id.TransaksiMenu)
        TransaksiMenu.setTitle(lang.mainMenuLang.subMenu2)

        val ProdukMenu = mn.findItem(R.id.ProdukMenu)
        ProdukMenu.setTitle(lang.mainMenuLang.subMenu3)

        val SideSettingMenu = mn.findItem(R.id.SideSettingMenu)
        SideSettingMenu.setTitle(lang.mainMenuLang.MenuSetting1)

        val Logout = mn.findItem(R.id.Logout)
        Logout.setTitle(lang.mainMenuLang.subMenu4)

        nav_view.setItemTextAppearance(themeSetting.GetThemeSetting().TextColor)

    }

    internal fun SettitleInOptionMenu(OptionMenu : Menu,lang : LangObj){

        val setting = OptionMenu.findItem(R.id.action_settings)
        val exit = OptionMenu.findItem(R.id.action_Exit)
        exit.setTitle(lang.mainMenuLang.MenuSetting2)
        setting.setTitle(lang.mainMenuLang.MenuSetting1)

    }

    fun SetSideProfile(){

        val header = nav_view.getHeaderView(0)
        val image : ImageView = header.findViewById(R.id.UserImage)
        val name : TextView = header.findViewById(R.id.UserName)
        val email : TextView = header.findViewById(R.id.UserEmail)

        name.setText(userData.Name)
        name.setTextColor(themeSetting.GetThemeSetting().TextColor)
        email.setText(userData.Email)
        email.setTextColor(themeSetting.GetThemeSetting().TextColor)
    }

    fun setTheme(){

        val header = nav_view.getHeaderView(0)
        val background : LinearLayout = header.findViewById(R.id.LinearLayoutDrawerUser)
        background.setBackgroundColor(themeSetting.GetThemeSetting().BackGroundColor)
        Toolbar.setBackgroundColor(themeSetting.GetThemeSetting().BackGroundColor)
        Toolbar.setTitleTextColor(themeSetting.GetThemeSetting().TextColor)
        fab.backgroundTintList = ColorStateList.valueOf(themeSetting.GetThemeSetting().BackGroundColor)

    }

    internal fun OpenLangAndThemeSetting(){
        val optionLang = arrayOf<CharSequence>(
                langSetting.GetlangObj().mainMenuSettingLang.OpenSetting1,
                langSetting.GetlangObj().mainMenuSettingLang.OpenSetting2,
                langSetting.GetlangObj().mainMenuSettingLang.typeLaporan
        )
        AlertDialog.Builder(context)
                .setTitle(langSetting.GetlangObj().mainMenuLang.MenuSetting1)
                .setItems(optionLang, DialogInterface.OnClickListener { dialogInterface, i ->
                    if (i == 0){

                        OpenLangSetting()

                    }else if (i == 1){

                        OpenThemeSetting()

                    } else if (i == 2){

                        OpenTypeLaporanSetting()
                    }
                    dialogInterface.dismiss()
                })
                .setNegativeButton(langSetting.GetlangObj().mainMenuSettingLang.Cancel, DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
                .create().show()
    }

    internal fun OpenTypeLaporanSetting(){
        val optionLang = arrayOf<CharSequence>(FormatLaporan.BIASA,FormatLaporan.ACCOUNTING)
        AlertDialog.Builder(context)
                .setTitle(langSetting.GetlangObj().mainMenuSettingLang.typeLaporan)
                .setItems(optionLang, DialogInterface.OnClickListener { dialogInterface, i ->
                    if (i == 0){

                        formatLaporan.TypeFormat = FormatLaporan.BIASA

                    }else if (i == 1){

                        formatLaporan.TypeFormat = FormatLaporan.ACCOUNTING
                    }

                    RefreshChangeSetting()
                    dialogInterface.dismiss()
                })
                .setNegativeButton(langSetting.GetlangObj().mainMenuSettingLang.Back, DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                    OpenLangAndThemeSetting()
                })
                .create().show()
    }

    internal fun OpenLangSetting(){

        val optionLang = arrayOf<CharSequence>("English","Indonesia")
        AlertDialog.Builder(context)
                .setTitle(langSetting.GetlangObj().mainMenuSettingLang.TitleOpenSetting1)
                .setItems(optionLang, DialogInterface.OnClickListener { dialogInterface, i ->
                    if (i == 0){
                        langSetting.ChangeLang(LangSetting.SetInglisLang)
                        langSetting.SetLangSetting(LangSetting.SetInglisLang)


                    }else if (i == 1){

                        langSetting.ChangeLang(LangSetting.SetIdoLang)
                        langSetting.SetLangSetting(LangSetting.SetIdoLang)
                    }

                    RefreshChangeSetting()
                    dialogInterface.dismiss()
                })
                .setNegativeButton(langSetting.GetlangObj().mainMenuSettingLang.Back, DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                    OpenLangAndThemeSetting()
                })
                .create().show()
    }

    internal fun OpenThemeSetting(){

        val optionLang = arrayOf<CharSequence>(ThemeSetting.red,ThemeSetting.blue,ThemeSetting.yellow,ThemeSetting.green,ThemeSetting.dark,ThemeSetting.purple,ThemeSetting.brown,ThemeSetting.blue_sea,ThemeSetting.oranges)
        AlertDialog.Builder(context)
                .setTitle(langSetting.GetlangObj().mainMenuSettingLang.TitleOpenSetting2)
                .setItems(optionLang, DialogInterface.OnClickListener { dialogInterface, i ->
                    when (i){
                        0 -> {
                            themeSetting.ChangeTheme(ThemeSetting.red)
                            themeSetting.SetThemeSetting(ThemeSetting.red)
                        }
                        1 -> {
                            themeSetting.ChangeTheme(ThemeSetting.blue)
                            themeSetting.SetThemeSetting(ThemeSetting.blue)
                        }
                        2 -> {
                            themeSetting.ChangeTheme(ThemeSetting.yellow)
                            themeSetting.SetThemeSetting(ThemeSetting.yellow)
                        }
                        3 -> {
                            themeSetting.ChangeTheme(ThemeSetting.green)
                            themeSetting.SetThemeSetting(ThemeSetting.green)
                        }
                        4 -> {
                            themeSetting.ChangeTheme(ThemeSetting.dark)
                            themeSetting.SetThemeSetting(ThemeSetting.dark)
                        }
                        5 -> {
                            themeSetting.ChangeTheme(ThemeSetting.purple)
                            themeSetting.SetThemeSetting(ThemeSetting.purple)
                        }
                        6 -> {
                            themeSetting.ChangeTheme(ThemeSetting.brown)
                            themeSetting.SetThemeSetting(ThemeSetting.brown)
                        }
                        7 -> {
                            themeSetting.ChangeTheme(ThemeSetting.blue_sea)
                            themeSetting.SetThemeSetting(ThemeSetting.blue_sea)
                        }
                        8 -> {
                            themeSetting.ChangeTheme(ThemeSetting.oranges)
                            themeSetting.SetThemeSetting(ThemeSetting.oranges)
                        }
                    }

                    RefreshChangeSetting()
                    dialogInterface.dismiss()
                })
                .setNegativeButton(langSetting.GetlangObj().mainMenuSettingLang.Back, DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                    OpenLangAndThemeSetting()
                })
                .create().show()
    }

    internal fun OpenAddTask(){

        val optionLang = arrayOf<CharSequence>(langSetting.GetlangObj().mainMenuAddTaskLang.addTransaction,langSetting.GetlangObj().mainMenuAddTaskLang.addProduct)
        AlertDialog.Builder(context)
                .setTitle(langSetting.GetlangObj().mainMenuAddTaskLang.title)
                .setItems(optionLang, DialogInterface.OnClickListener { dialogInterface, i ->
                    if (i == 0){

                        AddTransaksi(context,kartuPersediaanData,langSetting.GetlangObj(),themeSetting.GetThemeSetting(),nav_view,Toolbar,FragmentChanger,transaksiMenu)

                    }else if (i == 1){

                        AddProduk(context,kartuPersediaanData.ListProdukData,langSetting.GetlangObj(),themeSetting.GetThemeSetting(),nav_view,Toolbar,FragmentChanger,produkMenu)
                    }
                    dialogInterface.dismiss()
                })
                .setNegativeButton(langSetting.GetlangObj().mainMenuAddTaskLang.cancel, DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
                .create().show()
    }

    internal fun RefreshChangeSetting(){

        setDefault()
        AddContentToFrame()
        SetSlideMenuTitle(nav_view.menu,langSetting.GetlangObj())
        SettitleInOptionMenu(OptionMenu,langSetting.GetlangObj())

    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_utama, menu)
        this.OptionMenu = menu
        SettitleInOptionMenu(OptionMenu,langSetting.GetlangObj())
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings ->{
                OpenLangAndThemeSetting()
                return true
            }

            R.id.action_Exit ->{
                val saveMainData = SaveMainData(context,kartuPersediaanData)
                if (saveMainData.Save()) {
                    System.exit(0)
                }

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == fab){
            OpenAddTask()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.LaporanMenu -> {

                supportActionBar!!.setTitle(langSetting.GetlangObj().mainMenuLang.subMenu1)
                FragmentChanger.beginTransaction().replace(R.id.MainMenuFrameLaout,kartuPersediaanMenu).commit()
            }
            R.id.TransaksiMenu -> {

                supportActionBar!!.setTitle(langSetting.GetlangObj().mainMenuLang.subMenu2)
                FragmentChanger.beginTransaction().replace(R.id.MainMenuFrameLaout,transaksiMenu).commit()
            }
            R.id.ProdukMenu -> {
                supportActionBar!!.setTitle(langSetting.GetlangObj().mainMenuLang.subMenu3)
                FragmentChanger.beginTransaction().replace(R.id.MainMenuFrameLaout,produkMenu).commit()
            }
            R.id.SideSettingMenu -> {
                OpenLangAndThemeSetting()

            }

            R.id.Logout -> {

                val save = SaveMainData(context,kartuPersediaanData)
                val user = SaveUserData(context,userData)
                if (save.Delete() && user.Delete()){

                    val intent = Intent(context, Login::class.java)
                    context.startActivity(intent)
                    (context as Activity).finish()

                }
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onDestroy() {
        val save = SaveMainData(context,kartuPersediaanData)
        save.Save()
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            val save = SaveMainData(context,kartuPersediaanData)
            save.Save()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
