package com.example.renosyahputra.kartupersediaan.storage.restFull.saveAllData

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.user.UserData
import com.example.renosyahputra.kartupersediaan.ui.developerMode.DataDevMod
import com.example.renosyahputra.kartupersediaan.ui.developerMode.DevMod
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException


class SaveAllData : AsyncTask<Void,Void,String>{

    var context : Context
    var data : KartuPersediaanData
    var user : UserData
    val client = OkHttpClient()
    val gson = Gson()

    lateinit var dataDevMod : DataDevMod
    var devMod: DevMod? = null

    lateinit var JsonDataProductList :String
    lateinit var JsonDataTransactionList :String
    lateinit var JsonUserData : String

    var lang : LangObj

    constructor(context: Context, data: KartuPersediaanData,user : UserData,lang: LangObj) : super() {
        this.context = context
        this.data = data
        this.user = user
        this.lang = lang
        dataDevMod = DataDevMod(context)
        devMod = dataDevMod.Load()

    }


    override fun onPreExecute() {
        super.onPreExecute()
        JsonDataProductList = gson.toJson(data.ListProdukData)
        JsonDataTransactionList = gson.toJson(data.ListTransaksiData)
        JsonUserData = gson.toJson(user)
    }

    override fun doInBackground(vararg p0: Void?): String {
        var DataResponse = ""
        try {
            val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("ProductList",JsonDataProductList)
                    .addFormDataPart("TransactionList",JsonDataTransactionList)
                    .addFormDataPart("UserData",JsonUserData)
                    .build()

            val url = devMod!!.URL + ":" + devMod!!.PORT + devMod!!.SaveDataUrl

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
        if (result != ""){
            val jsonResponse = JSONObject(result)
            if (jsonResponse.getBoolean("Response")){
                val message = jsonResponse.getString("Message")
                Toast.makeText(context,if (message == "OK") lang.mainMenuLang.SavingComplete else message,Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(context,jsonResponse.getString("Message"),Toast.LENGTH_SHORT).show()
            }

        }else if(data.ListTransaksiData.size >= 20){

            Toast.makeText(context,lang.mainMenuLang.SavingButDataIsTooBig,Toast.LENGTH_SHORT).show()

        }else if (result == "" && data.ListTransaksiData.size >= 20) {

            Toast.makeText(context,lang.mainMenuLang.SavingFail,Toast.LENGTH_SHORT).show()

        }else if (result == "") {

            Toast.makeText(context,lang.mainMenuLang.SavingFail,Toast.LENGTH_SHORT).show()

        }
    }
}