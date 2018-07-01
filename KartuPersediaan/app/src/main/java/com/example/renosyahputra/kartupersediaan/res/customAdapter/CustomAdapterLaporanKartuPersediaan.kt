package com.example.renosyahputra.kartupersediaan.res.customAdapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.res.obj.laporanKartuPersediaan.LaporanKartuPersediaanObj
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData
import com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.res.ChangeDateToRelevanString
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.example.renosyahputra.kartupersediaan.ui.theme.obj.ThemeObj

class CustomAdapterLaporanKartuPersediaan(ctx : Context,res : Int,objek : ArrayList<LaporanKartuPersediaanObj>) : ArrayAdapter<LaporanKartuPersediaanObj>(ctx,res,objek){
    internal val context = ctx
    internal val resources = res
    internal val objects = objek

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

            holder.name = row.findViewById(R.id.ProductNameInventoryCardAdapter)
            holder.qty = row.findViewById(R.id.ProductQtyInventoryCardAdapter)
            holder.date = row.findViewById(R.id.DateInventoryCardAdapter)
            holder.image = row.findViewById(R.id.imageInventoryCardAdapter)

            row.setTag(holder)
        } else {
            holder = (row.getTag() as DataList)
        }
        val item = getItem(position)
        val dateInSring = ChangeDateToRelevanString(context,lang)

        holder.name.setText(item.ProdukData.Nama)
        holder.name.setTextColor(theme.BackGroundColor)

        holder.qty.setText(lang.printLaporanLang.qtyP + " : " +item.GetKuantitas())
        holder.date.setText(dateInSring.SetAndGetFormatSimple(item.TanggalTransaksi,"/","/"))
        holder.image.setBackgroundResource(if (item.ProductFlow == TransaksiData.ProductIn) R.drawable.arrowin else R.drawable.arrowout)


        return row!!
    }

    internal class DataList{
        lateinit var name : TextView
        lateinit var qty : TextView
        lateinit var date : TextView
        lateinit var image : ImageView
    }
}