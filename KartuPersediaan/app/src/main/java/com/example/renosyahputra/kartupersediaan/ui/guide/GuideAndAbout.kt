package com.example.renosyahputra.kartupersediaan.ui.guide

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.example.renosyahputra.kartupersediaan.ui.theme.obj.ThemeObj

class GuideAndAbout : AppCompatActivity(),View.OnClickListener {

    lateinit var context: Context
    lateinit var IntentData : Intent

    lateinit var lang : LangObj
    lateinit var theme : ThemeObj

    internal lateinit var bar : RelativeLayout
    internal lateinit var title : TextView
    internal lateinit var back : ImageView

    internal lateinit var nameApp : TextView
    internal lateinit var versionApp : TextView

    internal lateinit var guideText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_and_about)
        InitiationWidget()
    }


    internal fun InitiationWidget(){
        context = this@GuideAndAbout
        IntentData = intent

        lang = IntentData.getSerializableExtra("lang") as LangObj
        theme = IntentData.getSerializableExtra("theme") as ThemeObj

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        bar = findViewById(R.id.BarGuide)
        title = findViewById(R.id.TitleGuide)
        back  = findViewById(R.id.BackFromGuide)

        nameApp = findViewById(R.id.AppNameGuides)
        versionApp = findViewById(R.id.AppVerGuides)

        guideText = findViewById(R.id.GuideText)

        back.setOnClickListener(this)

        SetThemeAndLang()

    }

    internal fun SetThemeAndLang(){

        bar.setBackgroundColor(theme.BackGroundColor)
        title.setTextColor(theme.TextColor)


        nameApp.setTextColor(theme.BackGroundColor)
        versionApp.setTextColor(theme.BackGroundColor)

        guideText.setTextColor(theme.BackGroundColor)


        title.setText(lang.guideLang.title)
        nameApp.setText(lang.guideLang.appName)
        versionApp.setText(lang.guideLang.appVer)

        guideText.setText(lang.guideLang.guides)

    }

    override fun onClick(p0: View?) {
        when(p0){
            back -> {
                (context as Activity).finish()
            }
        }
    }
}
