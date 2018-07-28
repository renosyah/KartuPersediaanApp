package com.example.renosyahputra.kartupersediaan.res.obj.transaksiData

import java.io.Serializable

class FormatWaktu : Serializable{
    var Jam : Int = 0
    var Menit : Int = 0
    lateinit var PMorAM : String

    fun MakeJamString() : String{
        return Jam.toString() + ":" +Menit.toString()
    }

    fun CloneTime() : FormatWaktu {
        val time = FormatWaktu()
        time.Jam = this.Jam
        time.Menit = this.Menit
        return time
    }
}