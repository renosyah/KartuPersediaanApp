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
import com.example.renosyahputra.kartupersediaan.storage.local.SaveMainData
import com.example.renosyahputra.kartupersediaan.storage.local.SaveUserData
import com.example.renosyahputra.kartupersediaan.storage.restFull.obj.DataFromCloud
import com.example.renosyahputra.kartupersediaan.ui.developerMode.DataDevMod
import com.example.renosyahputra.kartupersediaan.ui.developerMode.DevMod
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.io.Serializable

class GetAllData : AsyncTask<Void,Void,String>{


    var context : Context
    var userData : UserData
    var dialog : AlertDialog
    var lang  : LangObj

    val client = OkHttpClient()
    val gson = Gson()

    var dataDevMod : DataDevMod
    var devMod: DevMod? = null

    class LoadResponseObj : Serializable {
        var Response : Boolean = false
        lateinit var Message : String
        lateinit var Data : DataFromCloud
    }

    internal fun MakeNewData(dataFromCloud : DataFromCloud){

        val userFromCloud = dataFromCloud.User
        val kartuPersediaanDataFromCloud = KartuPersediaanData()
        kartuPersediaanDataFromCloud.ListTransaksiData = ArrayList()
        kartuPersediaanDataFromCloud.ListProdukData = ArrayList()
        kartuPersediaanDataFromCloud.ListPersediaanData = ArrayList()

        val metode = MetodePersediaan()
        metode.MetodeUse = MetodePersediaan.FIFO
        kartuPersediaanDataFromCloud.metodePersediaan = metode

        for (data in dataFromCloud.Transaksi.sortedWith(compareBy({ it.TanggalTransaksi.Tahun }, { it.TanggalTransaksi.Bulan }, { it.TanggalTransaksi.Hari }, { it.Jam.Jam }, { it.Jam.Menit }))){
            kartuPersediaanDataFromCloud.ListTransaksiData.add(data.CloneTransData())
        }
        kartuPersediaanDataFromCloud.ListProdukData = dataFromCloud.Produk

        SaveDataAndGoToMainMenu(context,userFromCloud,kartuPersediaanDataFromCloud)
    }


    internal fun GoToMainMenu(context: Context,userData: UserData,kartuPersediaanData: KartuPersediaanData){
        val intent = Intent(context, MenuUtama::class.java)
        intent.putExtra("data", kartuPersediaanData)
        intent.putExtra("user", userData)
        context.startActivity(intent)
        (context as Activity).finish()
    }

    internal fun SaveDataAndGoToMainMenu(context: Context,userData: UserData,kartuPersediaanData: KartuPersediaanData){
        val saveSession = SaveUserData(context,userData)
        saveSession.SaveSession()

        val savedata = SaveMainData(context,kartuPersediaanData)
        savedata.Save()

        GoToMainMenu(context,saveSession.LoadSession()!!,savedata.Load()!!)
    }


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

            val url = devMod!!.URL + ":" + devMod!!.PORT + devMod!!.DataUrl
            val request = Request.Builder()
                    .url(url)
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

            val ResponseWithDataValid = gson.fromJson(result,LoadResponseObj::class.java)

            if (ResponseWithDataValid.Response){

                MakeNewData(ResponseWithDataValid.Data)

            }else {
                Toast.makeText(context,ResponseWithDataValid.Message,Toast.LENGTH_SHORT).show()
            }


        }else {
            Toast.makeText(context,lang.loginDialogLang.failLogin,Toast.LENGTH_SHORT).show()
        }
    }



}