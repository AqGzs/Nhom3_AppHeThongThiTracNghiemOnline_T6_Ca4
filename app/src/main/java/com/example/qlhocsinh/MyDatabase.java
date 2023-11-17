package com.example.qlhocsinh;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class MyDatabase {
    SQLiteDatabase database;
    DBHelper helper;

    public MyDatabase(Context context){
        helper = new DBHelper(context);
        database = helper.getWritableDatabase();
    }

    // Database User
    public User checkEmailPassword(String email, String password){
        User user = new User();
        Cursor cursor = database.rawQuery("select * from NguoiDung where Email=? and MatKhau=?", new String[]{email, password});
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            user.set_id(cursor.getInt(0));
            user.set_ten(cursor.getString(1));
            user.set_email(cursor.getString(2));
            user.set_matkhau(cursor.getString(3));
            user.set_gioitinh(cursor.getString(4));
            user.set_ngaysinh(cursor.getString(5));
            user.set_sdt(cursor.getString(6));
            user.set_diachi(cursor.getString(7));
        }
        else {
            return null;
        }
        return user;
    }

    public Boolean insertUser(User user){
        ContentValues values = new ContentValues();

        values.put(DBHelper.emailNguoiDung, user.get_email());
        values.put(DBHelper.matKhauNguoiDung, user.get_matkhau());

        long result = database.insert(DBHelper.tenBang, null, values);

        if(result == -1){
            return false;
        }
        return true;
    }

    public Boolean updateUser(User user){
        ContentValues values = new ContentValues();
        values.put(DBHelper.tenNguoiDung, user.get_ten());
        values.put(DBHelper.ngaySinhNguoiDung, user.get_ngaysinh());
        values.put(DBHelper.gioiTinhNguoiDung, user.get_gioitinh());
        values.put(DBHelper.sdtNguoiDung, user.get_sdt());
        values.put(DBHelper.diaChiNguoiDung, user.get_diachi());

        long result = database.update(DBHelper.tenBang, values, DBHelper.maNguoiDung + "=" +user.get_id(), null);
        if(result == -1){
            return false;
        }
        return true;
    }

    public Boolean checkEmail(String email){
        Cursor cursor = database.rawQuery("select * from NguoiDung where Email=?", new String[]{email});
        if(cursor.getCount() > 0)
            return true;
        return false;
    }

    //Database Class
    public Boolean insertClass(Class myClass){
        ContentValues values = new ContentValues();

        values.put(DBHelper.maNguoiDung, myClass.get_user_id());
        values.put(DBHelper.lop, myClass.getLop());
        values.put(DBHelper.tenLop, myClass.get_tenLop());
        values.put(DBHelper.moTaLop, myClass.get_mota());

        long result = database.insert(DBHelper.tenBangLop, null, values);
        if(result == -1){
            return false;
        }
        return true;
    }

    public Boolean removeClass(Class myClass){
        String idRemove = Integer.toString(myClass.get_id());
        long isRemove = database.delete(DBHelper.tenBangLop, DBHelper.maLop + "=" + idRemove, null);

        if(isRemove == -1){
            return false;
        }
        return true;
    }

    public Boolean updateClass(Class myClass){
        ContentValues values = new ContentValues();
        values.put(DBHelper.tenLop, myClass.get_tenLop());
        values.put(DBHelper.lop, myClass.getLop());
        values.put(DBHelper.moTaLop, myClass.get_mota());

        long result = database.update(DBHelper.tenBangLop, values, DBHelper.maLop + "=" +myClass.get_id(), null);
        if(result == -1){
            return false;
        }
        return true;
    }

    public Cursor getCursorsClass(int userId){
        Cursor cursor = database.rawQuery("Select * from Lop where MaNguoiDung=" + userId, null);
        return cursor;
    }

    //Database HocSinh
    public Boolean themHocSinh(HocSinh hocSinh){
        ContentValues values = new ContentValues();

        values.put(DBHelper.maLop, hocSinh.get_maLop());
        values.put(DBHelper.tenHS, hocSinh.get_tenHS());
        values.put(DBHelper.gioiTinhHS, hocSinh.get_gioiTinh());
        values.put(DBHelper.ngaySinhHS, hocSinh.get_ngaySinh());
        values.put(DBHelper.diaChiHS, hocSinh.get_diaChi());
        values.put(DBHelper.tonGiao, hocSinh.get_tonGiao());

        long result = database.insert(DBHelper.tenBangHocSinh, null, values);
        if(result == -1){
            return false;
        }
        return true;
    }

    public Boolean xoaHocSinh(HocSinh hocSinh){
        String idRemove = Integer.toString(hocSinh.get_maHS());
        long isRemove = database.delete(DBHelper.tenBangHocSinh, DBHelper.maHS + "=" + idRemove, null);

        if(isRemove == -1){
            return false;
        }
        return true;
    }

    public Boolean suaHocSinh(HocSinh hocSinh){
        ContentValues values = new ContentValues();
        values.put(DBHelper.maLop, hocSinh.get_maLop());
        values.put(DBHelper.tenHS, hocSinh.get_tenHS());
        values.put(DBHelper.gioiTinhHS, hocSinh.get_gioiTinh());
        values.put(DBHelper.ngaySinhHS, hocSinh.get_ngaySinh());
        values.put(DBHelper.diaChiHS, hocSinh.get_diaChi());
        values.put(DBHelper.tonGiao, hocSinh.get_tonGiao());

        long result = database.update(DBHelper.tenBangHocSinh, values, DBHelper.maHS + "=" + hocSinh.get_maHS(), null);
        if(result == -1){
            return false;
        }
        return true;
    }

    public Cursor getCursorsHS(int classID){
        Cursor cursor = database.rawQuery("Select * from HocSinh where MaLop=" + classID, null);
        return cursor;
    }

    public Cursor getHSById(int id){
        Cursor cursor = database.rawQuery("Select * from HocSinh where MaHS=" + id, null);
        return cursor;
    }

    //Database DiemDanh
    public Boolean createDiemDanh(DiemDanh diemDanh){
        ContentValues values = new ContentValues();
        values.put(DBHelper.maLop, diemDanh.get_maLop());
        values.put(DBHelper.maHS, diemDanh.get_maHS());
        values.put(DBHelper.ngayVang, diemDanh.get_ngayDD());
        values.put(DBHelper.trangThai, diemDanh.get_trangThai());
        long cursor = database.insert(DBHelper.tenBangDiemDanh, null, values);

        if(cursor == -1)
            return false;
        return true;
    }

    public Cursor getCursorDay(int classId){
        Cursor cursor = database.rawQuery("Select * from DiemDanh where MaLop=" + classId + " group by NgayVang", null);
        return cursor;
    }

    public Cursor getCursorDiemDanhByDay(int classId){
        Cursor cursor = database.rawQuery("Select * from DiemDanh where MaLop=" + classId, null);
        return cursor;
    }

    public Boolean updateDiemDanh(DiemDanh diemDanh){
        ContentValues values = new ContentValues();
        values.put(DBHelper.maLop, diemDanh.get_maLop());
        values.put(DBHelper.maHS, diemDanh.get_maHS());
        values.put(DBHelper.ngayVang, diemDanh.get_ngayDD());
        values.put(DBHelper.trangThai, diemDanh.get_trangThai());

        long result = database.update(DBHelper.tenBangDiemDanh, values, DBHelper.maDiemDanh + "=" + diemDanh.get_maDD(), null);

        if(result == -1)
            return false;
        return true;
    }

    public Boolean deleteDiemDanh(DiemDanh diemDanh){
        long isRemove = database.delete(DBHelper.tenBangDiemDanh, DBHelper.maDiemDanh + "=" + diemDanh.get_maDD(), null);

        if(isRemove == -1){
            return false;
        }
        return true;
    }

    public Boolean checkDay(String day){
        Cursor cursor = database.rawQuery("select * from DiemDanh where NgayVang=?", new String[]{day});
        if(cursor.getCount() > 0)
            return true;
        return false;
    }

    //Database MonHoc
    public Cursor getCursorMonHoc(int classId){
        Cursor cursor = database.rawQuery("Select * from MonHoc where MaLop=" + classId, null);
        return cursor;
    }

    public Boolean insertMonHoc(MonHoc monHoc){
        ContentValues values = new ContentValues();

        values.put(DBHelper.maLop, monHoc.getMaLop());
        values.put(DBHelper.tenMonHoc, monHoc.getTenMH());
        values.put(DBHelper.anhMonhoc, monHoc.getHinhAnh());

        long result = database.insert(DBHelper.tenBangMonHoc, null, values);
        if(result == -1){
            return false;
        }

        return true;
    }

    public Boolean deleteMonHoc(MonHoc monHoc){
        long isRemove = database.delete(DBHelper.tenBangMonHoc, DBHelper.maMonHoc + "=" + monHoc.getMaMH(), null);

        if(isRemove == -1){
            return false;
        }
        return true;
    }
}