package com.example.renosyahputra.kartupersediaan.res.customAlertDialog.userSetting

import android.app.Activity
import android.content.Context
import android.support.design.widget.NavigationView
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.*
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.res.obj.user.UserData
import com.example.renosyahputra.kartupersediaan.storage.local.SaveUserData
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.example.renosyahputra.kartupersediaan.ui.theme.obj.ThemeObj

class CustomAlertDialogUserSetting : View.OnClickListener{

    var context: Context
    lateinit var dialog  : AlertDialog
    var userData : UserData
    lateinit var nav : NavigationView

    lateinit var bar : RelativeLayout
    lateinit var title : TextView
    lateinit var name : EditText
    lateinit var namePerusahaan : EditText
    lateinit var email : EditText

    lateinit var username : EditText
    lateinit var OldPassword : EditText
    lateinit var NewPassword : EditText

    lateinit var simpan : Button
    lateinit var batal : Button

    constructor(context: Context, userData: UserData) {
        this.context = context
        this.userData = userData
    }

    fun SetNavBar(nav : NavigationView){
        this.nav = nav
    }

    fun SetSideProfile(){

        val header = nav.getHeaderView(0)
        val image : ImageView = header.findViewById(R.id.UserImage)
        val name : TextView = header.findViewById(R.id.UserName)
        val email : TextView = header.findViewById(R.id.UserEmail)

        name.setText(userData.Name)
        name.setTextColor(theme.TextColor)
        email.setText(userData.Email)
        email.setTextColor(theme.TextColor)
    }

    internal lateinit var lang : LangObj
    internal lateinit var theme: ThemeObj

    fun SetLangTheme(lang : LangObj, theme: ThemeObj){
        this.lang = lang
        this.theme = theme
    }

    fun InitiationDialog(){
        dialog = AlertDialog.Builder(context)
                .create()
        val inflater = (context as Activity).layoutInflater
        val v = inflater.inflate(R.layout.custom_alert_dialog_user_setting,null)

        bar = v.findViewById(R.id.TitleBarUserSetting)
        title = v.findViewById(R.id.TitleUserSetting)
        name = v.findViewById(R.id.FormUserSettingEditNama)
        namePerusahaan = v.findViewById(R.id.FormUserSettingEditNamaPerusahaan)
        email = v.findViewById(R.id.FormUserSettingEditEmail)

        username = v.findViewById(R.id.FormUserSettingEditUserNama)
        OldPassword = v.findViewById(R.id.FormUserSettingEditPassword)
        NewPassword = v.findViewById(R.id.FormUserSettingConfirmPassword)

        simpan = v.findViewById(R.id.buttonUserSettingSave)
        batal = v.findViewById(R.id.buttonUserSettingBatal)

        SetThemeAndLang()

        simpan.setOnClickListener(this)
        batal.setOnClickListener(this)

        dialog.setView(v)
        dialog.show()
    }

    internal fun SetThemeAndLang(){


        bar.setBackgroundColor(theme.BackGroundColor)
        title.setTextColor(theme.TextColor)
        title.setText(lang.userDataSettingLang.Title)

        name.setTextColor(theme.BackGroundColor)
        name.setHint(lang.userDataSettingLang.EditNamaPemilik)
        namePerusahaan.setTextColor(theme.BackGroundColor)
        namePerusahaan.setHint(lang.userDataSettingLang.EditNamaPerusahaan)
        email .setTextColor(theme.BackGroundColor)
        email.setHint(lang.userDataSettingLang.EditEmail)

        username.setTextColor(theme.BackGroundColor)
        username.setHint(lang.userDataSettingLang.EditUsername)
        OldPassword.setTextColor(theme.BackGroundColor)
        OldPassword.setHint(lang.userDataSettingLang.EditPassword)
        OldPassword.visibility = if (userData.Password == "") View.GONE else View.VISIBLE
        NewPassword.setTextColor(theme.BackGroundColor)
        NewPassword.setHint(lang.userDataSettingLang.KonfirmEditPassword)

        simpan.setBackgroundColor(theme.BackGroundColor)
        simpan.setText(lang.userDataSettingLang.Simpan)
        batal.setBackgroundColor(theme.BackGroundColor)
        batal.setText(lang.userDataSettingLang.Batal)

        simpan.setTextColor(theme.TextColor)
        batal.setTextColor(theme.TextColor)

        name.setText(userData.Name)
        namePerusahaan.setText(userData.CompanyName)
        email.setText(userData.Email)
        username.setText(userData.UserName)
    }

    fun CheckYangKosong() : Boolean {
        return (name.text.toString() == "" || namePerusahaan.text.toString() == "" || email.text.toString() == "")
    }

    fun ValidasiPassword() : Boolean {
        return OldPassword.text.toString() == userData.Password
    }

    override fun onClick(p0: View?) {
        when (p0){
            simpan-> {

                if (CheckYangKosong()) {

                    Toast.makeText(context, lang.userDataSettingLang.DataKosong, Toast.LENGTH_SHORT).show()

                }else if (OldPassword.text.toString() == "" && userData.Password != ""){

                    Toast.makeText(context, lang.userDataSettingLang.KonfirmasiDeanganMengisisPassword, Toast.LENGTH_SHORT).show()

                } else if (!ValidasiPassword() && userData.Password != ""){

                    Toast.makeText(context, lang.userDataSettingLang.GagalValidasiPassword, Toast.LENGTH_SHORT).show()

                }  else if (!CheckYangKosong() && ValidasiPassword()) {

                    userData.Name = name.text.toString()
                    userData.CompanyName = namePerusahaan.text.toString()
                    userData.Email = email.text.toString()

                    userData.UserName = username.text.toString()

                    if (NewPassword.text.toString() != ""){
                        userData.Password = NewPassword.text.toString()
                    }

                    val saveSession = SaveUserData(context,userData)
                    saveSession.SaveSession()

                    SetSideProfile()
                    dialog.dismiss()

                }
            }
            batal-> {

                dialog.dismiss()

            }
        }
    }

}