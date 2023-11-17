package com.example.qlhocsinh;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlhocsinh.ui.main.LamBaiThiFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class LamBaiThi extends AppCompatActivity {
    private Button next;
    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCd;
    private RadioGroup radioGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private TextView tvQues;
    private TextView tvScore , tvTime;
    private int questionCounter;
    private int questionTotalCount;
    private Question current;
    private boolean ans;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private int userScore;
    private long backPressedTime;

    public static final String EXTRA_SCORE = "extraScore";
    private static final long COUNTDOWN_IN_MILLIS = 30000;
    // Your QuizDBHelper instance
    private QuizDBHelper dbHelper;

    private ArrayList<Question> questionArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lam_bai_thi);

        // Initialize views
        next = findViewById(R.id.btnnext);
        radioGroup = findViewById(R.id.rdGr);
        rb1 = findViewById(R.id.radioButton);
        rb2 = findViewById(R.id.radioButton2);
        rb3 = findViewById(R.id.radioButton3);
        rb4 = findViewById(R.id.radioButton4);
        tvQues = findViewById(R.id.tvQue);
        tvScore = findViewById(R.id.tvScore);
        tvTime = findViewById(R.id.tvTime);

        textColorDefaultRb = rb1.getTextColors();
        textColorDefaultCd = tvTime.getTextColors();

        // Initialize other variables and listeners
        dbHelper = new QuizDBHelper(this); // Initialize your database helper
        questionTotalCount = questionArrayList.size();
        Collections.shuffle(questionArrayList);

        initializeQuiz();


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ans) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked()|| rb4.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(LamBaiThi.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showQuestion();
                }
            }
        });
    }

    private void initializeQuiz() {
        // Fetch questions from the database or any other source
        questionArrayList = dbHelper.getallQues(); // Implement this method in QuizDBHelper

        if (!questionArrayList.isEmpty()) {
            questionTotalCount = questionArrayList.size();
            questionCounter = 0;
            userScore = 0;
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ans) {
                        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                            checkAnswer();
                        } else {
                            Toast.makeText(LamBaiThi.this, "Please select an option", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            showQuestion();
        } else {
            // Handle the case when there are no questions in the database

            Toast.makeText(LamBaiThi.this, "No questions available.", Toast.LENGTH_SHORT).show();
        }
    }


    private void showQuestion() {
        radioGroup.clearCheck();
        if (questionCounter < questionTotalCount) {
            current = questionArrayList.get(questionCounter);

            tvQues.setText(current.getQuestion());
            rb1.setText(current.getOption1());
            rb2.setText(current.getOption2());
            rb3.setText(current.getOption3());
            rb4.setText(current.getOption4());

            questionCounter++;

            tvScore.setText("Question: " + questionCounter  + " / " + questionTotalCount);
            ans = false;
            next.setText("Confirm");
            // Clear the checked state and reset backgrounds of RadioButtons
            //radioGroup.clearCheck();
            //rb1.setBackgroundResource(android.R.drawable.btn_default);
            //rb2.setBackgroundResource(android.R.drawable.btn_default);
            //rb3.setBackgroundResource(android.R.drawable.btn_default);
            //rb4.setBackgroundResource(android.R.drawable.btn_default);

            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();

        } else {
            finishQuiz();
        }
    }

    private void checkAnswer() {
        ans = true;

        countDownTimer.cancel();

        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNr = radioGroup.indexOfChild(rbSelected) + 1;

        if (answerNr == current.getAnswer()) {
            userScore++;
            tvScore.setText("Score: " + userScore);
        }

        showSolution();
    }

    private void showSolution() {
        switch (current.getAnswer()) {
            case 1:
                tvQues.setText("Answer 1 is correct");
                break;
            case 2:
                tvQues.setText("Answer 2 is correct");
                break;
            case 3:
                tvQues.setText("Answer 3 is correct");
                break;
            case 4:
                tvQues.setText("Answer 4 is correct");
                break;
        }

        if (questionCounter < questionTotalCount) {
            next.setText("Next");
        } else {
            next.setText("Finish");
        }
    }

    private void finishQuiz() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, userScore);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        tvTime.setText(timeFormatted);

        if (timeLeftInMillis < 10000) {
            tvTime.setTextColor(Color.RED);
        } else {
            tvTime.setTextColor(textColorDefaultCd);
        }
    }
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuiz();
        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }


}