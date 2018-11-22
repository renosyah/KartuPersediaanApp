package com.example.renosyahputra.kartupersediaan.res.customAlertDialog.cariTanggalLaporan.res

import com.example.renosyahputra.kartupersediaan.subMenu.laporanMenu.KartuPersediaanMenu

class CariTanggalLaporanRes {
    interface OnCariTanggalLaporan {
        fun OnFinishSelectFirstAndLastDate(text : String, filter: KartuPersediaanMenu.FilterCard)
    }
}