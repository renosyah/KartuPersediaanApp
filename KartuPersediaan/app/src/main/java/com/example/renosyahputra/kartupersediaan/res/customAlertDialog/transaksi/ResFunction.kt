package com.example.renosyahputra.kartupersediaan.res.customAlertDialog.transaksi

import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.DetailTransaksi
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.FormatTanggal
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.FormatWaktu
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData

class ResFunction {
    companion object {
        fun CheckAndAddQtyIfSame(l : ArrayList<DetailTransaksi>, dt : DetailTransaksi) : Boolean{
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
                l.get(pos).Quantity += dt.Quantity
            }

            return !(ketemu)
        }

        fun getTotalFromDetail(t : TransaksiData, d : ArrayList<DetailTransaksi>) : Int{
            var total = 0
            for (a in d){
                if (t.ProductFlow == TransaksiData.ProductIn){
                    total += a.Quantity * a.ProdukData.Harga
                }else if (t.ProductFlow == TransaksiData.ProductOut) {
                    total -= a.Quantity * a.ProdukData.Harga
                }
            }
            if (total < 0){
                total = -total
            }

            return total
        }

        fun FindSameDate(time : FormatWaktu,date : FormatTanggal, transList : ArrayList<TransaksiData>) : Boolean {
            var ketemu = false
            for (d in transList){
                if (d.TanggalTransaksi.toDateString() == date.toDateString() && time.MakeJamString() == d.Jam.MakeJamString()){
                    ketemu = true
                    break
                }
            }
            return ketemu
        }

        fun FinalQtyCheckInNewTrans(newTrans : TransaksiData,p : ProdukData,dts : ArrayList<DetailTransaksi>,t : ArrayList<TransaksiData>) : Boolean{
            var istQtyMin = false
            for (detail in dts){
                 if (detail.ProdukData.IdProduk == p.IdProduk){
                    if ((GetTotalQtyProductFromAllTrans(newTrans,p,t) - detail.Quantity) < 0){
                        istQtyMin = true
                        break
                    }
                }
            }
            return istQtyMin
        }

        fun GetTotalQtyProductFromAllTrans(newTrans : TransaksiData,p : ProdukData,t : ArrayList<TransaksiData>) : Int{
            var qty = 0
            for (trans in t){

                if (newTrans.TanggalTransaksi.BandingkanTanggalYangLebihKecil(trans.Jam,newTrans.Jam,trans.TanggalTransaksi) && newTrans.IdTransaksiData != trans.IdTransaksiData) {

                    for (dt in trans.ListDetail) {
                        if (dt.ProdukData.IdProduk == p.IdProduk) {
                            if (trans.ProductFlow == TransaksiData.ProductIn) {
                                qty += dt.Quantity
                            } else if (trans.ProductFlow == TransaksiData.ProductOut) {
                                qty -= dt.Quantity
                            }


                        }
                    }

                }
            }
            return qty
        }
    }
}