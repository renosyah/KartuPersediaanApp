package main

import (
	"strings"
	"strconv"
)

func SetTanggalFormat(st string) *FormatTanggal {
	var d *FormatTanggal
	s := strings.Split(st, "-")

	hari,_ := strconv.Atoi(s[2])
	Bulan,_ := strconv.Atoi(s[1])
	Tahun,_ := strconv.Atoi(s[0])

	d = &FormatTanggal{
		Hari:hari,
		Bulan:Bulan,
		Tahun:Tahun,
	}
	return d
}

func SetTimeFormat(st string) *FormatWaktu {
	var d *FormatWaktu
	s := strings.Split(st, ":")

	jam,_ := strconv.Atoi(s[0])
	menit,_ := strconv.Atoi(s[2])

	d = &FormatWaktu{
		Jam:jam,
		Menit:menit,
	}
	return d
}


func (date *FormatTanggal) ToTanggalString() string {
	tanggal := strconv.Itoa(date.Hari)
	bulan := strconv.Itoa(date.Bulan)
	tahun := strconv.Itoa(date.Tahun)

	return tahun +"-"+bulan+"-"+tanggal
}

func (date *FormatWaktu) ToWaktuString() string {
	jam := strconv.Itoa(date.Jam)
	menit := strconv.Itoa(date.Menit)

	return jam +":"+menit+":00"
}
