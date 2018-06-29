package com.example.renosyahputra.kartupersediaan.res

import com.example.renosyahputra.kartupersediaan.res.obj.transaksiData.TransaksiData

class FindHowMuchGapBetweenTrans {
    companion object {
        fun FindGap(transList : ArrayList<TransaksiData>) : Int {
            val GapHolder = ArrayList<Int>()
            var Found = 0

            for (d in transList){
                if (d.ProductFlow == TransaksiData.ProductIn){
                    Found += 1
                }else if (d.ProductFlow == TransaksiData.ProductOut){
                    GapHolder.add(Found)
                    Found = 0
                }
            }

            var Holder = 0
            for (i in GapHolder){
                if (Holder < i){
                    Holder = i
                }
            }

            if (Holder == 0){
                Holder = 10
            }

            return Holder
        }
    }
}