package com.example.renosyahputra.kartupersediaan.storage.local

import android.content.Context
import java.io.*

class DevMod : Serializable {
    var URL = "http://192.168.23.1"
    var PORT = 7890
    var FullURL = URL + ":"+ PORT.toString()

    var DataUrl = FullURL + "/getData"
    var SaveDataUrl = FullURL + "/saveData"

    var FireBaseCode = ""
}

class DataDevMod(context: Context) {
    val filename = " DevMod.data"
    val ctx = context
    lateinit var data  : DevMod

    fun SetData( data  : DevMod){
        this.data = data
    }

    fun Save() : Boolean {

        var save = false
        try {

            val fos = ctx.openFileOutput(filename, Context.MODE_PRIVATE)
            val os = ObjectOutputStream(fos)
            os.writeObject(data)
            os.close()
            fos.close()

            save = true

        }catch (e : IOException){
            e.printStackTrace()
        }

        return save
    }



    fun Load() :  DevMod? {

        var data :  DevMod? = null

        try {
            val fis = ctx.openFileInput(filename)
            val file = ObjectInputStream(fis)
            data = file.readObject() as  DevMod
            file.close()
            fis.close()

        }catch (e : IOException){
            e.printStackTrace()
            data = DevMod()
        }
        return data
    }

    fun Delete() : Boolean {
        val f = File(ctx.filesDir,filename)
        return f.deleteRecursively()
    }
}