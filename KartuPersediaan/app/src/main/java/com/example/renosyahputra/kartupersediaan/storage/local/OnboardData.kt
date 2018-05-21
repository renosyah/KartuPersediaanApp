package com.example.renosyahputra.kartupersediaan.storage.local

import android.content.Context
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class OnboardData {
    class OnboardDataObj {
        var HassSeingThis: Boolean = false
    }

    companion object {


        fun RemidMe(ctx: Context): Boolean {

            val filename = "OnboardSession.data"
            var data: OnboardDataObj? = null

            try {
                val fis = ctx.openFileInput(filename)
                val file = ObjectInputStream(fis)
                data = file.readObject() as OnboardDataObj
                file.close()
                fis.close()

            } catch (e: IOException) {
                e.printStackTrace()
            }

            return data!!.HassSeingThis
        }

        fun IdontWantSeeThis(ctx: Context) {

            val filename = "OnboardSession.data"
            val data = OnboardDataObj()
            data.HassSeingThis = true

            try {

                val fos = ctx.openFileOutput(filename, Context.MODE_PRIVATE)
                val os = ObjectOutputStream(fos)
                os.writeObject(data)
                os.close()
                fos.close()

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}