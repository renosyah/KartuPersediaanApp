package com.example.renosyahputra.kartupersediaan.storage.restFull.getAllData

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.example.renosyahputra.kartupersediaan.MenuUtama
import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.metode.MetodePersediaan
import com.example.renosyahputra.kartupersediaan.res.obj.user.UserData
import com.example.renosyahputra.kartupersediaan.storage.local.DataDevMod
import com.example.renosyahputra.kartupersediaan.storage.local.DevMod
import com.example.renosyahputra.kartupersediaan.storage.restFull.getAllData.obj.DataFromCloud
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class GetAllData : AsyncTask<Void,Void,String>{


    var context : Context
    var userData : UserData
    var dialog : AlertDialog
    var lang  : LangObj

    val client = OkHttpClient()
    val gson = Gson()

    var dataDevMod : DataDevMod
    var devMod: DevMod? = null

    var dataFromCloud = DataFromCloud()


    constructor(context: Context, userData: UserData,dialog : AlertDialog,lang  : LangObj) : super() {
        this.context = context
        this.userData = userData
        this.dialog = dialog
        this.lang = lang
        dataDevMod = DataDevMod(context)
        devMod = dataDevMod.Load()
    }

    override fun onPreExecute() {
        super.onPreExecute()
    }


    override fun doInBackground(vararg p0: Void?): String {
        var DataResponse = ""
        try {
            val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("Username",userData.UserName)
                    .addFormDataPart("Password",userData.Password)
                    .build()

            val request = Request.Builder()
                    .url(devMod!!.DataUrl)
                    .post(requestBody)
                    .build()

            val data_respon = client.newCall(request).execute()
            DataResponse = data_respon.body()!!.string()

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return DataResponse
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

        dialog.dismiss()

        if (result !=""){



            dataFromCloud = gson.fromJson(result,DataFromCloud::class.java)
            val userFromCloud = dataFromCloud.User
            val kartuPersediaanDataFromCloud = KartuPersediaanData()

            val metode = MetodePersediaan()
            metode.MetodeUse = MetodePersediaan.FIFO

            kartuPersediaanDataFromCloud.ListTransaksiData = dataFromCloud.Transaksi
            kartuPersediaanDataFromCloud.ListProdukData = dataFromCloud.Produk
            kartuPersediaanDataFromCloud.ListPersediaanData = ArrayList()
            kartuPersediaanDataFromCloud.metodePersediaan = metode

            val intent = Intent(context, MenuUtama::class.java)
            intent.putExtra("data", kartuPersediaanDataFromCloud)
            intent.putExtra("user", userFromCloud)
            context.startActivity(intent)
            (context as Activity).finish()

        }else {
            Toast.makeText(context,lang.loginDialogLang.failLogin,Toast.LENGTH_SHORT).show()
        }
    }

}