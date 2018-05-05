package com.example.renosyahputra.kartupersediaan.res.obj.metode

import java.io.Serializable

class MetodePersediaan : Serializable {
    companion object {
        val FIFO = "FIFO"
        val LIFO = "LIFO"
        val AVERAGE = "AVERAGE"
    }
    lateinit var MetodeUse : String
}