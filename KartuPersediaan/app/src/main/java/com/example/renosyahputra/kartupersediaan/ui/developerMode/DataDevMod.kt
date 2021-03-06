package com.example.renosyahputra.kartupersediaan.ui.developerMode

import android.content.Context
import java.io.*

class DevMod : Serializable {
    companion object {
        val OLD = "By Transaction Data"
        val NEW = "By KartuPersediaan Data"

        val MakeSenseOfAverage = "Make Sense"
        val NotMakeSenseOfAverage = "Make No Sense"

    }
    var URL = "http://192.168.23.1"
    var PORT = 7890
    var DataUrl = "/getData"
    var SaveDataUrl = "/saveData"

    var LoopForFilter1 = 5
    var CovertMode = DevMod.OLD

    var AverageMode = MakeSenseOfAverage
}

class DataDevMod(context: Context) {
    companion object {
        val devmodString = "#DEVMOD"
    }
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
            data = file.readObject() as DevMod
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