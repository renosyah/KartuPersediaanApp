package com.example.renosyahputra.kartupersediaan.storage.restFull

class UrlAndPort{
    companion object {
        val URL = "http://192.168.23.1"
        val PORT = 7890
        val FullURL = URL + ":"+ PORT.toString()

        val LoginUrl = FullURL + "/login"
        val RegisterUrl = FullURL + "/register"
        val DataUrl = FullURL + "/getData"
        val SaveDataUrl = FullURL + "/saveData"
    }
}