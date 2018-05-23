package com.example.renosyahputra.kartupersediaan.res.customAlertDialog.transaksi

import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.DetailTransaksi
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.KuantitasTransaksi
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
                val ListKuantitas = ArrayList<KuantitasTransaksi>()
                val KuantitasData = KuantitasTransaksi()
                KuantitasData.Quantity += 1
                KuantitasData.Total = KuantitasData.Harga * KuantitasData.Quantity
                ListKuantitas.add(KuantitasData)

                l.get(pos).ListKuantitas = ListKuantitas
            }

            return !(ketemu)
        }

        fun getTotalFromDetail(t : TransaksiData, d : ArrayList<DetailTransaksi>) : Int{
            var total = 0
            for (a in d){
                if (t.ProductFlow == TransaksiData.ProductIn){
                    total += a.GetTotalListKuantitas()
                }else if (t.ProductFlow == TransaksiData.ProductOut) {
                    total -= a.GetTotalListKuantitas()
                }
            }
            if (total < 0){
                total = -total
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
            for (trans in t){

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