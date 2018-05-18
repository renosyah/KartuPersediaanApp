package com.example.renosyahputra.kartupersediaan.ui.lang

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.addProdukFormLang.AddProdukFormLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.addTransDialogLang.AddTransDialogLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.editProdukFormLang.EditProdukFormLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.laporanMenuLang.LaporanMenuLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.loginLang.LoginLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.mainMenuAddTaskLang.MainMenuAddTaskLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.mainMenuLang.MainMenuLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.mainMenuSettingLang.MainMenuSettingLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.printLaporanLang.PrintLaporanLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.subMenuProdukLang.SubMenuProdukLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.subMenuTransLang.SubMenuTransLang
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader

class LangSetting(ctx : Context){

    val context : Context = ctx
    val langObj = LangObj()
    companion object {
        val SetInglisLang : String = "ENGLISH"
        val SetIdoLang : String = "INDONESIA"
    }

    val FilenameLangSetting : String = "Langsetting.txt"

    fun GetlangObj() : LangObj{
        return langObj
    }

    private fun SetInglish() {

        val mainMenuLang = MainMenuLang()
        mainMenuLang.MenuSetting2 = "Exit"
        mainMenuLang.MenuSetting1 = "Setting"
        mainMenuLang.subMenu1 = "Supplies Card"
        mainMenuLang.subMenu2 = "Transaction Menu"
        mainMenuLang.subMenu3 = "Product Menu"
        mainMenuLang.subMenu4 = "Logout"

        val mainMenuSettingLang = MainMenuSettingLang()
        mainMenuSettingLang.OpenSetting1 = "Language Setting"
        mainMenuSettingLang.OpenSetting2 = "Theme Setting"
        mainMenuSettingLang.Cancel = "Cancel"
        mainMenuSettingLang.TitleOpenSetting1 = "Select Language"
        mainMenuSettingLang.TitleOpenSetting2 = "Choose Theme"
        mainMenuSettingLang.Back = "Back"

        val mainMenuAddTaskLang = MainMenuAddTaskLang()
        mainMenuAddTaskLang.title = "Add Data"
        mainMenuAddTaskLang.addProduct = "Add Product"
        mainMenuAddTaskLang.addTransaction = "Add Transaction"
        mainMenuAddTaskLang.cancel = "Cancel"
        mainMenuSettingLang.typeLaporan = "Type Report"

        val subMenuProdukLang = SubMenuProdukLang()
        subMenuProdukLang.CariProduk = "Search Product..."
        subMenuProdukLang.ProdukKosong = "Product Is Empty"
        subMenuProdukLang.ProdukTidakKetemu = "Product Not Found"
        subMenuProdukLang.titleDetailProduk  = "Detail Product"
        subMenuProdukLang.namaDetail = "Product Name"
        subMenuProdukLang.harga = "Price"
        subMenuProdukLang.hapus = "Delete"
        subMenuProdukLang.batal = "Cancel"
        subMenuProdukLang.edit = "Edit"

        val addProdukFormLang = AddProdukFormLang()
        addProdukFormLang.title = "Add Product"
        addProdukFormLang.batal = "Cancel"
        addProdukFormLang.tambah = "Add"
        addProdukFormLang.nameHint = "Product Name..."
        addProdukFormLang.price = "Price..."

        val editProdukFormLang = EditProdukFormLang()
        editProdukFormLang.title = "Edit product"
        editProdukFormLang.batal = "Cancel"
        editProdukFormLang.tambah = "Save"
        editProdukFormLang.nameHint = "Product Name..."
        editProdukFormLang.price = "Price..."

        val subMenuTransLang = SubMenuTransLang()
        subMenuTransLang.CariTrans = "Find Transaction..."
        subMenuTransLang.TransKosong = "Transaction is Empty"
        subMenuTransLang.TransTidakKetemu = "Transaction not Found"
        subMenuTransLang.titleDetailTrans = "Detail Transaction"
        subMenuTransLang.namaTrans = "Information"
        subMenuTransLang.total = "SubTotal"
        subMenuTransLang.hapus = "Delete"
        subMenuTransLang.batal = "Cancel"
        subMenuTransLang.edit = "Edit"
        subMenuTransLang.lihatDetail = "See Detail"


        val addTransDialogLang = AddTransDialogLang()
        addTransDialogLang.titleForEdit = "Edit Transaction"
        addTransDialogLang.title = "Add Transaction Form"
        addTransDialogLang.chooseType = "Choose Transaction Type"
        addTransDialogLang.chooseData = "Choose Date"
        addTransDialogLang.addInformation = "Add Information ..."
        addTransDialogLang.inputInformation = "Input Information ..."
        addTransDialogLang.addProduct = "Add Product"
        addTransDialogLang.chooseTime = "Choose Time"

        addTransDialogLang.nameProduct = "Product Name"
        addTransDialogLang.price = "Price"
        addTransDialogLang.qty = "Quantity"

        addTransDialogLang.add = "Ok"
        addTransDialogLang.cancel = "Cancel"

        addTransDialogLang.titleEditDetail = "Edit Detail Transaction"
        addTransDialogLang.save = "Save"
        addTransDialogLang.hapus = "Delete"

        addTransDialogLang.warningProductQtyLow = "Warning!,Stock of this Product is"
        addTransDialogLang.CheckAgain = "there is some transaction data left empty, please check again!"

        addTransDialogLang.warningThereIsProductQtyLow = "Warning, there is some Product Stock is below require"

        val laporanMenuLang =  LaporanMenuLang()
        laporanMenuLang.Kartupersediaan = "Supplies Card"
        laporanMenuLang.filterPilihMethod = "Method"
        laporanMenuLang.filterPilihPeriode = "Period"
        laporanMenuLang.filterPilihProduk = "Product"
        laporanMenuLang.KartuPersediaanKosong = "Supplies Card Is Empty"
        laporanMenuLang.namaProductDetail = "Product Name"
        laporanMenuLang.price = "Price"
        laporanMenuLang.qty = "Quantity In Transaction"
        laporanMenuLang.total = "Total"
        laporanMenuLang.stokKe = "Stock Number"
        laporanMenuLang.exportTitle = "Print Document"
        laporanMenuLang.toPDF = "Save as .PDF"
        laporanMenuLang.toHTML = "Save as .HTML"
        laporanMenuLang.save = "Save"
        laporanMenuLang.cancel = "Cancel"
        laporanMenuLang.saved = "File Hass been saved in Folder KartuPersediaan"
        laporanMenuLang.titleLaporan = "Supplies Card"
        laporanMenuLang.dateAwalLap = "From"
        laporanMenuLang.dateAkhirLap = ""
        laporanMenuLang.hinga = "To"

        val printLaporanLang = PrintLaporanLang()
        printLaporanLang.Pembelian = "Product In"
        printLaporanLang.Penjualan = "Product Out"
        printLaporanLang.tgl = "Date"
        printLaporanLang.keterangan = "Information"
        printLaporanLang.namaP = "Product Name"
        printLaporanLang.hargaP = "Price"
        printLaporanLang.qtyP = "Quantity"
        printLaporanLang.total = "Total"
        printLaporanLang.stok = "Stock"
        printLaporanLang.metode = "Method"


        val loginLang = LoginLang()
        loginLang.name = "Owner Name"
        loginLang.email = "Email"
        loginLang.company = "Company Name"
        loginLang.register = "Register"
        loginLang.title = "Register Form"
        loginLang.login = "Login"
        loginLang.setting = "Setting"
        loginLang.inputEmpty = "there is some empty form, please check again!"


        langObj.mainMenuLang = mainMenuLang
        langObj.mainMenuSettingLang = mainMenuSettingLang
        langObj.mainMenuAddTaskLang = mainMenuAddTaskLang
        langObj.subMenuProdukLang = subMenuProdukLang
        langObj.addProdukFormLang = addProdukFormLang
        langObj.editProdukFormLang = editProdukFormLang
        langObj.subMenuTransLang = subMenuTransLang
        langObj.addTransDialogLang = addTransDialogLang
        langObj.laporanMenuLang = laporanMenuLang
        langObj.printLaporanLang = printLaporanLang
        langObj.loginLang = loginLang
    }
    private fun SetIndo(){

        val mainMenuLang = MainMenuLang()
        mainMenuLang.MenuSetting2 = "Keluar"
        mainMenuLang.MenuSetting1 = "Pengaturan"
        mainMenuLang.subMenu1 = "Kartu Persediaan"
        mainMenuLang.subMenu2 = "Menu Transaksi"
        mainMenuLang.subMenu3 = "Menu Produk"
        mainMenuLang.subMenu4 = "Logout"

        val mainMenuSettingLang = MainMenuSettingLang()
        mainMenuSettingLang.OpenSetting1 = "Pengaturan Bahasa"
        mainMenuSettingLang.OpenSetting2 = "Pengaturan Tema"
        mainMenuSettingLang.Cancel = "Batal"
        mainMenuSettingLang.TitleOpenSetting1 = "Tetapkan Bahasa"
        mainMenuSettingLang.TitleOpenSetting2 = "Pilih"
        mainMenuSettingLang.Back = "Kembali"
        mainMenuSettingLang.typeLaporan = "Tipe Laporan"

        val mainMenuAddTaskLang = MainMenuAddTaskLang()
        mainMenuAddTaskLang.title = "Penambahan Data"
        mainMenuAddTaskLang.addProduct = "Tambah Produk"
        mainMenuAddTaskLang.addTransaction = "Tambah Transaksi"
        mainMenuAddTaskLang.cancel = "Batal"

        val subMenuProdukLang = SubMenuProdukLang()
        subMenuProdukLang.CariProduk = "Cari Produk..."
        subMenuProdukLang.ProdukKosong = "Produk Kosong"
        subMenuProdukLang.ProdukTidakKetemu = "Produk Tidak Ketemu"
        subMenuProdukLang.titleDetailProduk  = "Produk Detail"
        subMenuProdukLang.namaDetail = "Nama Produk"
        subMenuProdukLang.harga = "Harga"
        subMenuProdukLang.hapus = "Hapus"
        subMenuProdukLang.batal = "Batal"
        subMenuProdukLang.edit = "Edit"


        val addProdukFormLang = AddProdukFormLang()
        addProdukFormLang.title = "Form Tambah Produk"
        addProdukFormLang.batal = "Batal"
        addProdukFormLang.tambah = "Tambah"
        addProdukFormLang.nameHint = "Nama Produk..."
        addProdukFormLang.price = "Harga..."


        val editProdukFormLang = EditProdukFormLang()
        editProdukFormLang.title = "Form Edit Produk"
        editProdukFormLang.batal = "Batal"
        editProdukFormLang.tambah = "Simpan"
        editProdukFormLang.nameHint = "Nama Produk..."
        editProdukFormLang.price = "Harga..."


        val subMenuTransLang = SubMenuTransLang()
        subMenuTransLang.CariTrans = "Cari Transaksi..."
        subMenuTransLang.TransKosong = "Transaksi Kosong"
        subMenuTransLang.TransTidakKetemu = "Transaksi Tidak Ketemu"
        subMenuTransLang.titleDetailTrans = "Detail Transaksi"
        subMenuTransLang.namaTrans = "Keterangan"
        subMenuTransLang.total = "SubTotal"
        subMenuTransLang.hapus = "Hapus"
        subMenuTransLang.batal = "Batal"
        subMenuTransLang.edit = "Edit"
        subMenuTransLang.lihatDetail = "Lihat Detail"


        val addTransDialogLang = AddTransDialogLang()
        addTransDialogLang.titleForEdit = "Form Edit Transaksi"
        addTransDialogLang.title = "Form Tambah Transaksi"
        addTransDialogLang.chooseType = "Pilih Tipe Transaksi"
        addTransDialogLang.chooseData = "Pilih Tanggal"
        addTransDialogLang.addInformation = "Tambah Keterangan ..."
        addTransDialogLang.inputInformation = "Input Keterangan ..."
        addTransDialogLang.addProduct = "Tambah Produk"
        addTransDialogLang.chooseTime = "Pilih Jam"

        addTransDialogLang.nameProduct = "Nama Produk"
        addTransDialogLang.price = "Harga"
        addTransDialogLang.qty = "Kuantitas"

        addTransDialogLang.add = "Ok"
        addTransDialogLang.cancel = "Batal"

        addTransDialogLang.titleEditDetail = "Edit Detail Transaksi"
        addTransDialogLang.save = "Simpan"
        addTransDialogLang.hapus = "Hapus"

        addTransDialogLang.warningProductQtyLow = "Perhatian!,Stok produk Ini"
        addTransDialogLang.CheckAgain = "Ada Data Transaksi yg belum diisi, Cek Lagi!"

        addTransDialogLang.warningThereIsProductQtyLow = "Perhatian, ada Stok produk yang tidak memenuhi kebutuhan!"

        val laporanMenuLang =  LaporanMenuLang()
        laporanMenuLang.Kartupersediaan = "Kartu Persediaan"
        laporanMenuLang.filterPilihMethod = "Metode"
        laporanMenuLang.filterPilihPeriode = "Periode"
        laporanMenuLang.filterPilihProduk = "Produk"
        laporanMenuLang.KartuPersediaanKosong = "Kartu Persediaan Kosong"
        laporanMenuLang.namaProductDetail = "Nama Produk"
        laporanMenuLang.price = "Harga"
        laporanMenuLang.qty = "Kuantitas Di Transaksi"
        laporanMenuLang.total = "Total"
        laporanMenuLang.stokKe = "Nomor Stock"
        laporanMenuLang.exportTitle = "Cetak Dokumen"
        laporanMenuLang.toPDF = "Simpan Sebagai .PDF"
        laporanMenuLang.toHTML = "Simpan Sebagai .HTML"
        laporanMenuLang.save = "Simpan"
        laporanMenuLang.cancel = "Batal"
        laporanMenuLang.saved = "File Tersimpan di Folder KartuPersediaan"
        laporanMenuLang.titleLaporan = "Kartu Persediaan Barang"
        laporanMenuLang.dateAwalLap = "Dari"
        laporanMenuLang.dateAkhirLap = ""
        laporanMenuLang.hinga = "Hingga"

        val printLaporanLang = PrintLaporanLang()
        printLaporanLang.Pembelian = "Produk Masuk"
        printLaporanLang.Penjualan = "Produk Keluar"
        printLaporanLang.tgl = "Tanggal"
        printLaporanLang.keterangan = "Keterangan"
        printLaporanLang.namaP = "Nama Produk"
        printLaporanLang.hargaP = "Harga"
        printLaporanLang.qtyP = "Kuantitas"
        printLaporanLang.total = "Total"
        printLaporanLang.stok = "Kuantitas"
        printLaporanLang.metode = "Metode Pencatatan"


        val loginLang = LoginLang()
        loginLang.name = "Nama Pemilik"
        loginLang.email = "Email"
        loginLang.company = "Nama Perusahaan"
        loginLang.register = "Registrasi"
        loginLang.title = "Form Registrasi"
        loginLang.login = "Login"
        loginLang.setting = "Pengaturan"
        loginLang.inputEmpty = "ada form yg kosong, tolong cek kembali!"

        langObj.mainMenuLang = mainMenuLang
        langObj.mainMenuSettingLang = mainMenuSettingLang
        langObj.mainMenuAddTaskLang = mainMenuAddTaskLang
        langObj.subMenuProdukLang = subMenuProdukLang
        langObj.addProdukFormLang = addProdukFormLang
        langObj.editProdukFormLang = editProdukFormLang
        langObj.subMenuTransLang = subMenuTransLang
        langObj.addTransDialogLang = addTransDialogLang
        langObj.laporanMenuLang = laporanMenuLang
        langObj.printLaporanLang = printLaporanLang
        langObj.loginLang = loginLang
    }


    fun ChangeLang(s : String){
        if (s ==  SetInglisLang){
            SetInglish()
        }else if(s == SetIdoLang){
            SetIndo()
        }
    }

    fun SetLangSetting(setting : String){
        save_data(context, setting,FilenameLangSetting)
    }

    fun CheckLangSetting(){

        val SettingLangData : String = load_data(FilenameLangSetting,context)

        if (SettingLangData ==  SetInglisLang){
            SetInglish()
        }else if(SettingLangData == SetIdoLang){
            SetIndo()
        }else {
            SetIndo()
        }
    }

    fun save_data(context: Context, data_disimpan: String, nama_file: String) {
        val data_save = false
        try {
            val fileOutputStream = context.openFileOutput(nama_file, MODE_PRIVATE)
            fileOutputStream.write(data_disimpan.toByteArray())


        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun load_data(nama_file: String, context: Context): String {
        val stringbufer = StringBuffer()
        try {
            val fileinputstream = (context as Activity).openFileInput(nama_file)
            val reader = InputStreamReader(fileinputstream)
            val buferreader = BufferedReader(reader)

            for (pesan in buferreader.readLine()) {
                stringbufer.append(pesan)
            }

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return stringbufer.toString()
    }
}
