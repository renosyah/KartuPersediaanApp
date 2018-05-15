package com.example.renosyahputra.kartupersediaan.storage.local

import android.content.Context
import com.example.renosyahputra.kartupersediaan.res.obj.user.UserData
import java.io.File
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class SaveUserData(ctx : Context,user  :UserData){
    val context = ctx
    val data = user
    val user = "Session.data"

    fun LoadSession() : UserData? {

        var data : UserData? = null

        try {
            val fis = context.openFileInput(user)
            val file = ObjectInputStream(fis)
            data = file.readObject() as UserData
            file.close()
            fis.close()

        }catch (e : IOException){
            e.printStackTrace()
        }
        return data
    }


    fun SaveSession() : Boolean {

        var save = false
        try {

            val fos = context.openFileOutput(user, Context.MODE_PRIVATE)
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
    fun Delete() : Boolean {
        val f = File(context.filesDir,user)
        return f.deleteRecursively()
    }
}