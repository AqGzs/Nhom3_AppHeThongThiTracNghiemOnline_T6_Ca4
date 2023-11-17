package com.example.qlhocsinh;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String tenDB = "QuanLyHocSinh1";
    public static final String tenBang = "NguoiDung";
    public static final String maNguoiDung = "MaNguoiDung";
    public static final String tenNguoiDung = "TenNguoiDung";
    public static final String emailNguoiDung = "Email";
    public static final String matKhauNguoiDung = "MatKhau";
    public static final String gioiTinhNguoiDung = "GioiTinh";
    public static final String ngaySinhNguoiDung = "NgaySinh";
    public static final String sdtNguoiDung = "SDT";
    public static final String diaChiNguoiDung = "DiaChi";

    public static final String tenBangLop = "Lop";
    public static final String maLop = "MaLop";
    public static final String lop = "Lop";
    public static final String tenLop = "TenLop";
    public static final String moTaLop = "Mota";

    public static final String tenBangHocSinh = "HocSinh";
    public static final String maHS = "MaHS";
    public static final String tenHS = "tenHS";
    public static final String gioiTinhHS = "GioiTinhHS";
    public static final String ngaySinhHS = "NgaySinh";
    public static final String diaChiHS = "DiaChiHS";
    public static final String tonGiao = "TonGiao";

    public static final String tenBangDiemDanh = "DiemDanh";
    public static final String maDiemDanh = "MaDiemDanh";
    public static final String ngayVang = "NgayVang";
    public static final String trangThai = "TrangThai";

    public static final String tenBangMonHoc = "MonHoc";
    public static final String maMonHoc = "MaMH";
    public static final String tenMonHoc = "TenMH";
    public static final String anhMonhoc = "AnhMH";



    public DBHelper(Context context) {
        super(context, tenDB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("create table NguoiDung(" +
                "MaNguoiDung integer primary key autoincrement," +
                "TenNguoiDung text," +
                "Email text not null," +
                "MatKhau text not null," +
                "GioiTinh text," +
                "NgaySinh text," +
                "SDT text," +
                "DiaChi text)");

        database.execSQL("create table Lop(" +
                "MaLop integer primary key autoincrement," +
                "MaNguoiDung integer not null," +
                "Lop integer not null," +
                "TenLop text not null," +
                "MoTa text not null," +
                "FOREIGN KEY(MaNguoiDung) REFERENCES NguoiDung(MaNguoiDung))");

        database.execSQL("create table HocSinh(" +
                "MaHS integer primary key autoincrement," +
                "MaLop integer not null," +
                "TenHS text not null," +
                "GioiTinhHS integer not null," +
                "NgaySinh text not null," +
                "DiaChiHS text not null," +
                "TonGiao text," +
                "FOREIGN KEY(MaLop) REFERENCES Lop(MaLop))");

        database.execSQL("create table DiemDanh(" +
                "MaDiemDanh integer primary key autoincrement," +
                "MaLop integer not null," +
                "MaHS integer not null," +
                "NgayVang text not null," +
                "TrangThai integer not null," +
                "FOREIGN KEY(MaLop) REFERENCES Lop(MaLop))");

        database.execSQL("create table MonHoc(" +
                "MaMH integer primary key autoincrement," +
                "MaLop integer not null," +
                "TenMH text not null," +
                "AnhMH integer not null)");

        database.execSQL("create table KetQua(" +
                "MaKQ integer primary key autoincrement," +
                "MaHS integer not null," +
                "MaLop integer not null," +
                "MaMH integer not null," +
                "DiemKT integer not null," +
                "DiemThiGiuaKy integer not null," +
                "DiemThiCuoiKy integer not null," +
                "DiemTB integer not null," +
                "SoNgayVang integer not null," +
                "XepLoai text not null," +
                "Hanhkiem text not null," +
                "DanhGia text," +
                "FOREIGN KEY(MaHS) REFERENCES HocSinh(MaHS)," +
                "FOREIGN KEY(MaLop) REFERENCES Lop(MaLop)," +
                "FOREIGN KEY(MaMH) REFERENCES MonHoc(MaMH))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("drop table if exists NguoiDung");
        database.execSQL("drop table if exists Lop");
        database.execSQL("drop table if exists HocSinh");
        database.execSQL("drop table if exists DiemDanh");
        database.execSQL("drop table if exists MonHoc");
        database.execSQL("drop table if exists KetQua");
    }
}
