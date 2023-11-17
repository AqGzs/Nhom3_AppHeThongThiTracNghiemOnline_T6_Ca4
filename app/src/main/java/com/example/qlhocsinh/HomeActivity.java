package com.example.qlhocsinh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    MyDatabase database;
    User user;
    Bundle bundle;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    TextView tvHello;
    ImageView imgAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        tvHello = (TextView) findViewById(R.id.tvHello);
        imgAvatar = (ImageView) findViewById(R.id.imgAvatar);

        sp = getSharedPreferences("Data", MODE_PRIVATE);

        database = new MyDatabase(this);
        user = new User();

        Intent intent = getIntent();
        bundle = intent.getExtras();

        if(bundle != null){
            ArrayList<String> arrDataUser = bundle.getStringArrayList("dataUser");
            user.convertArrayToUser(arrDataUser);
        }

        if(user.get_gioitinh().equals("Thầy")){
            imgAvatar.setBackgroundResource(R.drawable.ic_thaygiao);
        } else {
            imgAvatar.setBackgroundResource(R.drawable.ic_cogiao);
        }

        tvHello.setText("Xin chào\n" + user.get_ten());
    }

    public void btnInfo(View view) {
        Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void btnDiemDanh(View view) {
        Intent intent = new Intent(getApplicationContext(), DiemDanhActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void btnNhapDiem(View view) {
        Intent intent = new Intent(getApplicationContext(), NhapDiemActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void btnMonHoc(View view) {
        Intent intent = new Intent(getApplicationContext(), MonHocActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void btnHocSinh(View view) {
        Intent intent = new Intent(getApplicationContext(), HocSinhActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void btnLop(View view) {
        Intent intent = new Intent(getApplicationContext(), ClassActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void btnDanhSachCauHoi(View view) {
        Intent intent = new Intent(getApplicationContext(), DanhSachCauHoi.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void btnDangXuat(View view) {
        editor = sp.edit();
        editor.clear();
        editor.commit();
        finish();
        Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
    }
}