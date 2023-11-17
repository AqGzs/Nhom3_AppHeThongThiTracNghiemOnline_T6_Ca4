package com.example.qlhocsinh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.qlhocsinh.ui.main.ThemQuesFragment;

import java.util.ArrayList;
import java.util.List;

public class ThemQues extends AppCompatActivity {

    private EditText questionEditText, option1EditText, option2EditText, option3EditText, option4EditText;
    private RadioGroup answerRadioGroup;
    private Button saveButton;
    private QuizDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_ques);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ThemQuesFragment.newInstance())
                    .commitNow();
        }

        questionEditText = findViewById(R.id.questionEditText);
        option1EditText = findViewById(R.id.option1EditText);
        option2EditText = findViewById(R.id.option2EditText);
        option3EditText = findViewById(R.id.option3EditText);
        option4EditText = findViewById(R.id.option4EditText);
        answerRadioGroup = findViewById(R.id.answerRadioGroup);
        saveButton = findViewById(R.id.saveButton);

        dbHelper = new QuizDBHelper(this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = questionEditText.getText().toString();
                String option1 = option1EditText.getText().toString();
                String option2 = option2EditText.getText().toString();
                String option3 = option3EditText.getText().toString();
                String option4 = option4EditText.getText().toString();
                int answerId = answerRadioGroup.getCheckedRadioButtonId();

                if (question.isEmpty() || option1.isEmpty() || option2.isEmpty() || option3.isEmpty() || option4.isEmpty() || answerId == -1) {
                    Toast.makeText(ThemQues.this, "Vui lòng điền đầy đủ thông tin câu hỏi và đáp án.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int answer;
                switch (answerId) {
                    case R.id.option1RadioButton:
                        answer = 1;
                        break;
                    case R.id.option2RadioButton:
                        answer = 2;
                        break;
                    case R.id.option3RadioButton:
                        answer = 3;
                        break;
                    case R.id.option4RadioButton:
                        answer = 4;
                        break;
                    default:
                        answer = 0;
                }

                // Lưu câu hỏi vào cơ sở dữ liệu
                long newRowId = addQuestionToDatabase(question, option1, option2, option3, option4, answer);

                if (newRowId != -1) {
                    Toast.makeText(ThemQues.this, "Câu hỏi đã được lưu.", Toast.LENGTH_SHORT).show();
                    clearForm();
                } else {
                    Toast.makeText(ThemQues.this, "Lưu câu hỏi thất bại.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private long addQuestionToDatabase(String question, String option1, String option2, String option3, String option4, int answer) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QuizContract.QuestionTable.COLUMN_QUESTION, question);
        values.put(QuizContract.QuestionTable.COLUMN_OPTION1, option1);
        values.put(QuizContract.QuestionTable.COLUMN_OPTION2, option2);
        values.put(QuizContract.QuestionTable.COLUMN_OPTION3, option3);
        values.put(QuizContract.QuestionTable.COLUMN_OPTION4, option4);
        values.put(QuizContract.QuestionTable.COLUMN_ANS, answer);
        long newRowId = db.insert(QuizContract.QuestionTable.TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }

    private void clearForm() {
        questionEditText.setText("");
        option1EditText.setText("");
        option2EditText.setText("");
        option3EditText.setText("");
        option4EditText.setText("");
        answerRadioGroup.clearCheck();
    }
    public List<Question> getQuestionsFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                QuizContract.QuestionTable._ID,
                QuizContract.QuestionTable.COLUMN_QUESTION,
                QuizContract.QuestionTable.COLUMN_OPTION1,
                QuizContract.QuestionTable.COLUMN_OPTION2,
                QuizContract.QuestionTable.COLUMN_OPTION3,
                QuizContract.QuestionTable.COLUMN_OPTION4,
                QuizContract.QuestionTable.COLUMN_ANS
        };

        Cursor cursor = db.query(
                QuizContract.QuestionTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List<Question> questions = new ArrayList<>();
        while (cursor.moveToNext()) {
            int questionColumnIndex = cursor.getColumnIndex(QuizContract.QuestionTable.COLUMN_QUESTION);
            int option1ColumnIndex = cursor.getColumnIndex(QuizContract.QuestionTable.COLUMN_OPTION1);
            int option2ColumnIndex = cursor.getColumnIndex(QuizContract.QuestionTable.COLUMN_OPTION2);
            int option3ColumnIndex = cursor.getColumnIndex(QuizContract.QuestionTable.COLUMN_OPTION3);
            int option4ColumnIndex = cursor.getColumnIndex(QuizContract.QuestionTable.COLUMN_OPTION4);
            int answerColumnIndex = cursor.getColumnIndex(QuizContract.QuestionTable.COLUMN_ANS);

            int id = cursor.getInt(questionColumnIndex);
            String question = cursor.getString(questionColumnIndex);
            String option1 = cursor.getString(option1ColumnIndex);
            String option2 = cursor.getString(option2ColumnIndex);
            String option3 = cursor.getString(option3ColumnIndex);
            String option4 = cursor.getString(option4ColumnIndex);
            int answer = cursor.getInt(answerColumnIndex);

            Question newQuestion = new Question(id,question, option1, option2, option3, option4, answer);
            questions.add(newQuestion);
        }

        cursor.close();
        db.close();
        return questions;
    }
}