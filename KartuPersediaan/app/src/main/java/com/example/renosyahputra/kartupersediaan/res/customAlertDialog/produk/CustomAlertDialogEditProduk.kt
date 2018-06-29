package com.example.renosyahputra.kartupersediaan.res.customAlertDialog.produk

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.*
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.res.AlterAllProductInTrans
import com.example.renosyahputra.kartupersediaan.res.customAdapter.CustomAdapterListProduk
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.example.renosyahputra.kartupersediaan.ui.theme.obj.ThemeObj

class CustomAlertDialogEditProduk(ctx : Context, res : Int, List : ArrayList<ProdukData>, p : ProdukData) : View.OnClickListener {

    internal val context = ctx
    lateinit var dialog: AlertDialog
    var resources: Int = res
    var ListProduk: ArrayList<ProdukData> = List
    var produk : ProdukData = p

    internal lateinit var lang: LangObj
    internal lateinit var theme: ThemeObj


    internal lateinit var bar: RelativeLayout
    internal lateinit var title: TextView
    internal lateinit var nama: EditText
    internal lateinit var harga: EditText
    internal lateinit var unit : EditText
    internal lateinit var tambah: Button
    internal lateinit var batal: Button

    lateinit var l : ListView


    fun SetLangTheme(lang: LangObj, theme: ThemeObj) {
        this.lang = lang
        this.theme = theme
    }

    fun SetListview(l : ListView){
        this.l = l
    }

    fun SetAdapter(){
        val adapter = CustomAdapterListProduk(context,R.layout.custom_adapter_listproduk,ListProduk)
        adapter.SetLangTheme(lang,theme)
        l.adapter = adapter
    }

    lateinit var transDatas : ArrayList<TransaksiData>

    fun settransDatas(transDatas : ArrayList<TransaksiData>){
        this.transDatas = transDatas
    }

    fun Initiated() {

        dialog = AlertDialog.Builder(context)
                .create()
        val inflater = (context as Activity).layoutInflater
        val v = inflater.inflate(resources, null)

        bar = v.findViewById(R.id.titleBarAddProduk)
        title = v.findViewById(R.id.titleAddProduk)
        nama = v.findViewById(R.id.ProdukNameForm)
        harga = v.findViewById(R.id.ProdukPriceForm)
        unit = v.findViewById(R.id.ProductUnitForm)

        bar.setBackgroundColor(theme.BackGroundColor)
        title.setText(lang.editProdukFormLang.title)
        title.setTextColor(theme.TextColor)
        nama.setHint(lang.editProdukFormLang.nameHint)
        harga.setHint(lang.editProdukFormLang.price)
        unit.setHint(lang.addProdukFormLang.unitProduct)


        nama.setText(produk.Nama)
        harga.setText(produk.Harga.toString())
        unit.setText(produk.Satuan.toString())

        tambah = v.findViewById(R.id.buttonAddProduk)
        batal = v.findViewById(R.id.buttonCancelAddProduk)

        tambah.setText(lang.editProdukFormLang.tambah)
        batal.setText(lang.editProdukFormLang.batal)

        tambah.setBackgroundColor(theme.BackGroundColor)
        batal.setBackgroundColor(theme.BackGroundColor)

        tambah.setTextColor(theme.TextColor)
        batal.setTextColor(theme.TextColor)

        tambah.setOnClickListener(this)
        batal.setOnClickListener(this)

        dialog.setView(v)
        dialog.show()
    }

    fun CheckDataIfKosong(p : ProdukData) : Boolean{
        return p.Nama == "" || p.Harga == 0 || p.Satuan == ""
    }

    override fun onClick(p0: View?) {
        if (p0 == tambah) {

            var ketemu = false
            var pos = 0

            for (d in ListProduk){
                if (d.IdProduk == produk.IdProduk){
                    ketemu = true
                    break
                }
                pos++
            }

            if (ketemu) {
                val editP = ProdukData()
                editP.Nama = if (nama.text.toString() == "") "" else nama.text.toString()
                editP.Harga = Integer.parseInt(if (harga.text.toString() == "") "0" else harga.text.toString())
                editP.Satuan = if (unit.text.toString() == "") "" else unit.text.toString()

                if (CheckDataIfKosong(editP)) {

                    Toast.makeText(context, lang.addProdukFormLang.dataEmpty, Toast.LENGTH_SHORT).show()

                } else {
                    val editProduct = ListProduk.get(pos)
                    editProduct.Nama = editP.Nama
                    editProduct.Harga = editP.Harga
                    editProduct.Satuan = editP.Satuan
                    AlterAllProductInTrans.AlterAll(transDatas, editProduct)

                    SetAdapter()
                    dialog.dismiss()
                }
            }

        } else if (p0 == batal) {

            dialog.dismiss()

        }
    }
}