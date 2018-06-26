package com.example.renosyahputra.kartupersediaan.register

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.res.IdGenerator
import com.example.renosyahputra.kartupersediaan.res.customAlertDialog.login.LoginDialog
import com.example.renosyahputra.kartupersediaan.res.obj.user.UserData
import com.example.renosyahputra.kartupersediaan.ui.lang.LangSetting
import com.example.renosyahputra.kartupersediaan.ui.theme.ThemeSetting

class Register : AppCompatActivity(),View.OnClickListener {


    lateinit var context : Context

    lateinit var langSetting : LangSetting
    lateinit var themeSetting : ThemeSetting

    lateinit var title : TextView
    lateinit var login : TextView
    lateinit var setting : TextView

    lateinit var inputNama : EditText
    lateinit var inputEmail : EditText
    lateinit var inputCompanyName : EditText
    lateinit var register : Button

    lateinit var BackgroundLogin : LinearLayout

    lateinit var userData : UserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        InitiationWidget()
    }

    internal fun InitiationWidget(){
        context = this@Register

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        userData = UserData()

        langSetting = LangSetting(context)
        langSetting.CheckLangSetting()

        themeSetting = ThemeSetting(context)
        themeSetting.CheckThemeSetting()

        BackgroundLogin = findViewById(R.id.BackgroundLogin)
        BackgroundLogin.setBackgroundColor(themeSetting.GetThemeSetting().BackGroundColor)

        title = findViewById(R.id.TitleFormRegister)
        title.setTextColor(themeSetting.GetThemeSetting().BackGroundColor)
        title.setText(langSetting.GetlangObj().registerLang.title)

        login = findViewById(R.id.OpenLoginMenu)
        login.setTextColor(themeSetting.GetThemeSetting().BackGroundColor)
        login.setText(langSetting.GetlangObj().registerLang.login)

        setting = findViewById(R.id.OpenSettingMenu)
        setting.setTextColor(themeSetting.GetThemeSetting().BackGroundColor)
        setting.setText(langSetting.GetlangObj().registerLang.setting)

        inputNama = findViewById(R.id.NameUserTextInput)
        inputNama.setTextColor(themeSetting.GetThemeSetting().BackGroundColor)
        inputNama.setHint(langSetting.GetlangObj().registerLang.name)

        inputEmail  = findViewById(R.id.EmailUserTextInput)
        inputEmail.setTextColor(themeSetting.GetThemeSetting().BackGroundColor)
        inputEmail.setHint(langSetting.GetlangObj().registerLang.email)

        inputCompanyName  = findViewById(R.id.CompanyUserTextInput)
        inputCompanyName.setTextColor(themeSetting.GetThemeSetting().BackGroundColor)
        inputCompanyName.setHint(langSetting.GetlangObj().registerLang.company)

        register  = findViewById(R.id.buttonRegister)
        register.setTextColor(themeSetting.GetThemeSetting().TextColor)
        register.setBackgroundColor(themeSetting.GetThemeSetting().BackGroundColor)
        register.setText(langSetting.GetlangObj().registerLang.register)

        login.setOnClickListener(this)
        register.setOnClickListener(this)
        setting.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0){
            register -> {

                if (inputCompanyName.text.toString() == "" ||inputEmail.text.toString() == "" ||inputCompanyName.text.toString() == ""){
                    Toast.makeText(context,langSetting.GetlangObj().registerLang.inputEmpty,Toast.LENGTH_SHORT).show()
                    return
                }

                val id = IdGenerator()
                id.CreateRandomString(15)

                userData.IdUser = id.GetId()
                userData.Name = inputNama.text.toString()
                userData.Email = inputEmail.text.toString()
                userData.CompanyName = inputCompanyName.text.toString()

                userData.UserName = ""
                userData.Password = ""


                val intent = Intent(context, Splash::class.java)
                intent.putExtra("newData",true)
                intent.putExtra("user",userData)
                context.startActivity(intent)
                (context as Activity).finish()
            }
            login -> {

                val login = LoginDialog(context)
                login.SetLangTheme(langSetting.GetlangObj(),themeSetting.GetThemeSetting())
                login.InitiationDialog()

            }
            setting -> {
                OpenLangAndThemeSetting()
            }
        }
    }

    internal fun OpenLangAndThemeSetting(){
        val optionLang = arrayOf<CharSequence>(
                langSetting.GetlangObj().mainMenuSettingLang.OpenSetting1,
                langSetting.GetlangObj().mainMenuSettingLang.OpenSetting2
        )
        AlertDialog.Builder(context)
                .setTitle(langSetting.GetlangObj().mainMenuLang.MenuSetting1)
                .setItems(optionLang, DialogInterface.OnClickListener { dialogInterface, i ->
                    if (i == 0){

                        OpenLangSetting()

                    }else if (i == 1){

                        OpenThemeSetting()

                    }
                    dialogInterface.dismiss()
                })
                .setNegativeButton(langSetting.GetlangObj().mainMenuSettingLang.Cancel, DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
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

    internal fun RefreshChangeSetting(){
        BackgroundLogin.setBackgroundColor(themeSetting.GetThemeSetting().BackGroundColor)


        title.setTextColor(themeSetting.GetThemeSetting().BackGroundColor)
        title.setText(langSetting.GetlangObj().registerLang.title)


        login.setTextColor(themeSetting.GetThemeSetting().BackGroundColor)
        login.setText(langSetting.GetlangObj().registerLang.login)


        setting.setTextColor(themeSetting.GetThemeSetting().BackGroundColor)
        setting.setText(langSetting.GetlangObj().registerLang.setting)


        inputNama.setTextColor(themeSetting.GetThemeSetting().BackGroundColor)
        inputNama.setHint(langSetting.GetlangObj().registerLang.name)


        inputEmail.setTextColor(themeSetting.GetThemeSetting().BackGroundColor)
        inputEmail.setHint(langSetting.GetlangObj().registerLang.email)


        inputCompanyName.setTextColor(themeSetting.GetThemeSetting().BackGroundColor)
        inputCompanyName.setHint(langSetting.GetlangObj().registerLang.company)


        register.setTextColor(themeSetting.GetThemeSetting().TextColor)
        register.setBackgroundColor(themeSetting.GetThemeSetting().BackGroundColor)
        register.setText(langSetting.GetlangObj().registerLang.register)
    }
}
