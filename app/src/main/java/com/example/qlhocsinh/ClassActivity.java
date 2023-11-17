package com.example.qlhocsinh;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClassActivity extends AppCompatActivity {
    MyDatabase database;
    Bundle bundle;
    SharedPreferences sp;
    ImageButton btnBack;
    TextView tvTitle;
    ListView lvClass;
    ArrayList<Class> classArrayList;
    //Danh sách các lớp của alertBox thêm lớp học
    String[] classList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    ArrayAdapter arrayAdapter;
    ClassArrayAdapter adapter;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        sp = getSharedPreferences("Data", MODE_PRIVATE);


        database = new MyDatabase(this);
        Intent intent = getIntent();
        bundle = intent.getExtras();

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        lvClass = (ListView) findViewById(R.id.lvClass);

        // Danh sách các lớp học hiển thị trong trang Lớp học
        classArrayList = new ArrayList<Class>();
        getDataClass();
        adapter = new ClassArrayAdapter(this, R.layout.layout_class_item, classArrayList, lvClass);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, classList);
        lvClass.setAdapter(adapter);
        lvClass.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        tvTitle.setText("Lớp Học");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goHome = new Intent(getApplicationContext(), HomeActivity.class);
                goHome.putExtras(bundle);
                startActivity(goHome);
            }
        });

        lvClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                menuItemMore(view, i);
            }
        });
    }

    public void btnInsertClass(View view) {
        showClassDialog();

        Spinner spnInsert = dialog.findViewById(R.id.spnInsertClass);
        spnInsert.setAdapter(arrayAdapter);
        EditText edtInsertClassName = dialog.findViewById(R.id.edtNameInsertClass);
        EditText edtInsertDescription = dialog.findViewById(R.id.edtInsertDescription);
        Button btnSubmitInsertClass = dialog.findViewById(R.id.btnSubmitInsertClass);

        btnSubmitInsertClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectClass = Integer.parseInt(spnInsert.getSelectedItem().toString());
                String className = edtInsertClassName.getText().toString();
                String description = edtInsertDescription.getText().toString();

                if(className.isEmpty() == false){
                    Class myClass = new Class();
                    myClass.set_user_id(Integer.parseInt(bundle.getStringArrayList("dataUser").get(0)));
                    myClass.setLop(selectClass);
                    myClass.set_tenLop(className);
                    myClass.set_mota(description);

                    Boolean insertClass = database.insertClass(myClass);

                    if(insertClass == true){
                        Toast.makeText(ClassActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        getDataClass();
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(ClassActivity.this, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ClassActivity.this, "Hãy nhập tên lớp", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    public void menuItemMore(View view, int position){
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.menu_class_option_more, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Class myClass = classArrayList.get(position);
                switch (menuItem.getItemId()) {
                    case R.id.menuItem_edit:
                        showClassDialog();
                        dialog.setContentView(R.layout.layout_dialog_edit_class);

                        Spinner spnEdit = dialog.findViewById(R.id.spnEditClass);
                        spnEdit.setAdapter(arrayAdapter);
                        EditText edtEditNameClass = (EditText) dialog.findViewById(R.id.edtNameEditClass);
                        EditText edtEditDescription = (EditText) dialog.findViewById(R.id.edtEditDescription);

                        edtEditNameClass.setText(myClass.get_tenLop());
                        edtEditDescription.setText(myClass.get_mota());

                        Button btnSubmitEditClass = dialog.findViewById(R.id.btnSubmitEditClass);
                        btnSubmitEditClass.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                myClass.setLop(Integer.parseInt(spnEdit.getSelectedItem().toString()));
                                myClass.set_tenLop(edtEditNameClass.getText().toString());
                                myClass.set_mota(edtEditDescription.getText().toString());

                                Boolean checkEdit = database.updateClass(myClass);
                                if(checkEdit == true){
                                    Toast.makeText(ClassActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    getDataClass();
                                    adapter.notifyDataSetChanged();
                                }
                                else{
                                    Toast.makeText(ClassActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        dialog.show();
                        return true;
                    case R.id.menuItem_remove:
                        if(database.removeClass(classArrayList.get(position)) == true)
                        {
                            Toast.makeText(ClassActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            getDataClass();
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(ClassActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    public void showClassDialog(){
        // Custom layout dialog thêm lớp học
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_insert_class);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.transparent);

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
    }

    public void getDataClass(){
        if(classArrayList != null)
            classArrayList.removeAll(classArrayList);

        Cursor cursor = database.getCursorsClass(Integer.parseInt(bundle.getStringArrayList("dataUser").get(0)));

        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            int userId = cursor.getInt(1);
            int lop = cursor.getInt(2);
            String tenLop = cursor.getString(3);
            String moTa = cursor.getString(4);

            Class newClass = new Class(id, userId, lop, tenLop, moTa);
            classArrayList.add(newClass);
        }
    }

    // Event Button
    public void btnCancelClass(View view){
        dialog.dismiss();
    }
}