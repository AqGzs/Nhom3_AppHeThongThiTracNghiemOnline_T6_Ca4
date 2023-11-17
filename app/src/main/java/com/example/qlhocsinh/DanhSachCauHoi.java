package com.example.qlhocsinh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.qlhocsinh.ui.main.DanhSachCauHoiFragment;

import java.util.ArrayList;
import java.util.List;

public class DanhSachCauHoi extends AppCompatActivity {

    private ListView questionListView;
    private Button addQuestionButton,deleteSelectedQuestionsButton;
    private QuizDBHelper dbHelper;
    private List<Question> questionList;

    private List<Integer> selectedQuestionsPositions = new ArrayList<>(); // To store selected questions
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_cau_hoi);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DanhSachCauHoiFragment.newInstance())
                    .commitNow();
        }  questionListView = findViewById(R.id.questionListView);
        addQuestionButton = findViewById(R.id.addQuestionButton);
        deleteSelectedQuestionsButton = findViewById(R.id.deleteSelectedQuestionsButton);
        dbHelper = new QuizDBHelper(this);

        // Lấy danh sách câu hỏi từ cơ sở dữ liệu
        questionList = dbHelper.getallQues();

        // Tạo một danh sách các nội dung câu hỏi để hiển thị trong ListView
        List<String> questionContents = new ArrayList<>();
        for (Question question : questionList) {
            questionContents.add(question.getQuestion());
        }

        // Tạo một ArrayAdapter để hiển thị danh sách câu hỏi trong ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, questionContents);
        questionListView.setAdapter(adapter);

        // Xử lý sự kiện khi người dùng nhấn nút "Thêm câu hỏi"
        addQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến màn hình thêm câu hỏi (AddQuestionActivity)
                Intent intent = new Intent(DanhSachCauHoi.this, ThemQues.class);
                startActivity(intent);
            }
        });
        /*deleteSelectedQuestionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int position : selectedQuestionsPositions) {
                    int questionId = questionList.get(position).getId();
                    dbHelper.deleteQuestion(questionId);
                }
                // Clear the list of selected questions
                selectedQuestionsPositions.clear();
                // Refresh the list view
                adapter.notifyDataSetChanged();
            }
        });*/

        // Handle item clicks to select questions for deletion
        questionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectedQuestionsPositions.contains(position)) {
                    // If it's already selected, deselect it
                    selectedQuestionsPositions.remove(Integer.valueOf(position));
                } else {
                    // If it's not selected, select it
                    selectedQuestionsPositions.add(position);
                }
                adapter.notifyDataSetChanged(); // Update the view to reflect selection changes
            }
        });

        questionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DanhSachCauHoi.this, EditActivity.class);
                startActivity(intent);
            }
        });



    }
}