package com.example.qlhocsinh;

public class Class {
    private int _id;
    private int _user_id;
    private int _lop;
    private String _tenLop;
    private String _mota;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_user_id() {
        return _user_id;
    }

    public void set_user_id(int _user_id) {
        this._user_id = _user_id;
    }

    public String get_tenLop() {
        return _tenLop;
    }

    public void set_tenLop(String _ten) {
        this._tenLop = _ten;
    }

    public int getLop() {
        return _lop;
    }

    public void setLop(int lop) {
        this._lop = lop;
    }

    public String get_mota() {
        return _mota;
    }

    public void set_mota(String _mota) {
        this._mota = _mota;
    }

    public Class(){

    }
    public Class(int _id, int _user_id, int _lop, String _ten, String _mota){
        this._id = _id;
        this._user_id = _user_id;
        this._lop = _lop;
        this._tenLop = _ten;
        this._mota = _mota;
    }
}
