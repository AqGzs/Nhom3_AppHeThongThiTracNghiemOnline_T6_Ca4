package com.example.qlhocsinh;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DiemDanhListActivity extends AppCompatActivity {
    TextView tvTitle;
    ImageButton btnBack, btnDeleteDiemDanh;
    Button btnEditDiemDanh;
    Spinner spnChooseClass, spnChooseDay;
    RecyclerView rcvHocSinh;
    RCVHocSinhAdapter rcvHocSinhAdapter;
    ArrayList<Class> classes;
    ArrayList<HocSinh> hocSinhs = new ArrayList<HocSinh>();
    ArrayList<DiemDanh> diemDanhs = new ArrayList<DiemDanh>();
    ArrayAdapter classAdapter;
    String[] arrDay;
    MyDatabase database;
    int classId;
    String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diem_danh_list);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnDeleteDiemDanh = (ImageButton) findViewById(R.id.btnDeleteDiemDanh);
        btnEditDiemDanh = (Button) findViewById(R.id.btnEditDiemDanh);
        spnChooseClass = (Spinner) findViewById(R.id.spnChooseClass);
        spnChooseDay = (Spinner) findViewById(R.id.spnChooseDay);
        rcvHocSinh = (RecyclerView) findViewById(R.id.rcvHocSinh);
        database = new MyDatabase(this);

        updateAppbar();
        showSpinnerClass();
        setBtnDeleteDiemDanh();
        setBtnEditDiemDanh();
    }

    public void updateAppbar(){
        tvTitle.setText("Bảng điểm danh");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void showSpinnerClass(){
        classes = DiemDanhActivity.classes;
        classAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, DiemDanhActivity.getClassesName(classes));
        spnChooseClass.setAdapter(classAdapter);

        spnChooseClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                classId = classes.get(i).get_id();

                getDayDiemDanh(classId);
                showSpinnerDiemDanh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void showSpinnerDiemDanh(){
        ArrayAdapter dayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrDay);
        spnChooseDay.setAdapter(dayAdapter);
        spnChooseDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                day = arrDay[i];
                showHocSinh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void showHocSinh(){
        getDataDiemDanh();
        getDataHocSinh();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvHocSinh.setLayoutManager(layoutManager);
        rcvHocSinhAdapter = new RCVHocSinhAdapter(this, R.layout.layout_diemdanh_list, hocSinhs);
        rcvHocSinh.setAdapter(rcvHocSinhAdapter);
    }

    public void getDataHocSinh(){
        if(hocSinhs != null)
            hocSinhs.removeAll(hocSinhs);

        for(int i = 0; i < diemDanhs.size(); i++){
            Cursor cursor = database.getHSById(diemDanhs.get(i).get_maHS());
            cursor.moveToNext();

            HocSinh hocSinh = new HocSinh();
            hocSinh.set_maHS(cursor.getInt(0));
            hocSinh.set_maLop(cursor.getInt(1));
            hocSinh.set_tenHS(cursor.getString(2));
            hocSinh.set_gioiTinh(cursor.getInt(3));
            hocSinh.set_ngaySinh(cursor.getString(4));
            hocSinh.set_diaChi(cursor.getString(5));
            hocSinh.set_tonGiao(cursor.getString(6));
            hocSinh.set_trangThai(diemDanhs.get(i).get_trangThai());
            hocSinhs.add(hocSinh);
        }
    }

    public void getDataDiemDanh(){
        if(diemDanhs != null)
            diemDanhs.removeAll(diemDanhs);
        Cursor cursor = database.getCursorDiemDanhByDay(classId);
        while(cursor.moveToNext()){
            if(cursor.getString(3).equals(day)){
                DiemDanh diemDanh = new DiemDanh();
                diemDanh.set_maDD(cursor.getInt(0));
                diemDanh.set_maLop(cursor.getInt(1));
                diemDanh.set_maHS(cursor.getInt(2));
                diemDanh.set_ngayDD(cursor.getString(3));
                diemDanh.set_trangThai(cursor.getInt(4));
                diemDanhs.add(diemDanh);
            }
        }
    }

    public void getDayDiemDanh(int classId){
        Cursor cursor = database.getCursorDay(classId);
        arrDay = new String[cursor.getCount()];
        for(int i = 0; cursor.moveToNext(); i++){
            arrDay[i] = cursor.getString(3);
        }
    }

    public void setBtnDeleteDiemDanh(){
        btnDeleteDiemDanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int size = diemDanhs.size();
                int count = 0;

                if(size > 0) {
                    for(int i = 0; i < size; i++){
                        Boolean check = database.deleteDiemDanh(diemDanhs.get(i));

                        if(check == true){
                            count++;
                        }
                    }

                    if(count == size)
                    {
                        finish();
                        startActivity(new Intent(getApplicationContext(), DiemDanhListActivity.class));
                        Toast.makeText(DiemDanhListActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(DiemDanhListActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DiemDanhListActivity.this, "Không có bảng điểm danh nào", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setBtnEditDiemDanh(){
        btnEditDiemDanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int size = diemDanhs.size();
                int count = 0;

                if(size > 0){
                    for(int i = 0; i < size; i++){
                        int trangThai = hocSinhs.get(i).get_trangThai();
                        diemDanhs.get(i).set_trangThai(trangThai);
                        Boolean check = database.updateDiemDanh(diemDanhs.get(i));

                        if(check == true){
                            count++;
                        }
                    }

                    if(count == size)
                    {
                        finish();
                        startActivity(new Intent(getApplicationContext(), DiemDanhListActivity.class));
                        Toast.makeText(DiemDanhListActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DiemDanhListActivity.this, "Sửa file thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DiemDanhListActivity.this, "Không có học sinh để sửa", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}