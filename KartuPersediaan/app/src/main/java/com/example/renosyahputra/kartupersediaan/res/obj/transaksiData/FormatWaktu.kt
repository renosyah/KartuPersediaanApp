package com.example.renosyahputra.kartupersediaan.res.obj.transaksiData

import java.io.Serializable
import java.text.SimpleDateFormat

class FormatWaktu : Serializable{
    var Jam : Int = 0
    var Menit : Int = 0
    lateinit var PMorAM : String

    companion object {
        val AM = "AM"
        val PM = "PM"
    }

    fun MakeJamString() : String{
        return Jam.toString() + ":" +Menit.toString()
    }

    fun BandingkanWaktuYangLebihKecil(waktu: FormatWaktu) :Boolean {
        val parser = SimpleDateFormat("HH:mm")
        val time1 = parser.parse(waktu.MakeJamString())
        val time2 = parser.parse(this.MakeJamString())
        return (time2 > time1)
    }

    fun MakeClear(){
        if (PMorAM == AM){
            Jam += 0
        }else if (PMorAM == PM){
            Jam += 12
        }
    }
}