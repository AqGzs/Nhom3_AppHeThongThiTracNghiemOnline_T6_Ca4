package com.example.qlhocsinh;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DiemDanhActivity extends AppCompatActivity{

    ImageButton btnBack, btnCalendar;
    TextView tvTitle, tvDate, tvTotalHS;
    Spinner spnChooseClass;
    RecyclerView rcvHocSinh;
    Button btnSave, btnDiemDanhList;
    static ArrayList<HocSinh> arrStudent;
    static ArrayList<Class> classes;
    MyDatabase database;
    Bundle bundle;
    Calendar calendar;
    LinearLayoutManager layoutManager;
    RCVHocSinhAdapter rcvHocSinhAdapter;
    int classID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diem_danh);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnCalendar = (ImageButton) findViewById(R.id.btnCalendar);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTotalHS = (TextView) findViewById(R.id.tvTotalHS);
        spnChooseClass = (Spinner) findViewById(R.id.spnChooseClass);
        rcvHocSinh = (RecyclerView) findViewById(R.id.rcvHocSinh);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDiemDanhList = (Button) findViewById(R.id.btnDiemDanhList);

        Intent intent = getIntent();
        bundle = intent.getExtras();
        database = new MyDatabase(this);
        arrStudent = new ArrayList<HocSinh>();

        updateAppbar();
        setNameTeacher();
        Calendar();
        showClass();
        setBtnSave();
        setBtnDiemDanhList();
    }

    public void updateAppbar(){
        tvTitle.setText("Điểm danh");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void setNameTeacher(){
        TextView tvNameTeacher = (TextView) findViewById(R.id.tvNameTeacher);
        tvNameTeacher.setText(bundle.getStringArrayList("dataUser").get(1));
    }

    public void showClass(){
        classes = new ArrayList<Class>();
        getDataClass();
        String[] classesName = getClassesName(classes);
        ArrayAdapter classAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, classesName);
        spnChooseClass.setAdapter(classAdapter);
        selectClassItem();
    }

    private void selectClassItem(){
        spnChooseClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                classID = classes.get(i).get_id();
                getDataStudent(classID);
                showHocSinhs();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(DiemDanhActivity.this, "Không có lớp học", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataStudent(int classId){
        if(arrStudent != null){
            arrStudent.removeAll(arrStudent);
        }
        Cursor cursor = database.getCursorsHS(classId);

        while(cursor.moveToNext()){
            int maHS = cursor.getInt(0);
            int maLopHS = cursor.getInt(1);
            String tenHS = cursor.getString(2);
            int gioiTinhHS = cursor.getInt(3);
            String ngaySinhHS = cursor.getString(4);
            String diaChiHS = cursor.getString(5);
            String tonGiao = cursor.getString(6);

            HocSinh hocSinh = new HocSinh(maHS, maLopHS, tenHS, gioiTinhHS, ngaySinhHS, diaChiHS, tonGiao);
            arrStudent.add(hocSinh);
        }
        tvTotalHS.setText(Integer.toString(cursor.getCount()));
    }

    private void getDataClass(){
        Cursor cursor = database.getCursorsClass(Integer.parseInt(bundle.getStringArrayList("dataUser").get(0)));

        while(cursor.moveToNext()){
            Class newClass = new Class();
            newClass.set_id(cursor.getInt(0));
            newClass.set_tenLop(cursor.getString(3));
            classes.add(newClass);
        }
    }

    public static String[] getClassesName(ArrayList<Class> classes){
        String[] classesName = new String[classes.size()];
        for(int i  = 0; i < classes.size(); i++){
            classesName[i] = classes.get(i).get_tenLop();
        }
        return classesName;
    }

    private void setBtnSave(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int size = arrStudent.size();
                int count = 0;
                String dateDD = tvDate.getText().toString();

                if(!database.checkDay(dateDD)){
                    for(int i = 0; i < size; i++) {
                        int maHS = arrStudent.get(i).get_maHS();
                        int trangThai = arrStudent.get(i).get_trangThai();

                        DiemDanh diemDanh = new DiemDanh();
                        diemDanh.set_ngayDD(dateDD);
                        diemDanh.set_maLop(classID);
                        diemDanh.set_maHS(maHS);
                        diemDanh.set_trangThai(trangThai);

                        Boolean check = database.createDiemDanh(diemDanh);

                        if(check == true){
                            count++;
                        }
                    }

                    if(count == size){
                        Intent intent = new Intent(getApplicationContext(), DiemDanhListActivity.class);
                        Toast.makeText(DiemDanhActivity.this, "Tạo bảng điểm danh thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DiemDanhActivity.this, "Tạo không thành công", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DiemDanhActivity.this, "Hôm nay đã điểm danh", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void Calendar(){
        calendar = Calendar.getInstance();
        updateCalendar();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCalendar();
            }
        };

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd = new DatePickerDialog(DiemDanhActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });
    }
    private void updateCalendar(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-20yy", Locale.getDefault());
        tvDate.setText(sdf.format(calendar.getTime()));
    }

    public void setBtnDiemDanhList(){
        btnDiemDanhList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DiemDanhListActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showHocSinhs(){
        layoutManager = new LinearLayoutManager(DiemDanhActivity.this, LinearLayoutManager.VERTICAL, false);
        rcvHocSinh.setLayoutManager(layoutManager);
        rcvHocSinhAdapter = new RCVHocSinhAdapter(DiemDanhActivity.this, R.layout.layout_diemdanh_hocsinh, arrStudent);
        rcvHocSinh.setAdapter(rcvHocSinhAdapter);
    }
}