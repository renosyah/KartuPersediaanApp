package com.example.renosyahputra.kartupersediaan.res.customAlertDialog.login

import android.app.Activity
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.*
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.res.obj.user.UserData
import com.example.renosyahputra.kartupersediaan.storage.restFull.getAllData.GetAllData
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.example.renosyahputra.kartupersediaan.ui.theme.obj.ThemeObj

class LoginDialog(ctx : Context) : View.OnClickListener {

    internal val context = ctx
    internal lateinit var dialog  : AlertDialog
    internal lateinit var userData : UserData

    internal lateinit var bar  :RelativeLayout
    internal lateinit var title : TextView
    internal lateinit var username : EditText
    internal lateinit var password : EditText
    internal lateinit var login : Button
    internal lateinit var cancel : Button

    internal lateinit var lang : LangObj
    internal lateinit var theme: ThemeObj

    fun SetLangTheme(lang : LangObj, theme: ThemeObj){
        this.lang = lang
        this.theme = theme
    }

    fun InitiationDialog(){

        userData = UserData()

        dialog = AlertDialog.Builder(context)
                .create()
        val inflater = (context as Activity).layoutInflater
        val v = inflater.inflate(R.layout.dialog_login,null)

        bar  = v.findViewById(R.id.barDialogLogin)
        title = v.findViewById(R.id.titleBarDialogLogin)
        username = v.findViewById(R.id.UsernameDialogLogin)
        password = v.findViewById(R.id.PasswordDialogLogin)
        login  = v.findViewById(R.id.buttonLoginDialogLogin)
        cancel = v.findViewById(R.id.buttonCancelDialogLogin)

        login.setOnClickListener(this)
        cancel.setOnClickListener(this)

        SetThemeAndLang()

        dialog.setView(v)
        dialog.show()
    }

    internal fun SetThemeAndLang(){

        bar.setBackgroundColor(theme.BackGroundColor)
        title.setTextColor(theme.TextColor)

        username.setTextColor(theme.BackGroundColor)
        password.setTextColor(theme.BackGroundColor)

        login.setBackgroundColor(theme.BackGroundColor)
        login.setTextColor(theme.TextColor)
        cancel.setBackgroundColor(theme.BackGroundColor)
        cancel.setTextColor(theme.TextColor)


        title.setText(lang.loginDialogLang.title)
        username.setHint(lang.loginDialogLang.username)
        password.setHint(lang.loginDialogLang.password)
        login.setText(lang.loginDialogLang.login)
        cancel.setText(lang.loginDialogLang.cancel)

    }

    internal fun JikaKosong() : Boolean {
        return (username.text.toString() == "" || password.text.toString() == "")
    }

    override fun onClick(p0: View?) {
        when (p0){
            login ->{

                userData.IdUser = ""
                userData.Name = ""
                userData.CompanyName = ""

                if (JikaKosong()){

                    Toast.makeText(context,lang.loginDialogLang.emptyForm,Toast.LENGTH_SHORT).show()

                }else {
                    userData.UserName = username.text.toString()
                    userData.Password = password.text.toString()


                    GetAllData(context,userData,dialog,lang).execute()
                }


            }
            cancel ->{
                dialog.dismiss()
            }
        }
    }

}