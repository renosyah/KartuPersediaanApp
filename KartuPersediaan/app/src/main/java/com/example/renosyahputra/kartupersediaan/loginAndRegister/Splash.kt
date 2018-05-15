package com.example.renosyahputra.kartupersediaan.loginAndRegister

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.renosyahputra.kartupersediaan.MenuUtama
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.metode.MetodePersediaan
import com.example.renosyahputra.kartupersediaan.res.obj.user.UserData
import com.example.renosyahputra.kartupersediaan.storage.local.SaveMainData
import com.example.renosyahputra.kartupersediaan.storage.local.SaveUserData

class Splash : AppCompatActivity() {

    lateinit var context: Context
    lateinit var IntentData : Intent
    lateinit var kartuPersediaanData : KartuPersediaanData
    lateinit var userData : UserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        InitiationWiddget()
    }

    internal fun InitiationWiddget(){
        context = this@Splash
        IntentData = intent



        var dataKosong = false
        var sessionKosong = false

        kartuPersediaanData = KartuPersediaanData()
        kartuPersediaanData.metodePersediaan = MetodePersediaan()
        kartuPersediaanData.metodePersediaan.MetodeUse = MetodePersediaan.FIFO
        kartuPersediaanData.ListProdukData = ArrayList()
        kartuPersediaanData.ListTransaksiData = ArrayList()
        kartuPersediaanData.ListPersediaanData = ArrayList()

        if (IntentData.getBooleanExtra("newData",false)){

            val newUserData = IntentData.getSerializableExtra("user") as UserData

            val saveSession = SaveUserData(context,newUserData)
            saveSession.SaveSession()

            val savedata = SaveMainData(context,kartuPersediaanData)
            savedata.Save()

            val intent = Intent(context, MenuUtama::class.java)
            intent.putExtra("data",kartuPersediaanData)
            intent.putExtra("user",newUserData)
            context.startActivity(intent)
            (context as Activity).finish()

        }else {


            val save = SaveMainData(context, kartuPersediaanData)
            dataKosong = save.Load() == null
            if (!dataKosong) {
                kartuPersediaanData.metodePersediaan = save.Load()!!.metodePersediaan
                kartuPersediaanData.ListProdukData = save.Load()!!.ListProdukData
                kartuPersediaanData.ListTransaksiData = save.Load()!!.ListTransaksiData
                kartuPersediaanData.ListPersediaanData = save.Load()!!.ListPersediaanData

            }

            userData = UserData()
            val session = SaveUserData(context, userData)
            sessionKosong = session.LoadSession() == null
            if (!sessionKosong) {
                userData.Name = session.LoadSession()!!.Name
                userData.Email = session.LoadSession()!!.Email
                userData.CompanyName = session.LoadSession()!!.CompanyName
            }




            if (!dataKosong && !sessionKosong) {

                val intent = Intent(context, MenuUtama::class.java)
                intent.putExtra("data", kartuPersediaanData)
                intent.putExtra("user", userData)
                context.startActivity(intent)
                (context as Activity).finish()

            } else {

                val intent = Intent(context, Login::class.java)
                context.startActivity(intent)
                (context as Activity).finish()
            }
        }

    }

}
