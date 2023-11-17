package com.example.qlhocsinh;

import java.util.ArrayList;
import java.util.List;

public class DataMonHoc {
    public static ArrayList<MonHoc> getMonHocs(){
        ArrayList<MonHoc> monHocs = new ArrayList<>();

        MonHoc tiengViet = new MonHoc();
        tiengViet.setTenMH("Tiếng việt");
        tiengViet.setHinhAnh(R.drawable.tiengviet);
        monHocs.add(tiengViet);

        MonHoc toan = new MonHoc();
        toan.setTenMH("Toán");
        toan.setHinhAnh(R.drawable.toan);
        monHocs.add(toan);

        MonHoc ngoaiNgu = new MonHoc();
        ngoaiNgu.setTenMH("Ngoại ngữ");
        ngoaiNgu.setHinhAnh(R.drawable.ngoaingu);
        monHocs.add(ngoaiNgu);

        MonHoc daoDuc = new MonHoc();
        daoDuc.setTenMH("Đạo đức");
        daoDuc.setHinhAnh(R.drawable.daoduc);
        monHocs.add(daoDuc);

        MonHoc lichSu = new MonHoc();
        lichSu.setTenMH("Lịch sử");
        lichSu.setHinhAnh(R.drawable.lichsu);
        monHocs.add(lichSu);

        MonHoc diaLy = new MonHoc();
        diaLy.setTenMH("Địa lý");
        diaLy.setHinhAnh(R.drawable.dialy);
        monHocs.add(diaLy);

        MonHoc tinHoc = new MonHoc();
        tinHoc.setTenMH("Tin học");
        tinHoc.setHinhAnh(R.drawable.tinhoc);
        monHocs.add(tinHoc);

        MonHoc defaultMH = new MonHoc();
        defaultMH.setTenMH("Khác");
        defaultMH.setHinhAnh(R.drawable.monhoc_default);
        monHocs.add(defaultMH);

        return monHocs;
    }
}
