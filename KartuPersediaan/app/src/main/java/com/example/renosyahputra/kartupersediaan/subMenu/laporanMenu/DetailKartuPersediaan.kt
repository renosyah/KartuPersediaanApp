package com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.res.customAdapter.CustomAdapterPersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.laporanKartuPersediaan.LaporanKartuPersediaanObj
import com.example.renosyahputra.kartupersediaan.res.obj.persediaanData.PersediaanData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.example.renosyahputra.kartupersediaan.ui.theme.obj.ThemeObj
import com.example.renosyahputra.quicktrans.ui.GetListViewTotalHeight
import java.text.DecimalFormat

class DetailKartuPersediaan : AppCompatActivity(),View.OnClickListener {


    lateinit var context: Context
    lateinit var IntentData : Intent
    
    lateinit var bar : RelativeLayout
    lateinit var back : ImageView
    lateinit var title : TextView
    lateinit var titleImage : ImageView
    
    
    lateinit var date: TextView
    lateinit var name : TextView
    lateinit var price : TextView
    lateinit var qty : TextView
    lateinit var total : TextView
    
    lateinit var lang : LangObj
    lateinit var theme : ThemeObj
    
    
    lateinit var layoutList : LinearLayout
    lateinit var ListPersediaan : ListView

    lateinit var dt: LaporanKartuPersediaanObj

    val formatter = DecimalFormat("##,###")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kartu_persediaan)
        InitiationWidget()
    }
    
    fun InitiationWidget(){
        context = this@DetailKartuPersediaan
        IntentData = intent

        dt = IntentData.getSerializableExtra("dataDetail") as LaporanKartuPersediaanObj
        lang = IntentData.getSerializableExtra("lang") as LangObj
        theme = IntentData.getSerializableExtra("theme") as ThemeObj


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        bar = findViewById(R.id.titleDetailLapInfoBar)
        titleImage = findViewById(R.id.ImagetitleDetailLapInfo)
        back = findViewById(R.id.backFromDetail)
        title = findViewById(R.id.titleDetailLapInfo)
        date = findViewById(R.id.DateDetailLap)
        name = findViewById(R.id.nameProductDetailLap)
        price = findViewById(R.id.PriceDetailLap)
        qty = findViewById(R.id.QtyDetailLap)
        total = findViewById(R.id.TotalDetailLapInfo)
        layoutList = findViewById(R.id.LayoutlistDetailLap)
        ListPersediaan = findViewById(R.id.listDetailLap)


        back.setOnClickListener(this)

        SetValue()
    }
    
    fun SetValue(){
        date.setText(dt.TanggalTransaksi.toDateString())
        titleImage.setBackgroundResource(if (dt.ProductFlow == TransaksiData.ProductIn) R.drawable.arrowin else R.drawable.arrowout)
        title.setText(dt.Keterangan)
        name.setText(lang.laporanMenuLang.namaProductDetail + " : " +dt.ProdukData.Nama)
        price.setText(lang.laporanMenuLang.price + " : " +formatter.format(dt.ProdukData.Harga))
        qty.setText(lang.laporanMenuLang.qty + " : " +dt.GetKuantitas())
        total.setText(lang.laporanMenuLang.total + " : " +formatter.format(dt.ProdukData.Harga * dt.GetKuantitas()))
        total.setTextColor(if (dt.ProductFlow == TransaksiData.ProductIn) ResourcesCompat.getColor(context.resources, R.color.greenMoney,null) else ResourcesCompat.getColor(context.resources, R.color.red,null))
        bar.setBackgroundColor(theme.BackGroundColor)

        SetAdapter(dt.ListPersediaanData)
    }
    
    fun SetAdapter(l : ArrayList<PersediaanData>){

        val adapter = CustomAdapterPersediaanData(context,R.layout.custom_adapter_persediaan,l)
        adapter.SetLangTheme(lang,theme)
        ListPersediaan.adapter = adapter
        ListPersediaan.divider = null
        layoutList.layoutParams.height = GetListViewTotalHeight.getListViewTotalHeight(ListPersediaan)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            back -> {
                (context as Activity).finish()
            }
        }
    }
}
