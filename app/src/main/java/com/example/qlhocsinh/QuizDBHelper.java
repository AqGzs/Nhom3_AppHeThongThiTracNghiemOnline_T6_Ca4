package com.example.qlhocsinh;

import static com.example.qlhocsinh.QuizContract.QuestionTable.TABLE_NAME;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.qlhocsinh.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "GoQuiz2";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public QuizDBHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String Question_Table = "CREATE TABLE " + TABLE_NAME + " ( " +
                QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_QUESTION + " TEXT, " +
                QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionTable.COLUMN_ANS + " INTEGER " + ")";
        db.execSQL(Question_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" Drop table if EXISTS " + TABLE_NAME);
        onCreate(db);
    }



    public Boolean suacauhoi(Question question){
        ContentValues values = new ContentValues();
        values.put(QuestionTable.COLUMN_QUESTION, question.getQuestion());
        values.put(QuestionTable.COLUMN_OPTION1, question.getOption1());
        values.put(QuestionTable.COLUMN_OPTION2, question.getOption2());
        values.put(QuestionTable.COLUMN_OPTION3, question.getOption3());
        values.put(QuestionTable.COLUMN_OPTION4, question.getOption4());
        values.put(QuestionTable.COLUMN_ANS, question.getAnswer());

        long result = db.update(TABLE_NAME, values, QuestionTable._ID + "=" +question.getId(), null);
        if(result == -1){
            return false;
        }
        return true;
    }
    public int getQuestionsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        int count = 0;

        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return count;
    }

    public ArrayList<Question> getallQues() {
        ArrayList<Question> questionList = new ArrayList<>();

        db = getReadableDatabase();

        String[] Projection = {
                QuestionTable._ID,
                QuestionTable.COLUMN_QUESTION,
                QuestionTable.COLUMN_OPTION1,
                QuestionTable.COLUMN_OPTION2,
                QuestionTable.COLUMN_OPTION3,
                QuestionTable.COLUMN_OPTION4,
                QuestionTable.COLUMN_ANS
        };

        Cursor c = db.query(
                TABLE_NAME,
                Projection,
                null,
                null,
                null,
                null,
                null
        );

        if (c != null) {
            int columnIndexQuestion = c.getColumnIndex(QuestionTable.COLUMN_QUESTION);
            int columnIndexOption1 = c.getColumnIndex(QuestionTable.COLUMN_OPTION1);
            int columnIndexOption2 = c.getColumnIndex(QuestionTable.COLUMN_OPTION2);
            int columnIndexOption3 = c.getColumnIndex(QuestionTable.COLUMN_OPTION3);
            int columnIndexOption4 = c.getColumnIndex(QuestionTable.COLUMN_OPTION4);
            int columnIndexAnswer = c.getColumnIndex(QuestionTable.COLUMN_ANS);

            while (c.moveToNext()) {
                Question question = new Question();

                if (columnIndexQuestion >= 0) {
                    question.setQuestion(c.getString(columnIndexQuestion));
                }

                if (columnIndexOption1 >= 0) {
                    question.setOption1(c.getString(columnIndexOption1));
                }

                if (columnIndexOption2 >= 0) {
                    question.setOption2(c.getString(columnIndexOption2));
                }

                if (columnIndexOption3 >= 0) {
                    question.setOption3(c.getString(columnIndexOption3));
                }

                if (columnIndexOption4 >= 0) {
                    question.setOption4(c.getString(columnIndexOption4));
                }

                if (columnIndexAnswer >= 0) {
                    question.setAnswer(c.getInt(columnIndexAnswer));
                }

                questionList.add(question);
            }
            c.close();
        }

        return questionList;
    }


}
