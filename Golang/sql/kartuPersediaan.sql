create database kartuPersediaan;

use kartuPersediaan;

create table User(
	IdUser char(28),
	NamaPemilik varchar(25),
	NamaPerusahaan varchar(25),
	Email varchar(25),
	Username varchar(25),
	Password varchar(25)
);

alter table User add primary key(IdUser);

create table Produk(
	IdUser char(28),
	IdProduk char(28),
	NamaProduk varchar(35),
	harga int(11),
	satuan varchar(25)
);

alter table Produk add primary key(IdProduk);

alter table Produk add foreign key(IdUser) references User(IdUser);

create table Transaksi(
	IdUser char(28),
	IdTransaksi char(28),
	TanggalTransaksi date,
	WaktuTransaksi time,
	keterangan varchar(50),
	ProductFlow varchar(5),
	SubTotal int(11)
);


alter table Transaksi add primary key(IdTransaksi);

alter table Transaksi add foreign key(IdUser) references User(IdUser);

create table DetailTransaksi(
	IdTransaksi char(28),
	IdProduk char(28),
	IdDetailTransaksi char(28)
);

alter table DetailTransaksi add primary key(IdDetailTransaksi);
alter table DetailTransaksi add foreign key(IdTransaksi) references Transaksi(IdTransaksi);
alter table DetailTransaksi add foreign key(IdProduk) references Produk(IdProduk);

create table DaftarKuantitas(
	IdDetailTransaksi char(28),
	Kuantitas int(11),
	Harga int(11),
	Total int(11)
);

alter table DaftarKuantitas add foreign key(IdDetailTransaksi) references DetailTransaksi(IdDetailTransaksi);


desc User;
desc Produk;
desc Transaksi;
desc DetailTransaksi;
desc DaftarKuantitas;

