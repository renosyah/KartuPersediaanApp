package com.example.renosyahputra.kartupersediaan.ui.lang.obj.printLaporanLang

import java.io.Serializable

class PrintLaporanLang : Serializable {
    lateinit var tgl : String
    lateinit var keterangan : String
    lateinit var Pembelian : String
    lateinit var Penjualan : String
    lateinit var namaP : String
    lateinit var unitP : String
    lateinit var defaultHargaP : String
    lateinit var hargaP : String
    lateinit var qtyP : String
    lateinit var total : String
    lateinit var stok : String
    lateinit var metode : String

    lateinit var MenyiapkanPrintTitleDialog : String
    lateinit var MenyiapkanPrintMessageDialog  :String

    lateinit var PrintNowTitleDialog : String
    lateinit var PrintNowMessageDialog  :String

}