package com.example.renosyahputra.kartupersediaan.res

class IdGenerator {

    internal lateinit var Id: String
    internal var possible: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    fun CreateRandomString(length: Int) {
        var LocalId = ""
        for (i in 0..length) {
            LocalId += possible[(Math.floor(Math.random() * possible.length)).toInt()]
        }
        Id = LocalId

    }

    fun GetId(): String {
        return Id

    }
}