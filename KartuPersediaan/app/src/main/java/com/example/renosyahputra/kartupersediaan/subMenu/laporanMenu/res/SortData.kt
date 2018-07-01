package com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.res

import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData

class SortData{
    companion object {
        fun GetAllPeriodeInGroup(data: ArrayList<TransaksiData>): ArrayList<Int> {
            val firstValue = ArrayList<Int>()
            for (tahun in data.sortedWith(compareBy({it.TanggalTransaksi.Tahun}))) {
                firstValue.add(tahun.TanggalTransaksi.Tahun)
            }
            val secondValue = ArrayList<Int>()
            for (tahun in ArrayList<Int>(HashSet<Int>(firstValue)).sortedWith(compareBy({ it }))){
                secondValue.add(tahun)
            }
            return secondValue

        }
    }
}