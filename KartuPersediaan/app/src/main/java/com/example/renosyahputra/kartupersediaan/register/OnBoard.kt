package com.example.renosyahputra.kartupersediaan.register

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.storage.local.OnboardData
import com.example.renosyahputra.kartupersediaan.ui.lang.LangSetting
import com.example.renosyahputra.kartupersediaan.ui.theme.ThemeSetting
import me.relex.circleindicator.CircleIndicator

class OnBoard : AppCompatActivity() {

    lateinit var context: Context

    lateinit var theme : ThemeSetting
    lateinit var lang : LangSetting

    lateinit var ViewPagerOnboard : ViewPager
    lateinit var circleIndicator : CircleIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_board)
        InitiationWidget()
    }

    internal fun InitiationWidget(){
        context = this@OnBoard

        theme = ThemeSetting(context)
        theme.CheckThemeSetting()

        lang = LangSetting(context)
        lang.CheckLangSetting()

        ViewPagerOnboard = findViewById(R.id.ViewPagerOnboard)
        circleIndicator = findViewById(R.id.ViewPagerOnboardCircleIdicator)

        if (CheckIfUserHaveSeeingThis()){
            (context as Activity).finish()
        }

    }

    internal fun CheckIfUserHaveSeeingThis() : Boolean {
        return OnboardData.RemidMe(context)
    }

    internal fun SetContenFragment(){

    }

    internal fun SetAdapter(){

    }
}
