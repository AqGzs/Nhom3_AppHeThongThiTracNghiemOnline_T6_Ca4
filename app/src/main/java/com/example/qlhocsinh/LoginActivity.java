package com.example.qlhocsinh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    public static MyDatabase database;
    EditText edtEmail, edtPassword;
    Button btnLogin;
    CheckBox cbRemember;
    User user;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Bundle bundle;

    Spinner spin1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = new MyDatabase(this);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        spin1 = (Spinner) findViewById(R.id.spinner01);
        sp = getSharedPreferences("Data", MODE_PRIVATE);
        editor = sp.edit();
        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.usertype, android.support.design.R.layout.support_simple_spinner_dropdown_item);
        spin1.setAdapter(adapter);

        Boolean login = sp.getBoolean("DaDangNhap", false);

        if (login == true) {
            user = database.checkEmailPassword(sp.getString("email", "email"), sp.getString("password", "password"));
            switchView();
        }
    }

    public void btnRegister(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    public void btnLogin(View view) {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        //String email = "tk1";
        //String password = "123";

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Các ô nhập không bỏ trống!", Toast.LENGTH_SHORT).show();
        } else {
            user = database.checkEmailPassword(email, password);
            if (user != null)
            {
                if (cbRemember.isChecked()) {
                    editor.putBoolean("DaDangNhap", true);
                    editor.putString("email", user.get_email());
                    editor.putString("password", user.get_matkhau());
                    editor.apply();
                }
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                switchView();
            }
            else {
                Toast.makeText(this, "Email hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void switchView(){
        Intent intent;
        bundle = new Bundle();
        bundle.putStringArrayList("dataUser", user.convertUserToArray());

        String item1 = spin1.getSelectedItem().toString();

        if(user.get_ten() != null && item1.equals("Teacher")){
            intent = new Intent(getApplicationContext(), HomeActivity.class);
        }
        else if(user.get_ten() == null && item1.equals("Teacher")) {
            intent = new Intent(getApplicationContext(), EditInfoActivity.class);
        }
        else if (user.get_ten() != null && item1.equals("Student")){
            intent = new Intent(getApplicationContext(), HomeHS.class);
        }
        else {
            intent = new Intent(getApplicationContext(), ThongTinHS.class);
        }
        intent.putExtras(bundle);
        startActivity(intent);
    }
}