package com.example.renosyahputra.kartupersediaan.res.customAdapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.DetailTransaksi
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.example.renosyahputra.kartupersediaan.ui.theme.obj.ThemeObj
import java.text.DecimalFormat

class CustomAdapterDetailTransaction(context: Context, resource: Int, objects: MutableList<DetailTransaksi>) : ArrayAdapter<DetailTransaksi>(context,resource,objects){

    internal var context: Context = context
    internal var resources: Int = resource
    internal var objects: MutableList<DetailTransaksi> = objects

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

            holder.name = row.findViewById(R.id.nameProductDetailTrans)
            holder.price= row.findViewById(R.id.priceProductDetailTrans)
            holder.qty = row.findViewById(R.id.qtyProductDetailTrans)

            row.setTag(holder)
        } else {
            holder = (row.getTag() as DataList)
        }
        val item = getItem(position)

        holder.name.setText(item.ProdukData.Nama)
        holder.name.setTextColor(theme.BackGroundColor)

        holder.price.setText(formatter.format(item.ProdukData.Harga))
        holder.qty.setText(item.Quantity.toString())


        return row!!
    }

    class DataList {
        lateinit var name : TextView
        lateinit var price : TextView
        lateinit var qty : TextView
    }
}