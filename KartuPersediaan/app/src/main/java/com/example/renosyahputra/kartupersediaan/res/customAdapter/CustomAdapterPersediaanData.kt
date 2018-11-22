package com.example.renosyahputra.kartupersediaan.res.customAdapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.res.obj.persediaanData.PersediaanData
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.example.renosyahputra.kartupersediaan.ui.theme.obj.ThemeObj
import java.text.DecimalFormat

class CustomAdapterPersediaanData(context: Context, resource: Int, objects: MutableList<PersediaanData>) : ArrayAdapter<PersediaanData>(context,resource,objects) {

    internal var context: Context = context
    internal var resources: Int = resource
    internal var objects: MutableList<PersediaanData> = objects

    val formatter = DecimalFormat("##,###")


    internal lateinit var lang: LangObj
    internal lateinit var theme: ThemeObj

    fun SetLangTheme(lang: LangObj, theme: ThemeObj) {
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

            holder.Name = row.findViewById(R.id.NamePersediaan)
            holder.qty = row.findViewById(R.id.qtyPersediaan)
            holder.total = row.findViewById(R.id.totalPersediaan)
            holder.price = row.findViewById(R.id.HargaPersediaan)

            row.setTag(holder)
        } else {
            holder = (row.getTag() as DataList)
        }
        val item = getItem(position)

        holder.Name.setText(lang.laporanMenuLang.stokKe +" : "+ (position + 1))
        holder.Name.setTextColor(theme.BackGroundColor)
        holder.qty.setText(lang.printLaporanLang.qtyP +" : " +item.Jumlah.toString())
        holder.total.setText(lang.laporanMenuLang.total +" : " +formatter.format(item.Total))
        holder.price.setText(lang.laporanMenuLang.price +" : " +formatter.format(item.Produk.Harga))

        return row!!
    }

    class DataList {
        lateinit var Name : TextView
        lateinit var qty: TextView
        lateinit var total : TextView
        lateinit var price : TextView
    }
}
