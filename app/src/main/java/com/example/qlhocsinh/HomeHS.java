package com.example.qlhocsinh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qlhocsinh.ui.main.HomeHFragment;

import java.util.ArrayList;

public class HomeHS extends AppCompatActivity {

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
        setContentView(R.layout.activity_home_h);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, HomeHFragment.newInstance())
                    .commitNow();
        }
        tvHello = (TextView) findViewById(R.id.tvHello1);
        imgAvatar = (ImageView) findViewById(R.id.imgAvatar1);

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
    public void btnThi(View view)
    {
        Intent intent = new Intent(getApplicationContext(), LamBaiThi.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}