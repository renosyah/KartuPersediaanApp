package com.example.renosyahputra.kartupersediaan.ui.developerMode

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.example.renosyahputra.kartupersediaan.ui.theme.obj.ThemeObj

class DeveloperMode : AppCompatActivity(),View.OnClickListener {


    lateinit var context: Context
    lateinit var IntentData : Intent

    lateinit var lang : LangObj
    lateinit var theme : ThemeObj

    lateinit var  Data  : DevMod

    lateinit var bar : RelativeLayout
    lateinit var back  :ImageView

    lateinit var title : TextView
    lateinit var subtitleCloud : TextView
    lateinit var subtitleApp : TextView

    lateinit var mainUrl : TextView
    lateinit var port : TextView
    lateinit var getData : TextView
    lateinit var saveData : TextView

    lateinit var loopFilterOne  :TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer_mode)
        InitiationWidget()
    }

    internal fun InitiationWidget(){
        context = this@DeveloperMode
        IntentData = intent

        lang = IntentData.getSerializableExtra("lang") as LangObj
        theme = IntentData.getSerializableExtra("theme") as ThemeObj

        val load = DataDevMod(context)
        Data = load.Load()!!

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        bar = findViewById(R.id.bartitleDeveloperMode)
        back  = findViewById(R.id.closeDeveloperMode)

        title = findViewById(R.id.titleDeveloperMode)
        subtitleCloud = findViewById(R.id.SubtitleCloudSetDeveloperMode)
        subtitleApp  = findViewById(R.id.SubtitleAppSetDeveloperMode)

        mainUrl  =  findViewById(R.id.MainIpSetDeveloperMode)
        port = findViewById(R.id.PortSetDeveloperMode)
        getData = findViewById(R.id.GetDataSetDeveloperMode)
        saveData = findViewById(R.id.SaveDataSetDeveloperMode)

        loopFilterOne  = findViewById(R.id.FirstFilterLoopDeveloperMode)

        SetLangAndTheme(Data)

        back.setOnClickListener(this)

        mainUrl.setOnClickListener(this)
        port.setOnClickListener(this)
        getData.setOnClickListener(this)
        saveData.setOnClickListener(this)

        loopFilterOne.setOnClickListener(this)
    }

    internal fun SetLangAndTheme(Data : DevMod){
        bar.setBackgroundColor(theme.BackGroundColor)
        title.setTextColor(theme.TextColor)

        subtitleCloud.setTextColor(theme.BackGroundColor)
        subtitleApp.setTextColor(theme.BackGroundColor)

        title.setText(lang.devModeLang.title)
        subtitleApp.setText(lang.devModeLang.subtitle_app)
        subtitleCloud.setText(lang.devModeLang.subtitle_cloud)

        mainUrl.setText(lang.devModeLang.editUrl + " : " +Data.URL.toString())
        port.setText(lang.devModeLang.editport + " : " +Data.PORT.toString())
        getData.setText(lang.devModeLang.editloadData + " : " +Data.DataUrl)
        saveData.setText(lang.devModeLang.editsaveData + " : " +Data.SaveDataUrl)
        loopFilterOne.setText(lang.devModeLang.editloop + " : " +Data.LoopForFilter1.toString())

    }

    val SETTING_FOR_URL = "URL"
    val SETTING_FOR_PORT = "PORT"
    val SETTING_FOR_LOAD = "LOAD_URL"
    val SETTING_FOR_SAVE = "SAVE_URL"
    val SETTING_FOR_LOOP = "LOOP_FILTER_1"


    internal fun OpenDialogEditParam(context: Context,target : TextView,value : String,type : Int,settingParam  :String){
        val inflater = (context as Activity).layoutInflater
        val v = inflater.inflate(R.layout.custom_alert_dialog_edit_qty,null)

        val EditForm : EditText = v.findViewById(R.id.editQtyDetail)
        EditForm.inputType = type
        EditForm.setText(value)

        val dialog = AlertDialog.Builder(context)
                .setTitle(lang.devModeLang.title_dialog)
                .setPositiveButton(lang.devModeLang.save, DialogInterface.OnClickListener { dialogInterface, i ->
                    if (type == InputType.TYPE_CLASS_TEXT){
                        if (settingParam == SETTING_FOR_URL) {
                            Data.URL = EditForm.text.toString()
                        } else if (settingParam == SETTING_FOR_LOAD) {
                            Data.DataUrl = EditForm.text.toString()
                        } else if (settingParam == SETTING_FOR_SAVE) {
                            Data.SaveDataUrl = EditForm.text.toString()
                        }

                    }else if (type == InputType.TYPE_CLASS_NUMBER){
                        if (settingParam == SETTING_FOR_PORT) {
                            Data.PORT =  if (EditForm.text.toString()!= "") Integer.parseInt(EditForm.text.toString()) else  7890
                        } else if (settingParam == SETTING_FOR_LOOP) {
                            Data.LoopForFilter1 = if (EditForm.text.toString()!= "") Integer.parseInt(EditForm.text.toString()) else 5
                        }
                    }



                    val save = DataDevMod(context)
                    save.SetData(Data)
                    save.Save()

                    SetLangAndTheme(Data)

                    dialogInterface.dismiss()
                })
                .setNegativeButton(lang.devModeLang.cancel, DialogInterface.OnClickListener { dialogInterface, i ->
                   dialogInterface.dismiss()
                })
                .create()

        dialog.setView(v)
        dialog.show()
    }


    override fun onClick(p0: View?) {
        when(p0){
            back ->{
                (context as Activity).finish()
            }
            mainUrl->{
                OpenDialogEditParam(context,mainUrl,Data.URL.toString(),InputType.TYPE_CLASS_TEXT,SETTING_FOR_URL)
            }
            port->{
                OpenDialogEditParam(context,port,Data.PORT.toString(),InputType.TYPE_CLASS_NUMBER,SETTING_FOR_PORT)
            }
            getData->{
                OpenDialogEditParam(context,getData,Data.DataUrl.toString(),InputType.TYPE_CLASS_TEXT,SETTING_FOR_LOAD)
            }
            saveData->{
                OpenDialogEditParam(context,saveData,Data.SaveDataUrl.toString(),InputType.TYPE_CLASS_TEXT,SETTING_FOR_SAVE)
            }
            loopFilterOne->{
                OpenDialogEditParam(context,loopFilterOne,Data.LoopForFilter1.toString(),InputType.TYPE_CLASS_NUMBER,SETTING_FOR_LOOP)
            }
        }
    }

}
