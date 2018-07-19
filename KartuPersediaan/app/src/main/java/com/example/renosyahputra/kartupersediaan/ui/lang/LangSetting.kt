package com.example.renosyahputra.kartupersediaan.ui.lang

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.LangObj
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.addProdukFormLang.AddProdukFormLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.addTransDialogLang.AddTransDialogLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.cariTanggalLaporanLang.CariTanggalLaporanLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.devModeLang.DevModeLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.editProdukFormLang.EditProdukFormLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.guideLang.GuideLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.laporanMenuLang.LaporanMenuLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.loginDialogLang.LoginDialogLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.logoutDialogLang.LogoutDialogLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.mainMenuAddTaskLang.MainMenuAddTaskLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.mainMenuLang.MainMenuLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.mainMenuSettingLang.MainMenuSettingLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.montInString.MonthInString
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.printLaporanLang.PrintLaporanLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.registerLang.RegisterLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.saveOnlineDialogLang.SaveOnlineDialogLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.subMenuProdukLang.SubMenuProdukLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.subMenuTransLang.SubMenuTransLang
import com.example.renosyahputra.kartupersediaan.ui.lang.obj.userDataSettingLang.UserDataSettingLang
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

        fun load_data_for_public(nama_file: String, context: Context): String {
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

    val FilenameLangSetting : String = "Langsetting.txt"

    fun GetlangObj() : LangObj{
        return langObj
    }

    private fun SetInglish() {

        val mainMenuLang = MainMenuLang()
        mainMenuLang.MenuSetting2 = "Exit"
        mainMenuLang.MenuSetting1 = "Setting"
        mainMenuLang.MenuSetting3 = "Save Online"
        mainMenuLang.MenuSetting4 = "Login"
        mainMenuLang.MenuSetting5 = "Guide"
        mainMenuLang.subMenu1 = "Stock Card"
        mainMenuLang.subMenu2 = "Transaction Menu"
        mainMenuLang.subMenu3 = "Product Menu"
        mainMenuLang.subMenu4 = "Logout"
        mainMenuLang.Saving = "Saving.."
        mainMenuLang.SavingComplete = "Saving Complete!"
        mainMenuLang.SavingFail = "Saving Fail!"
        mainMenuLang.SaveDataOnlineButAccountNotValid = "Fail to save, please check your account setting, some data is missing!"
        mainMenuLang.SavingButDataIsTooBig = "Saving.., but data is too big!"

        val mainMenuSettingLang = MainMenuSettingLang()
        mainMenuSettingLang.OpenSetting1 = "Language Setting"
        mainMenuSettingLang.OpenSetting2 = "Theme Setting"
        mainMenuSettingLang.OpenSetting3 = "User Profile"
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
        addProdukFormLang.unitProduct = "Unit..."
        addProdukFormLang.dataEmpty = "There is some empty form, please fill it!"

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
        addTransDialogLang.addProduct = "Add Product To Detail"
        addTransDialogLang.chooseTime = "Choose Time"
        addTransDialogLang.TypeIn = "Product In"
        addTransDialogLang.TypeOUT = "Product Out"

        addTransDialogLang.nameProduct = "Product Name"
        addTransDialogLang.price = "Price"
        addTransDialogLang.qty = "Quantity"
        addTransDialogLang.StokTersedia = "Stock Left"
        addTransDialogLang.Stok = "Stock"

        addTransDialogLang.add = "Ok"
        addTransDialogLang.Ok = "Ok"
        addTransDialogLang.cancel = "Cancel"

        addTransDialogLang.titleEditDetail = "Edit Detail Transaction"
        addTransDialogLang.save = "Save"
        addTransDialogLang.hapus = "Delete"

        addTransDialogLang.warningProductQtyLow = "Warning!,Stock of this Product is"
        addTransDialogLang.CheckAgain = "there is some transaction data left empty, please check again!"

        addTransDialogLang.warningThereIsProductQtyLow = "Warning, there is some Product Stock is below require"

        addTransDialogLang.tutup = "Close"

        val laporanMenuLang =  LaporanMenuLang()
        laporanMenuLang.Kartupersediaan = "Stock Card"
        laporanMenuLang.filterPilihMethod = "Method"
        laporanMenuLang.filterPilihPeriode = "Date"
        laporanMenuLang.filterPilihProduk = "Product"
        laporanMenuLang.KartuPersediaanKosong = "Stock Card Is Empty"
        laporanMenuLang.namaProductDetail = "Product Name"
        laporanMenuLang.price = "Price"
        laporanMenuLang.qty = "Total Quantity"
        laporanMenuLang.total = "Total"
        laporanMenuLang.stokKe = "Stock Number"
        laporanMenuLang.KuantitasKe = "Quantity Number"
        laporanMenuLang.titleStockForListStock = "Stock Balance"
        laporanMenuLang.titleTransForListQty = "Transaction Balance"
        laporanMenuLang.exportTitle = "Print Document"
        laporanMenuLang.toPDF = "PDF (Potrait)"
        laporanMenuLang.toPDFLANDSCAPE = "PDF (Landscape)"
        laporanMenuLang.save = "Save"
        laporanMenuLang.cancel = "Cancel"
        laporanMenuLang.saved = "File Hass been saved in Folder KartuPersediaan"
        laporanMenuLang.titleLaporan = "Stock Card"
        laporanMenuLang.dateAwalLap = "From"
        laporanMenuLang.dateAkhirLap = ""
        laporanMenuLang.hinga = "-"
        laporanMenuLang.WarningInvalidProduckInTrans = "there is some transaction with invalid product quantity, please check!"
        laporanMenuLang.WarningInvalidProduckInTransTitle = "Warning!"
        laporanMenuLang.titleOpenFile = "Document Saved"
        laporanMenuLang.messageOpenFile = "File Hass been saved in Folder KartuPersediaan,would you like to open it?"
        laporanMenuLang.ok = "Open"
        laporanMenuLang.no = "No"
        laporanMenuLang.FilterChooseAllProduct = "All Product"
        laporanMenuLang.FiterChooseAllPeriod = "All Dates"
        laporanMenuLang.titlePeriodeTahun = "Choose Year"

        val printLaporanLang = PrintLaporanLang()
        printLaporanLang.Pembelian = "Product In"
        printLaporanLang.Penjualan = "Product Out"
        printLaporanLang.tgl = "Date"
        printLaporanLang.keterangan = "Information"
        printLaporanLang.defaultHargaP = "Default Price"
        printLaporanLang.unitP = "Unit"
        printLaporanLang.namaP = "Product Name"
        printLaporanLang.hargaP = "Price"
        printLaporanLang.qtyP = "Quantity"
        printLaporanLang.total = "Total"
        printLaporanLang.stok = "Stock"
        printLaporanLang.metode = "Method"
        printLaporanLang.MenyiapkanPrintTitleDialog = "Preparing data"
        printLaporanLang.MenyiapkanPrintMessageDialog  = "Preparing all the data...."
        printLaporanLang.PrintNowTitleDialog = "Print"
        printLaporanLang.PrintNowMessageDialog ="printing all the document,please wait...."


        val registerLang = RegisterLang()
        registerLang.name = "Owner Name"
        registerLang.email = "Email"
        registerLang.company = "Company Name"
        registerLang.register = "Register"
        registerLang.title = "Register Form"
        registerLang.login = "Login"
        registerLang.setting = "Setting"
        registerLang.inputEmpty = "there is some empty form, please check again!"


        val monthInString = MonthInString()
        monthInString.Januari = "January"
        monthInString.Februari = "February"
        monthInString.Maret = "March"
        monthInString.April = "April"
        monthInString.Mei = "May"
        monthInString.Juni = "June"
        monthInString.Juli = "July"
        monthInString.Agustus = "August"
        monthInString.September = "September"
        monthInString.Oktober = "October"
        monthInString.November = "November"
        monthInString.Desember = "Desember"

        val userDataSettingLang = UserDataSettingLang()
        userDataSettingLang.Title = "Edit User Profile"
        userDataSettingLang.EditNamaPemilik = "Edit Owner Name"
        userDataSettingLang.EditNamaPerusahaan = "Edit Company Name"
        userDataSettingLang.EditEmail = "Edit Email"
        userDataSettingLang.EditUsername = "Edit Username"
        userDataSettingLang.EditPassword = "Old Password"
        userDataSettingLang.KonfirmEditPassword = "New Password"
        userDataSettingLang.Simpan = "Save"
        userDataSettingLang.Batal = "Cancel"
        userDataSettingLang.DataKosong = "there is some user data need to be fill!"
        userDataSettingLang.GagalValidasiPassword = "Fail to validate your password!"
        userDataSettingLang.KonfirmasiDeanganMengisisPassword = "Konfirm save with password fill!"

        val loginDialogLang = LoginDialogLang()
        loginDialogLang.title = "Login Form"
        loginDialogLang.username = "Username or Email"
        loginDialogLang.password = "Input Password"
        loginDialogLang.login = "Login"
        loginDialogLang.cancel = "Cancel"
        loginDialogLang.failLogin = "Fail to Login to your account!"
        loginDialogLang.emptyForm = "There is some empty form, please fill it!"
        loginDialogLang.LoginDialogTitle = "Login"
        loginDialogLang.LoginDialogMessage = "Login,please wait..."

        val guideLang = GuideLang()
        guideLang.appName = "Kartu Persediaan"
        guideLang.appVer = "Version 1.6"
        guideLang.title = "Guide"
        guideLang.titleForSocialMediaPages = "Visit My Social media's Page on : "
        guideLang.guides = "Stock Card Cards are Cards used to record Inventory of goods, Inventory Cards can be used to record Merchandise Inventory in a trading company, recording Supplies of Raw Materials and Substituents for manufacturing companies.\n" +
                "\n" +
                "This inventory card application makes it easy for users to be able to compile inventory card statements, just by inputting product data and transactions, automatically the application will compile all inventory card statements with 3 recording methods at once, so users can easily see the comparison of each report .\n" +
                "\n" +
                "here are the usage guidelines for this app:\n" +
                "\n" +
                "1). after registering, you will be in the main menu, the main menu consists of 3 menus, product menu, transactions, and card supplies.\n" +
                "\n" +
                "2). add some product data by pressing the button in the bottom right corner, and select product added menu, input name, standard price and unit.\n" +
                "\n" +
                "3). when done, add some transaction data. by pressing the same button, and select the add transaction menu, enter transaction time, date, description, IN transaction type for OUT product transaction type and OUT for outgoing product transaction type, and add some products into transaction and specify quantity dah respective price product.\n" +
                "\n" +
                "4). when done, select the menu of the inventory card, and automatically report will be displayed\n" +
                "\n" +
                ""


        val cariTanggalLaporanLang = CariTanggalLaporanLang()
        cariTanggalLaporanLang.title = "Transaction's Date"
        cariTanggalLaporanLang.Tampilkan = "Show"
        cariTanggalLaporanLang.Batal = "Cancel"
        cariTanggalLaporanLang.awal = "First Date"
        cariTanggalLaporanLang.akhir = "Last Date"
        cariTanggalLaporanLang.InputTanggalSalah = "Attention, date is not valid!"


        val saveOnlineDialogLang = SaveOnlineDialogLang()
        saveOnlineDialogLang.title = "Save Data To Cloud"
        saveOnlineDialogLang.message = "You about to save all your data to server, press save to continue"
        saveOnlineDialogLang.save = "Save"
        saveOnlineDialogLang.cancel = "Cancel"
        saveOnlineDialogLang.SavingMessage = "Saving all your data,please wait...."
        saveOnlineDialogLang.SavingTitle = "Saving"

        val logoutDialogLang = LogoutDialogLang()
        logoutDialogLang.title = "Logout"
        logoutDialogLang.message = "you'll logout, all data and session from app will be erase, are you sure?"
        logoutDialogLang.logout = "Logout"
        logoutDialogLang.cancel = "Cancel"

        val devModeLang = DevModeLang()
        devModeLang.title = "Developer Mode"
        devModeLang.subtitle_app = "App Setting"
        devModeLang.subtitle_cloud = "Cloud Setting"
        devModeLang.editUrl = "Url Server"
        devModeLang.editport = "Port"
        devModeLang.editloadData = "Url Login"
        devModeLang.editsaveData = "Url Simpan"
        devModeLang.editloop = "Override Stock Loop"
        devModeLang.title_dialog = "Edit Value"
        devModeLang.save = "Save"
        devModeLang.cancel = "Cancel"
        devModeLang.editModeCatatan = "Edit Writing Mode"

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
        langObj.registerLang = registerLang
        langObj.monthInString = monthInString
        langObj.userDataSettingLang = userDataSettingLang
        langObj.loginDialogLang = loginDialogLang
        langObj.guideLang = guideLang
        langObj.cariTanggalLaporanLang = cariTanggalLaporanLang
        langObj.saveOnlineDialogLang = saveOnlineDialogLang
        langObj.logoutDialogLang = logoutDialogLang
        langObj.devModeLang = devModeLang
    }


    private fun SetIndo(){

        val mainMenuLang = MainMenuLang()
        mainMenuLang.MenuSetting2 = "Keluar"
        mainMenuLang.MenuSetting1 = "Pengaturan"
        mainMenuLang.MenuSetting3 = "Simpan Online"
        mainMenuLang.MenuSetting4 = "Login"
        mainMenuLang.MenuSetting5 = "Panduan"
        mainMenuLang.subMenu1 = "Kartu Persediaan"
        mainMenuLang.subMenu2 = "Menu Transaksi"
        mainMenuLang.subMenu3 = "Menu Produk"
        mainMenuLang.subMenu4 = "Logout"
        mainMenuLang.SaveDataOnlineButAccountNotValid = "Gagal menyimpan data anda, mohon check kembali pengaturan akun anda, ada beberapa data yang hilang!"
        mainMenuLang.Saving = "Menyimpan.."
        mainMenuLang.SavingComplete = "Berhasil Menyimpan!"
        mainMenuLang.SavingFail = "Gagal Menyimpan!"
        mainMenuLang.SavingButDataIsTooBig = "Menyimpan.., namun data yang dikirim terlalu besar!"

        val mainMenuSettingLang = MainMenuSettingLang()
        mainMenuSettingLang.OpenSetting1 = "Pengaturan Bahasa"
        mainMenuSettingLang.OpenSetting2 = "Pengaturan Tema"
        mainMenuSettingLang.OpenSetting3 = "Profil Pengguna"
        mainMenuSettingLang.Cancel = "Batal"
        mainMenuSettingLang.TitleOpenSetting1 = "Tetapkan Bahasa"
        mainMenuSettingLang.TitleOpenSetting2 = "Pilih Tema"
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
        addProdukFormLang.unitProduct = "Satuan..."
        addProdukFormLang.dataEmpty = "Ada form yang kosong, mohon diisi!"

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
        addTransDialogLang.TypeIn = "Produk Masuk"
        addTransDialogLang.TypeOUT = "Produk Keluar"
        addTransDialogLang.chooseData = "Pilih Tanggal"
        addTransDialogLang.addInformation = "Tambah Keterangan ..."
        addTransDialogLang.inputInformation = "Input Keterangan ..."
        addTransDialogLang.addProduct = "Tambah Produk Ke Detail"
        addTransDialogLang.chooseTime = "Pilih Jam"

        addTransDialogLang.nameProduct = "Nama Produk"
        addTransDialogLang.price = "Harga"
        addTransDialogLang.qty = "Kuantitas"
        addTransDialogLang.StokTersedia = "Stok Tersedia"
        addTransDialogLang.Stok = "Stok"

        addTransDialogLang.add = "Ok"
        addTransDialogLang.Ok = "Ok"
        addTransDialogLang.cancel = "Batal"

        addTransDialogLang.titleEditDetail = "Edit Detail Transaksi"
        addTransDialogLang.save = "Simpan"
        addTransDialogLang.hapus = "Hapus"

        addTransDialogLang.warningProductQtyLow = "Perhatian!,Stok produk Ini"
        addTransDialogLang.CheckAgain = "Ada Data Transaksi yg belum diisi, Cek Lagi!"

        addTransDialogLang.warningThereIsProductQtyLow = "Perhatian, ada Stok produk yang tidak memenuhi kebutuhan!"

        addTransDialogLang.tutup = "Tutup"

        val laporanMenuLang =  LaporanMenuLang()
        laporanMenuLang.Kartupersediaan = "Kartu Persediaan"
        laporanMenuLang.filterPilihMethod = "Metode"
        laporanMenuLang.filterPilihPeriode = "Tanggal"
        laporanMenuLang.filterPilihProduk = "Produk"
        laporanMenuLang.KartuPersediaanKosong = "Kartu Persediaan Kosong"
        laporanMenuLang.namaProductDetail = "Nama Produk"
        laporanMenuLang.price = "Harga"
        laporanMenuLang.qty = "Total Kuantitas"
        laporanMenuLang.total = "Total"
        laporanMenuLang.stokKe = "Nomor Persediaan"
        laporanMenuLang.KuantitasKe = "Nomor Kuantitas"
        laporanMenuLang.titleStockForListStock = "Saldo Persediaan"
        laporanMenuLang.titleTransForListQty = "Saldo Transaksi"
        laporanMenuLang.exportTitle = "Cetak Dokumen"
        laporanMenuLang.toPDF = "PDF (Potret)"
        laporanMenuLang.toPDFLANDSCAPE = "PDF (Mendatar)"
        laporanMenuLang.save = "Simpan"
        laporanMenuLang.cancel = "Batal"
        laporanMenuLang.saved = "File Tersimpan di Folder KartuPersediaan"
        laporanMenuLang.titleLaporan = "Kartu Persediaan Barang"
        laporanMenuLang.dateAwalLap = "Dari"
        laporanMenuLang.dateAkhirLap = ""
        laporanMenuLang.hinga = "-"
        laporanMenuLang.WarningInvalidProduckInTrans = "ada beberapa data transaksi yang kuantitas produknya tidak valid, mohon dicek!"
        laporanMenuLang.WarningInvalidProduckInTransTitle = "Perhatian"
        laporanMenuLang.titleOpenFile = "Dokumen Tersimpan"
        laporanMenuLang.messageOpenFile = "File Tersimpan di Folder KartuPersediaan,apakah anda ingin membukanya?"
        laporanMenuLang.ok = "Buka"
        laporanMenuLang.no = "Tidak"
        laporanMenuLang.FilterChooseAllProduct = "Semua Produk"
        laporanMenuLang.FiterChooseAllPeriod = "Semua Tanggal"
        laporanMenuLang.titlePeriodeTahun = "Pilih Tahun"

        val printLaporanLang = PrintLaporanLang()
        printLaporanLang.Pembelian = "Produk Masuk"
        printLaporanLang.Penjualan = "Produk Keluar"
        printLaporanLang.tgl = "Tanggal"
        printLaporanLang.keterangan = "Keterangan"
        printLaporanLang.namaP = "Nama Produk"
        printLaporanLang.defaultHargaP = "Harga Standar"
        printLaporanLang.unitP = "Satuan"
        printLaporanLang.hargaP = "Harga"
        printLaporanLang.qtyP = "Kuantitas"
        printLaporanLang.total = "Total"
        printLaporanLang.stok = "Kuantitas"
        printLaporanLang.metode = "Metode Pencatatan"
        printLaporanLang.MenyiapkanPrintTitleDialog = "Menyiapkan Data"
        printLaporanLang.MenyiapkanPrintMessageDialog  = "Menyiapkan data-data yang akan di cetak, harap menunggu...."

        printLaporanLang.PrintNowTitleDialog = "Mencetak"
        printLaporanLang.PrintNowMessageDialog ="dokumen-dukumen laporan kartu persediaan sedang dicetak, harap menunggu..."


        val registerLang = RegisterLang()
        registerLang.name = "Nama Pemilik"
        registerLang.email = "Email"
        registerLang.company = "Nama Perusahaan"
        registerLang.register = "Registrasi"
        registerLang.title = "Form Registrasi"
        registerLang.login = "Login"
        registerLang.setting = "Pengaturan"
        registerLang.inputEmpty = "ada form yg kosong, tolong cek kembali!"

        val monthInString = MonthInString()
        monthInString.Januari = "Januari"
        monthInString.Februari = "Februari"
        monthInString.Maret = "Maret"
        monthInString.April = "April"
        monthInString.Mei = "Mei"
        monthInString.Juni = "Juni"
        monthInString.Juli = "Juli"
        monthInString.Agustus = "Agustus"
        monthInString.September = "September"
        monthInString.Oktober = "Oktober "
        monthInString.November = "November"
        monthInString.Desember = "Desember"


        val userDataSettingLang = UserDataSettingLang()
        userDataSettingLang.Title = "Edit Profil Pengguna"
        userDataSettingLang.EditNamaPemilik = "Edit Nama Pemilik"
        userDataSettingLang.EditNamaPerusahaan = "Edit Nama Perusahaan"
        userDataSettingLang.EditEmail = "Edit Email"
        userDataSettingLang.EditUsername = "Edit Username"
        userDataSettingLang.EditPassword = "Password Lama"
        userDataSettingLang.KonfirmEditPassword = "Password Baru"
        userDataSettingLang.Simpan = "Simpan"
        userDataSettingLang.Batal = "Batal"
        userDataSettingLang.DataKosong = "Ada Data pengguna Yang Perluh diisi!"
        userDataSettingLang.GagalValidasiPassword = "Gagal validasi password anda!"
        userDataSettingLang.KonfirmasiDeanganMengisisPassword = "Konfirmasi Dengan Mengisi Password!"

        val loginDialogLang = LoginDialogLang()
        loginDialogLang.title = "Form Login"
        loginDialogLang.username = "Username atau Email"
        loginDialogLang.password = "Input Password"
        loginDialogLang.login = "Login"
        loginDialogLang.cancel = "Batal"
        loginDialogLang.failLogin = "Gagal login ke akun anda!"
        loginDialogLang.emptyForm = "ada form yang kosong, harap isi!"
        loginDialogLang.LoginDialogTitle = "Login"
        loginDialogLang.LoginDialogMessage = "Login,harap menunggu..."

        val guideLang = GuideLang()
        guideLang.appName = "Kartu Persediaan"
        guideLang.appVer = "Versi 1.6"
        guideLang.title = "Panduan"
        guideLang.titleForSocialMediaPages = "Kunjungi Halaman Sosial Media Saya Di : "
        guideLang.guides = "Kartu Persediaan adalah Kartu yang digunakan untuk mencatat Persediaan barang, Kartu Persediaan bisa digunakan untuk mencatat Persediaan Barang Dagangan dalam perusahaan dagang, mencatat Persediaan Bahan Baku dan Bahan Pembantu untuk perusahaan manufaktur.\n" +
                "\n" +
                "Aplikasi kartu persediaan ini memberikan kemudahan bagi pengguna untuk dapat menyusun laporan kartu persediaan, hanya dengan menginputkan data produk dan transaksi, secara otomatis aplikasi akan menyusun semua laporan kartu persediaan dengan 3 metode pencatatan sekaligus, sehingga pengguna dapat dengan mudah melihat perbandingan dari masing - masing laporan. \n" +
                "\n" +
                "berikut adalah panduan penggunaan untuk aplikasi ini : \n" +
                "\n" +
                "1). setelah melakukan register, anda akan berada pada menu utama, menu utama tersebut terdiri dari 3 menu, menu produk, transaksi, dan kartu persediaan.\n" +
                "\n" +
                "2). tambahkan beberapa data produk dengan menekan tombol di sudut kanan bawah, dan pilih menu tambah produk,masukan nama, harga standar dan satuan.\n" +
                "\n" +
                "3). setelah selesai, tambahkan beberapa data transaksi. dengan menekan tombol yang sama, dan pilih menu tambah transaksi, masukan waktu transaksi, tanggal, keterangan, tipe transaksi IN untuk tipe transaksi produk masuk dan OUT untuk tipe transaksi produk keluar, serta menambahkan beberapa produk kedalam transaksi dan menentukan kuantitas dah harga masing-masing produk.\n" +
                "\n" +
                "4). setelah selesai, pilih menu kartu persediaan, dan secara otomatis laporan akan ditampilkan\n" +
                "\n" +
                ""

        val cariTanggalLaporanLang = CariTanggalLaporanLang()
        cariTanggalLaporanLang.title = "Tanggal Transaksi"
        cariTanggalLaporanLang.Tampilkan = "Tampilkan"
        cariTanggalLaporanLang.Batal = "Batal"
        cariTanggalLaporanLang.awal = "Tanggal Awal"
        cariTanggalLaporanLang.akhir = "Tanggal Akhir"
        cariTanggalLaporanLang.InputTanggalSalah = "perhatian,Tanggal Yang diinputkan tidak valid!"

        val saveOnlineDialogLang = SaveOnlineDialogLang()
        saveOnlineDialogLang.title = "Simpan data ke Cloud"
        saveOnlineDialogLang.message = "Anda akan menyimpan seluruh data anda ke server, tekan simpan untuk melanjukan"
        saveOnlineDialogLang.save = "Simpan"
        saveOnlineDialogLang.cancel = "Batal"
        saveOnlineDialogLang.SavingTitle = "Menyimpan"
        saveOnlineDialogLang.SavingMessage = "Menyimpan semua data anda, harap menunggu...."

        val logoutDialogLang = LogoutDialogLang()
        logoutDialogLang.title = "Logout"
        logoutDialogLang.message = "Anda akan logout, seluruh data dan sesi untuk aplikasi akan dihapus dari perangkat anda,apakah anda yakin ingin melanjutkan?"
        logoutDialogLang.logout = "Logout"
        logoutDialogLang.cancel = "Batal"

        val devModeLang = DevModeLang()
        devModeLang.title = "Mode Developer"
        devModeLang.subtitle_app = "Pengaturan Aplikasi"
        devModeLang.subtitle_cloud = "Pengaturan Cloud"
        devModeLang.editUrl = "Url Server"
        devModeLang.editport = "Port"
        devModeLang.editloadData = "Url Login"
        devModeLang.editsaveData = "Url Simpan"
        devModeLang.editloop = "Override looping Persediaan"
        devModeLang.title_dialog = "Edit Nilai"
        devModeLang.save = "Simpan"
        devModeLang.cancel = "Batal"
        devModeLang.editModeCatatan = "Edit Mode Catatan"

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
        langObj.registerLang = registerLang
        langObj.monthInString = monthInString
        langObj.userDataSettingLang = userDataSettingLang
        langObj.loginDialogLang = loginDialogLang
        langObj.guideLang = guideLang
        langObj.cariTanggalLaporanLang = cariTanggalLaporanLang
        langObj.saveOnlineDialogLang = saveOnlineDialogLang
        langObj.logoutDialogLang = logoutDialogLang
        langObj.devModeLang = devModeLang
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
