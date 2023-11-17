package com.example.qlhocsinh;

import java.util.Date;

public class HocSinh {
    private int _maHS;
    private int _maLop;
    private String _tenHS;
    private int _gioiTinh;
    private String _ngaySinh;
    private String _diaChi;
    private String _tonGiao;
    private int _trangThai;

    public int get_trangThai() {
        return _trangThai;
    }

    public void set_trangThai(int _trangThai) {
        this._trangThai = _trangThai;
    }

    public int get_maHS() {
        return _maHS;
    }

    public void set_maHS(int _maHS) {
        this._maHS = _maHS;
    }

    public int get_maLop() {
        return _maLop;
    }

    public void set_maLop(int _maLop) {
        this._maLop = _maLop;
    }

    public String get_tenHS() {
        return _tenHS;
    }

    public void set_tenHS(String _tenHS) {
        this._tenHS = _tenHS;
    }

    public int get_gioiTinh() {
        return _gioiTinh;
    }

    public void set_gioiTinh(int _gioiTinh) {
        this._gioiTinh = _gioiTinh;
    }

    public String get_ngaySinh() {
        return _ngaySinh;
    }

    public void set_ngaySinh(String _ngaySinh) {
        this._ngaySinh = _ngaySinh;
    }

    public String get_diaChi() {
        return _diaChi;
    }

    public void set_diaChi(String _diaChi) {
        this._diaChi = _diaChi;
    }

    public String get_tonGiao() {
        return _tonGiao;
    }

    public void set_tonGiao(String _tonGiao) {
        this._tonGiao = _tonGiao;
    }

    public HocSinh(){}

    public HocSinh(int _maHS, int _maLop, String _tenHS, int _gioiTinh, String _ngaySinh, String _diaChi, String _tonGiao){
        this._maHS = _maHS;
        this._maLop = _maLop;
        this._tenHS = _tenHS;
        this._gioiTinh = _gioiTinh;
        this._ngaySinh = _ngaySinh;
        this._diaChi = _diaChi;
        this._tonGiao = _tonGiao;
    }

    public HocSinh(int _maLop, String _tenHS, int _gioiTinh, String _ngaySinh, String _diaChi, String _tonGiao){
        this._maLop = _maLop;
        this._tenHS = _tenHS;
        this._gioiTinh = _gioiTinh;
        this._ngaySinh = _ngaySinh;
        this._diaChi = _diaChi;
        this._tonGiao = _tonGiao;
    }
}
