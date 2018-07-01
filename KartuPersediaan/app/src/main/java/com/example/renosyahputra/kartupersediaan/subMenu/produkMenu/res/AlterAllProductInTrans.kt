package com.example.renosyahputra.kartupersediaan.subMenu.produkMenu.res

import com.example.renosyahputra.kartupersediaan.res.obj.produkData.ProdukData
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.DetailTransaksi
import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData

class AlterAllProductInTrans{
    companion object {

        fun getTotalFromDetail(t : TransaksiData,d : ArrayList<DetailTransaksi>) : Int{
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

        fun DeleteAllProduct(t : ArrayList<TransaksiData>,p : ProdukData){
            for (i in 0..(t.size)-1){
                val transData = t.get(i)
                for (o in 0..(transData.ListDetail.size)-1){
                    val detailTransData = transData.ListDetail.get(o)
                    if (detailTransData.ProdukData.IdProduk == p.IdProduk){
                        transData.ListDetail.removeAt(o)
                        break
                    }
                }
                t.get(i).SubTotal = getTotalFromDetail(t.get(i), t.get(i).ListDetail)
            }
        }

        fun AlterAll(t : ArrayList<TransaksiData>,p : ProdukData){
            for (i in t){
                for (o in i.ListDetail){
                    if (o.ProdukData.IdProduk == p.IdProduk){
                        //o.ProdukData.Harga = p.Harga
                        o.ProdukData.Nama = p.Nama
                    }
                }
                i.SubTotal = getTotalFromDetail(i, i.ListDetail)
            }
        }

    }
}