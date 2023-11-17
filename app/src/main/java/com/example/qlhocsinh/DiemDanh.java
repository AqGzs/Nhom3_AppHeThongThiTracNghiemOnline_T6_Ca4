package com.example.qlhocsinh;

import java.util.Date;

public class DiemDanh {
    private int _maDD;
    private int _maLop;
    private int _maHS;
    private String _ngayDD;
    private int _trangThai; // 1 vắng, 0: có mặt

    public int get_maDD() {
        return _maDD;
    }

    public void set_maDD(int _maDD) {
        this._maDD = _maDD;
    }

    public String get_ngayDD() {
        return _ngayDD;
    }

    public void set_ngayDD(String _ngayDD) {
        this._ngayDD = _ngayDD;
    }

    public int get_maHS() {
        return _maHS;
    }

    public void set_maHS(int _maHS) {
        this._maHS = _maHS;
    }

    public int get_trangThai() {
        return _trangThai;
    }

    public void set_trangThai(int _trangThai) {
        this._trangThai = _trangThai;
    }

    public int get_maLop() {
        return _maLop;
    }

    public void set_maLop(int _maLop) {
        this._maLop = _maLop;
    }
}
