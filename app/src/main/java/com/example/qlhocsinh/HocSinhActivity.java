package com.example.qlhocsinh;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HocSinhActivity extends AppCompatActivity {
    ImageButton btnBack;
    Button btnInsertHocSinh;
    static Button btnCloseDialog;
    static Button btnSubmitInsertHS;
    TextView tvTitle;
    protected static TextView tvTotalHS;
    protected static EditText edtTenHS;
    protected static EditText edtNgaySinhHS;
    protected static EditText edtTonGiao;
    protected static EditText edtDiaChi;
    protected static RadioGroup rdGioiTinhHS;
    ListView lvHS;
    protected static ArrayList<HocSinh> arrHS;
    Spinner spnChooseClass;
    protected static HocSinhAdapter hocSinhAdapter;
    protected static MyDatabase database;
    Bundle bundleUser;
    protected static Dialog dialog;
    static int idCLass;
    public Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoc_sinh);

        btnInsertHocSinh = (Button) findViewById(R.id.btnInsertHocSinh);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTotalHS = (TextView) findViewById(R.id.tvTotalHS);
        lvHS = (ListView) findViewById(R.id.lvHS_HS);
        spnChooseClass = (Spinner) findViewById(R.id.spnChooseClass);

        database = new MyDatabase(this);
        Intent intentUser = getIntent();
        bundleUser = intentUser.getExtras();

        updateAppbar();
        studentsHandle();
    }

    protected void updateAppbar(){
        tvTitle.setText("Danh sách học sinh");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected void studentsHandle(){
        showDSClass();
        showDSHocSinh();
        setBtnInsertHocSinh();
    }

    private void showDSHocSinh(){
        arrHS = new ArrayList<HocSinh>();
        hocSinhAdapter = new HocSinhAdapter(this, R.layout.layout_hocsinh, arrHS);
        lvHS.setAdapter(hocSinhAdapter);
    }

    private void setBtnInsertHocSinh(){
        btnInsertHocSinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBaseHocSinh(context, R.layout.layout_dialog_base_hocsinh);
                setBtnSubmitInsertHS();
            }
        });
    }

    private void showDSClass(){
        ArrayList<Class> classes = getDataClass(Integer.parseInt(bundleUser.getStringArrayList("dataUser").get(0)));
        String[] classItems = getNameClass(classes);
        spnChooseClass.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, classItems));
        selectClassItem(classes);
    }

    protected static void showDialogBaseHocSinh(Context context, int layoutID){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layoutID);

        edtTenHS = (EditText) dialog.findViewById(R.id.edtTenHS);
        edtNgaySinhHS = (EditText) dialog.findViewById(R.id.edtNgaySinhHS);
        edtTonGiao = (EditText) dialog.findViewById(R.id.edtTonGiao);
        edtDiaChi = (EditText) dialog.findViewById(R.id.edtDiaChi);
        rdGioiTinhHS = (RadioGroup) dialog.findViewById(R.id.rdGioiTinhHS);
        btnCloseDialog = (Button) dialog.findViewById(R.id.btnCloseDialog);
        btnSubmitInsertHS = (Button) dialog.findViewById(R.id.btnSubmitDialogHS);


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
        setBtnCloseDialog();
    }

    private void setBtnSubmitInsertHS(){
        btnSubmitInsertHS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenHS = edtTenHS.getText().toString();
                String ngaySinh = edtNgaySinhHS.getText().toString();
                String tonGiao = edtTonGiao.getText().toString();
                String diaChi = edtDiaChi.getText().toString();
                int gioiTinh = getValueRadio();

                if(!tenHS.isEmpty() && !ngaySinh.isEmpty() && gioiTinh != -1){
                    HocSinh hocSinh = new HocSinh(idCLass, tenHS, gioiTinh, ngaySinh, diaChi, tonGiao);
                    Boolean checkInsert = database.themHocSinh(hocSinh);

                    if(checkInsert == true){
                        Toast.makeText(HocSinhActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        edtTenHS.setText("");
                        edtNgaySinhHS.setText("");
                        edtTonGiao.setText("");
                        edtDiaChi.setText("");
                        getDataHocSinh();
                        hocSinhAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(HocSinhActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HocSinhActivity.this, "Tên và ngày sinh không được bỏ trống", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void selectClassItem(ArrayList<Class> classes){
        spnChooseClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(classes.size() > 0)
                {
                    idCLass = classes.get(i).get_id();
                    getDataHocSinh();
                    btnInsertHocSinh.setEnabled(true);
                } else {
                    btnInsertHocSinh.setBackgroundColor(getResources().getColor(R.color.gray));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(HocSinhActivity.this, "Bạn chưa chọn lớp", Toast.LENGTH_SHORT).show();
            }
        });
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

    public static void getDataHocSinh(){
        if(arrHS != null)
            arrHS.removeAll(arrHS);

        Cursor cursor = database.getCursorsHS(idCLass);
        while (cursor.moveToNext()){
            int maHS = cursor.getInt(0);
            int maLopHS = cursor.getInt(1);
            String tenHS = cursor.getString(2);
            int gioiTinhHS = cursor.getInt(3);
            String ngaySinhHS = cursor.getString(4);
            String diaChiHS = cursor.getString(5);
            String tonGiao = cursor.getString(6);

            HocSinh hocSinh = new HocSinh(maHS, maLopHS, tenHS, gioiTinhHS, ngaySinhHS, diaChiHS, tonGiao);
            hocSinhAdapter.add(hocSinh);
        }

        tvTotalHS.setText("Sỉ số: " + cursor.getCount());
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

    private static void setBtnCloseDialog(){
        btnCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    protected static int getValueRadio(){
        if(rdGioiTinhHS.getCheckedRadioButtonId() == -1)
            return -1;
        else if(rdGioiTinhHS.getCheckedRadioButtonId() == R.id.rdMale)
            return 0;
        return 1;
    }
}