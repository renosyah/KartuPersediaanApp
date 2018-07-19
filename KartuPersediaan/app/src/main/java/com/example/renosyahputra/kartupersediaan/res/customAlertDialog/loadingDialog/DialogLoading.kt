package com.example.renosyahputra.kartupersediaan.res.customAlertDialog.loadingDialog

import android.app.Activity
import android.content.Context
import android.support.v7.app.AlertDialog
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.ui.theme.obj.ThemeObj

class DialogLoading(ctx : Context){
    internal val context = ctx
    internal lateinit var dialog : AlertDialog

    internal lateinit var bar  :LinearLayout
    lateinit var title : TextView

    internal lateinit var message : TextView
    internal lateinit var progress : ProgressBar


    internal lateinit var theme : ThemeObj
    internal lateinit var titleString : String
    internal lateinit var messageString :String

    fun SetLangAndTheme(titleString : String,messageString :String, theme : ThemeObj){
        this.titleString = titleString
        this.messageString = messageString
        this.theme = theme
    }

    fun Initialization(){
        dialog = AlertDialog.Builder(context)
                .create()
        val inflater = (context as Activity).layoutInflater
        val v = inflater.inflate(R.layout.custom_alert_dialog_loading,null)

        bar = v.findViewById(R.id.barDialogLoading)
        bar.setBackgroundColor(theme.BackGroundColor)

        title = v.findViewById(R.id.titleDialogLoading)
        title.setTextColor(theme.TextColor)
        title.setText(titleString)

        progress = v.findViewById(R.id.progressDialog)


        message = v.findViewById(R.id.messageDialogLoading)
        message.setTextColor(theme.BackGroundColor)
        message.setText(messageString)

        dialog.setView(v)
        dialog.setCancelable(false)
        dialog.show()
    }

    fun DismissDialog(){
        dialog.dismiss()
    }
}