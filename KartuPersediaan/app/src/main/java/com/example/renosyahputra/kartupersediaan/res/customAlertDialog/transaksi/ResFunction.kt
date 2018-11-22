package com.example.renosyahputra.kartupersediaan.res.customAlertDialog.transaksi

import android.app.Activity
import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.renosyahputra.kartupersediaan.R
import com.example.renosyahputra.kartupersediaan.res.IdGenerator
import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.DetailTransaksi
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.KuantitasTransaksi
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData

class ResFunction {
    companion object {

        fun AddedProductToDetailTransaction(newTrans: TransaksiData,p :ProdukData){

            val detailAdded = DetailTransaksi()
            val productAdded = ProdukData()

            productAdded.IdProduk = p.IdProduk
            productAdded.Nama = p.Nama
            productAdded.Harga = p.Harga

            val id = IdGenerator()
            id.CreateRandomString(15)
            val idDetail = id.GetId()

            detailAdded.IdTransaksiData = newTrans.IdTransaksiData
            detailAdded.IdDetailTransaksiData = idDetail

            detailAdded.ProdukData = productAdded

            val ListKuantitas = ArrayList<KuantitasTransaksi>()
            val KuantitasData = KuantitasTransaksi()
            KuantitasData.IdDetailTransaksiData = idDetail
            KuantitasData.Quantity = 1
            KuantitasData.Harga = detailAdded.ProdukData.Harga
            KuantitasData.Total = KuantitasData.Harga * KuantitasData.Quantity
            ListKuantitas.add(KuantitasData)

            detailAdded.ListKuantitas = ListKuantitas

            detailAdded.ListPersediaanData = ArrayList()

            if (!CheckAndAddQtyIfAlreadyAdded(newTrans.ListDetail,detailAdded)){
                newTrans.ListDetail.add(detailAdded)
            }
        }


        fun HideKeyboard(context: Context,v: View){
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken,0)
        }

        fun SetBackgroundColor(context: Context,item : TransaksiData) : Int{
            var color_jumlah = 0
            if (item.ProductFlow == TransaksiData.ProductIn){

                color_jumlah = ResourcesCompat.getColor(context.resources, R.color.greenMoney,null)

            }else if (item.ProductFlow == TransaksiData.ProductOut){

                color_jumlah = ResourcesCompat.getColor(context.resources, R.color.red,null)
            }

            return color_jumlah
        }

        fun CompareDateAndTimeIfSameDontAccept(t  :ArrayList<TransaksiData>,newTrans: TransaksiData) : Boolean {
            var Ketemu = false
            for (data in t.sortedWith(compareBy({ it.TanggalTransaksi.Tahun }, { it.TanggalTransaksi.Bulan }, { it.TanggalTransaksi.Hari }, { it.Jam.Jam }, { it.Jam.Menit }))){
                if (data.IdTransaksiData != newTrans.IdTransaksiData && data.TanggalTransaksi.toDateString() == newTrans.TanggalTransaksi.toDateString() && data.Jam.MakeJamString() == newTrans.Jam.MakeJamString()){
                    Ketemu = true
                    break
                }
            }

            return Ketemu
        }

        fun CheckAndAddQtyIfAlreadyAdded(l : ArrayList<DetailTransaksi>, dt : DetailTransaksi) : Boolean{
            var ketemu = false
            var pos = 0
            for (d in 0..(l.size)-1){
                if (l.get(d).ProdukData.IdProduk == dt.ProdukData.IdProduk){
                    ketemu = true
                    pos = d
                    break
                }
            }

            if (ketemu){
                l.get(pos).SetQuantityAll( l.get(pos).GetKuantitas() + 1)
            }

            return ketemu
        }

        fun getTotalFromDetail(t : TransaksiData) : Int{
            var total = 0
            for (a in t.ListDetail){
                total += a.GetTotalListKuantitas()
            }
            return total
        }


        fun FinalQtyCheckInNewTrans(newTrans : TransaksiData,p : ProdukData,dts : ArrayList<DetailTransaksi>,t : ArrayList<TransaksiData>) : Boolean{
            var istQtyMin = false
            for (detail in dts){
                 if (detail.ProdukData.IdProduk == p.IdProduk){
                    if ((GetTotalQtyProductFromAllTrans(newTrans,p,t) - detail.GetKuantitas()) < 0){
                        istQtyMin = true
                        break
                    }
                }
            }
            return istQtyMin
        }

        fun GetTotalQtyProductFromAllTrans(newTrans : TransaksiData,p : ProdukData,t : ArrayList<TransaksiData>) : Int{
            var qty = 0
            for (trans in t.sortedWith(compareBy({ it.TanggalTransaksi.Tahun }, { it.TanggalTransaksi.Bulan }, { it.TanggalTransaksi.Hari }, { it.Jam.Jam }, { it.Jam.Menit }))){

                if (newTrans.TanggalTransaksi.BandingkanTanggalYangLebihKecil(trans.Jam,newTrans.Jam,trans.TanggalTransaksi) && newTrans.IdTransaksiData != trans.IdTransaksiData) {

                    for (dt in trans.ListDetail) {
                        if (dt.ProdukData.IdProduk == p.IdProduk) {
                            if (trans.ProductFlow == TransaksiData.ProductIn) {
                                qty += dt.GetKuantitas()
                            } else if (trans.ProductFlow == TransaksiData.ProductOut) {
                                qty -= dt.GetKuantitas()
                            }


                        }
                    }

                }
            }
            return qty
        }
    }
}