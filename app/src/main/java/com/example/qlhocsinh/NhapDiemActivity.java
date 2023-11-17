package com.example.qlhocsinh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class NhapDiemActivity extends AppCompatActivity {
    TextView tvTitle;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_diem);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        updateAppbar();
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

}