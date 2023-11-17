package com.example.qlhocsinh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.qlhocsinh.ui.main.EditFragment;

public class EditActivity extends AppCompatActivity {

    private QuizDBHelper dbHelper;
    private EditText editQuestionContent;
    private EditText editOption1;
    private EditText editOption2;
    private EditText editOption3;
    private EditText editOption4;
    private RadioGroup editCorrectAnswerGroup;
    private Button saveEditButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, EditFragment.newInstance())
                    .commitNow();
        }
        dbHelper = new QuizDBHelper(this);
        int questionId = getIntent().getIntExtra("questionId", -1);
        if (questionId != -1) {
            // Use the questionId to retrieve the question's details from your database
            Question questionToEdit = dbHelper.getallQues().get(questionId); // Implement this method
            // Populate the UI elements with the question's details
            editQuestionContent.setText(questionToEdit.getQuestion());
            editOption1.setText(questionToEdit.getOption1());
            editOption2.setText(questionToEdit.getOption2());
            editOption3.setText(questionToEdit.getOption3());
            editOption4.setText(questionToEdit.getOption4());
            // Set the correct answer radio button based on questionToEdit

            // Implement a "Save" button to save the edited question
            saveEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Retrieve the edited values from the UI elements
                    String editedQuestion = editQuestionContent.getText().toString();
                    String editedOption1 = editOption1.getText().toString();
                    String editedOption2 = editOption2.getText().toString();
                    String editedOption3 = editOption3.getText().toString();
                    String editedOption4 = editOption4.getText().toString();
                    Intent listIntent = new Intent(EditActivity.this, DanhSachCauHoi.class);
                    startActivity(listIntent);
                }
            });
        }
    }
}