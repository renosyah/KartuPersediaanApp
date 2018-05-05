package com.example.renosyahputra.kartupersediaan.res.obj.transaksiData

import java.io.Serializable

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
        return (this.Jam >= waktu.Jam && this.Menit > waktu.Menit)
    }

    fun MakeClear(){
        if (PMorAM == AM){
            Jam += 0
        }else if (PMorAM == PM){
            Jam += 12
        }
    }
}