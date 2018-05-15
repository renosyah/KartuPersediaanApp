package com.example.renosyahputra.kartupersediaan.ui.theme

import android.app.Activity
import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.ui.theme.obj.ThemeObj
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader

class ThemeSetting(ctx : Context){
    val context : Context = ctx
    val themeObj : ThemeObj = ThemeObj()
    companion object {
        val red = "RED"
        val green = "GREEN"
        val yellow = "YELLOW"
        val blue = "BLUE"
        val dark = "DARK"
        val purple= "PURPLE"
        val blue_sea = "LIGHT BLUE"
        val brown = "BROWN"
        val oranges = "ORANGE"
    }
    val FilenameLangSetting : String = "ThemeSetting.txt"

    fun ChangeTheme(s : String){
        when (s) {
            red -> {
                Setred()
            }
            green -> {
                Setgreen()
            }
            yellow -> {
                Setyellow()
            }
            blue -> {
                Setblue()
            }
            dark ->{
                Setdark()
            }
            purple -> {
                Setpurple()
            }
            brown -> {
                Setbrown()
            }
            oranges -> {
                Setorange()
            }
            blue_sea -> {
                Setbluesea()
            }
            else ->{
                Setred()
        }
        }
    }

    fun GetThemeSetting() : ThemeObj{
        return themeObj
    }

    fun Setred() {
        themeObj.BackGroundColor = ResourcesCompat.getColor(context.resources, R.color.red,null)
        themeObj.TextColor = ResourcesCompat.getColor(context.resources, R.color.whiteText,null)

    }
    fun Setgreen() {
        themeObj.BackGroundColor = ResourcesCompat.getColor(context.resources, R.color.green,null)
        themeObj.TextColor = ResourcesCompat.getColor(context.resources, R.color.whiteText,null)
    }
    fun Setyellow() {
        themeObj.BackGroundColor = ResourcesCompat.getColor(context.resources, R.color.yellow,null)
        themeObj.TextColor = ResourcesCompat.getColor(context.resources, R.color.whiteText,null)
    }
    fun Setblue() {
        themeObj.BackGroundColor = ResourcesCompat.getColor(context.resources, R.color.blue,null)
        themeObj.TextColor = ResourcesCompat.getColor(context.resources, R.color.whiteText,null)
    }
    fun Setdark(){
        themeObj.BackGroundColor = ResourcesCompat.getColor(context.resources, R.color.dark,null)
        themeObj.TextColor = ResourcesCompat.getColor(context.resources, R.color.whiteText,null)
    }
    fun Setpurple() {
        themeObj.BackGroundColor = ResourcesCompat.getColor(context.resources, R.color.purple,null)
        themeObj.TextColor = ResourcesCompat.getColor(context.resources, R.color.whiteText,null)

    }
    fun Setbluesea() {
        themeObj.BackGroundColor = ResourcesCompat.getColor(context.resources, R.color.blue_sea,null)
        themeObj.TextColor = ResourcesCompat.getColor(context.resources, R.color.whiteText,null)
    }
    fun Setbrown() {
        themeObj.BackGroundColor = ResourcesCompat.getColor(context.resources, R.color.brown,null)
        themeObj.TextColor = ResourcesCompat.getColor(context.resources, R.color.whiteText,null)
    }
    fun Setorange() {
        themeObj.BackGroundColor = ResourcesCompat.getColor(context.resources, R.color.orange,null)
        themeObj.TextColor = ResourcesCompat.getColor(context.resources, R.color.whiteText,null)
    }

    fun CheckThemeSetting(){

        val SettingLangData : String = load_data(FilenameLangSetting,context)

        when (SettingLangData) {
            red -> {
                Setred()
            }
            green -> {
                Setgreen()
            }
            yellow -> {
                Setyellow()
            }
            blue -> {
                Setblue()
            }
            dark ->{
                Setdark()
            }
            purple -> {
                Setpurple()
            }
            brown -> {
                Setbrown()
            }
            oranges -> {
                Setorange()
            }
            blue_sea -> {
                Setbluesea()
            }
            else ->{
                Setred()
            }
        }
    }

    fun SetThemeSetting(setting : String){
        save_data(context, setting,FilenameLangSetting)
    }
    fun save_data(context: Context, data_disimpan: String, nama_file: String) {
        val data_save = false
        try {
            val fileOutputStream = context.openFileOutput(nama_file, Context.MODE_PRIVATE)
            fileOutputStream.write(data_disimpan.toByteArray())


        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun load_data(nama_file: String, context: Context): String {
        val stringbufer = StringBuffer()
        try {
            val fileinputstream = (context as Activity).openFileInput(nama_file)
            val reader = InputStreamReader(fileinputstream)
            val buferreader = BufferedReader(reader)

            for (pesan in buferreader.readLine()) {
                stringbufer.append(pesan)
            }

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return stringbufer.toString()
    }
}
