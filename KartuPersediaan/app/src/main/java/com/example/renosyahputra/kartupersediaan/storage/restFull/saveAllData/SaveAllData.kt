package com.example.renosyahputra.kartupersediaan.storage.restFull.saveAllData

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.example.renosyahputra.kartupersediaan.res.obj.KartuPersediaanData
import com.example.renosyahputra.kartupersediaan.storage.restFull.UrlAndPort
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException


class SaveAllData : AsyncTask<Void,Void,String>{

    var context : Context
    var data : KartuPersediaanData
    val client = OkHttpClient()
    val gson = Gson()
    lateinit var JsonDataProductList :String
    lateinit var JsonDataTransactionList :String

    constructor(context: Context, data: KartuPersediaanData) : super() {
        this.context = context
        this.data = data
    }


    override fun onPreExecute() {
        super.onPreExecute()
        JsonDataProductList = gson.toJson(data.ListProdukData)
        JsonDataTransactionList = gson.toJson(data.ListTransaksiData)
    }

    override fun doInBackground(vararg p0: Void?): String {
        var DataResponse = ""
        try {
            val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("ProductList",JsonDataProductList)
                    .addFormDataPart("TransactionList",JsonDataTransactionList)
                    .build()

            val request = Request.Builder()
                    .url(UrlAndPort.SaveDataUrl)
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

            val obj = JSONObject(result)
            if (!obj.getBoolean("Ok")){
                Toast.makeText(context,obj.getString("Err"),Toast.LENGTH_SHORT).show()
            }
        }
    }
}