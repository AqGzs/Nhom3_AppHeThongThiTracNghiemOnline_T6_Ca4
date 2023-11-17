package com.example.qlhocsinh;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MonHocActivity extends AppCompatActivity {
    MyDatabase database;
    Bundle bundleUser;
    ImageButton btnBack, btnInsertMonHoc;
    TextView tvTitle;
    Spinner spnChooseClass;
    RecyclerView rcvMonHoc;
    RecyclerView.LayoutManager layoutManager;
    MonHocAdapter monHocAdapter;
    ArrayList<Class> classes;
    String[] classItems;
    ArrayList<MonHoc> monHocs;
    Dialog dialog;
    int classID;
    MonHoc chooseMonHoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_hoc);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnInsertMonHoc = (ImageButton) findViewById(R.id.btnInsertMonHoc);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        spnChooseClass = (Spinner) findViewById(R.id.spnChooseClass);
        rcvMonHoc = (RecyclerView) findViewById(R.id.rcvMonHoc);


        database = new MyDatabase(this);
        Intent intent = getIntent();
        bundleUser = intent.getExtras();
        monHocs = new ArrayList<>();

        updateAppbar();
        showClass();
        setBtnInsertMonHoc();
    }

    private void setBtnInsertMonHoc() {
        btnInsertMonHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    public void updateAppbar(){
        tvTitle.setText("Danh sách học sinh");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void showClass(){
        classes = getDataClass(Integer.parseInt(bundleUser.getStringArrayList("dataUser").get(0)));
        classItems = getNameClass(classes);
        spnChooseClass.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, classItems));
        selectClassItem();
    }

    public void showMonHoc(){
        layoutManager = new LinearLayoutManager(MonHocActivity.this, LinearLayoutManager.VERTICAL, false);
        rcvMonHoc.setLayoutManager(layoutManager);
        monHocAdapter = new MonHocAdapter(this, R.layout.layout_monhoc_item, monHocs);
        rcvMonHoc.setAdapter(monHocAdapter);
    }

    public void getDataMonHoc(int classID){
        if(monHocs != null){
            monHocs.removeAll(monHocs);
        }

        Cursor cursor = database.getCursorMonHoc(classID);

        while(cursor.moveToNext()){
            MonHoc monHoc = new MonHoc();
            monHoc.setMaMH(cursor.getInt(0));
            monHoc.setMaLop(cursor.getInt(1));
            monHoc.setTenMH(cursor.getString(2));
            monHoc.setHinhAnh(cursor.getInt(3));
            monHocs.add(monHoc);
        }
    }

    private ArrayList<Class> getDataClass(int idUser){
        Cursor cursor = database.getCursorsClass(idUser);

        ArrayList<Class> classList = new ArrayList<Class>();
        while(cursor.moveToNext()) {
            Class newClass = new Class();
            newClass.set_id(cursor.getInt(0));
            newClass.set_tenLop(cursor.getString(3));
            classList.add(newClass);
        }
        return classList;
    }

    private String[] getNameClass(ArrayList<Class> classes){
        if(classes.size() == 0){
            return new String[]{"Không chọn"};
        }
        String[] arrClass = new String[classes.size()];
        for(int i = 0; i < classes.size(); i++)
            arrClass[i] = classes.get(i).get_tenLop();
        return arrClass;
    }

    private void selectClassItem(){
        spnChooseClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(classes.size() > 0){
                    getDataMonHoc(classes.get(i).get_id());
                    showMonHoc();
                    monHocAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(MonHocActivity.this, "Bạn chưa chọn lớp", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showDialog(){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_insert_monhoc);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.transparent);

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.show();
        showSpinner();
    }

    public void showSpinner(){
        Spinner spnLop = (Spinner) dialog.findViewById(R.id.spnLop);
        Spinner spnMonHoc = (Spinner) dialog.findViewById(R.id.spnMonHoc);
        EditText edtLop = (EditText) dialog.findViewById(R.id.edtLop);
        EditText edtTenMonHoc = (EditText) dialog.findViewById(R.id.edtTenMonHoc);
        ImageView imgMonHoc = (ImageView) dialog.findViewById(R.id.imgMonHoc);
        Button btnCloseDialog = (Button) dialog.findViewById(R.id.btnCloseDialog);
        Button btnSubmitInsertMH = (Button) dialog.findViewById(R.id.btnSubmitInsertMH);

        spnLop.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, classItems));
        spnMonHoc.setAdapter(new SpinnerMonHocAdapter(this, R.layout.layout_spinner_monhoc, DataMonHoc.getMonHocs()));

        spnLop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(classes.size() > 0){
                    classID = classes.get(i).get_id();
                    edtLop.setText(classes.get(i).get_tenLop());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnMonHoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chooseMonHoc = DataMonHoc.getMonHocs().get(i);
                if(!chooseMonHoc.getTenMH().equals("Khác")){
                    edtTenMonHoc.setEnabled(false);
                    edtTenMonHoc.setText(chooseMonHoc.getTenMH());
                    imgMonHoc.setBackgroundResource(chooseMonHoc.getHinhAnh());

                } else {
                    edtTenMonHoc.setEnabled(true);
                    edtTenMonHoc.setText("");
                    edtTenMonHoc.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSubmitInsertMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtLop.getText().toString().isEmpty() && !edtTenMonHoc.getText().toString().isEmpty()){
                    MonHoc monHoc = new MonHoc();
                    monHoc.setMaLop(classID);
                    monHoc.setTenMH(edtTenMonHoc.getText().toString());
                    monHoc.setHinhAnh(chooseMonHoc.getHinhAnh());;

                    Boolean check = database.insertMonHoc(monHoc);

                    if(check == true){
                        dialog.dismiss();
                        getDataMonHoc(classID);
                        monHocAdapter.notifyDataSetChanged();
                        Toast.makeText(MonHocActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MonHocActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MonHocActivity.this, "Dữ liệu không đầy đủ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}