package com.example.renosyahputra.kartupersediaan.res.customAdapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.example.renosyahputra.kartupersediaan.ui.theme.obj.ThemeObj
import java.text.DecimalFormat

class CustomAdapterListProduk(context: Context,resource: Int,objects: MutableList<ProdukData>) : ArrayAdapter<ProdukData>(context,resource,objects){

    internal var context: Context = context
    internal var resources: Int = resource
    internal var objects: MutableList<ProdukData> = objects

    val formatter = DecimalFormat("##,###")


    internal lateinit var lang : LangObj
    internal lateinit var theme: ThemeObj

    fun SetLangTheme(lang : LangObj, theme: ThemeObj){
        this.lang = lang
        this.theme = theme
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var row = convertView
        var holder: DataList? = null
        if (row == null) {
            val inflater = (context as Activity).layoutInflater
            row = inflater.inflate(resources, parent, false)
            holder = DataList()

            holder.NameProduct = row.findViewById(R.id.NamaProdukAdapter)
            holder.Price = row.findViewById(R.id.HargaProdukAdapter)
            holder.Image = row.findViewById(R.id.GambarProdukAdapter)
            holder.unit = row.findViewById(R.id.UnitProductAdapter)

            row.setTag(holder)
        } else {
            holder = (row.getTag() as DataList)
        }
        val item = getItem(position)

        holder.NameProduct.setText(item.Nama)
        holder.Price.setText(formatter.format(item.Harga))
        holder.unit.setText(item.Satuan.toString())


        holder.NameProduct.setTextColor(theme.BackGroundColor)


        return row!!
    }

    class DataList {
        lateinit var NameProduct : TextView
        lateinit var Price : TextView
        lateinit var Image : ImageView
        lateinit var unit : TextView
    }
}