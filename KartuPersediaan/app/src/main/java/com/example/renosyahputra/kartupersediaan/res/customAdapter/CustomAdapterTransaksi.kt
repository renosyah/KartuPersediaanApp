package com.example.renosyahputra.kartupersediaan.res.customAdapter

import android.app.Activity
import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.example.renosyahputra.kartupersediaan.ui.theme.obj.ThemeObj
import java.text.DecimalFormat

class CustomAdapterTransaksi(context: Context,resource: Int,objects: MutableList<TransaksiData>) : ArrayAdapter<TransaksiData>(context,resource,objects) {

    internal var context: Context = context
    internal var resources: Int = resource
    internal var objects: MutableList<TransaksiData> = objects

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

            holder.Keterangan = row.findViewById(R.id.KeteranganTransaksiAdapter)
            holder.SubTotal = row.findViewById(R.id.SubTotalTransaksiAdapter)
            holder.ImageTransaksi = row.findViewById(R.id.GambarTransaksiAdapter)
            holder.Tgl = row.findViewById(R.id.TglTransaksiAdapter)

            row.setTag(holder)
        } else {
            holder = (row.getTag() as DataList)
        }
        val item = getItem(position)


        holder.ImageTransaksi.setImageDrawable(if (!item.IsThisValidTransaction) ResourcesCompat.getDrawable(context.resources, R.drawable.warning, null) else ResourcesCompat.getDrawable(context.resources, R.drawable.transactionicon, null))


        var color_jumlah = 0
        if (item.ProductFlow == TransaksiData.ProductIn){

            color_jumlah = ResourcesCompat.getColor(context.resources, R.color.greenMoney,null)

        }else if (item.ProductFlow == TransaksiData.ProductOut){

            color_jumlah = ResourcesCompat.getColor(context.resources, R.color.red,null)
        }



        holder.Keterangan.setText(item.Keterangan)
        holder.SubTotal.setText(formatter.format(item.SubTotal))
        holder.Tgl.setText(item.TanggalTransaksi.Hari.toString() +"-" + item.TanggalTransaksi.Bulan.toString() + "-" + item.TanggalTransaksi.Tahun.toString())

        holder.SubTotal.setTextColor(color_jumlah)

        holder.Keterangan.setTextColor(theme.BackGroundColor)


        return row!!
    }

    class DataList {
        lateinit var Keterangan: TextView
        lateinit var SubTotal: TextView
        lateinit var Tgl : TextView
        lateinit var ImageTransaksi : ImageView
    }
}