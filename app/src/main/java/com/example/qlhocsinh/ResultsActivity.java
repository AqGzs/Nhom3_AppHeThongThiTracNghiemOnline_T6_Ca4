package com.example.qlhocsinh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.qlhocsinh.ui.main.ResultsFragment;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ResultsFragment.newInstance())
                    .commitNow();
        }
    }
    Intent intent = getIntent();
    int userScore = intent.getIntExtra("userScore", 0);
    int questionTotalCount = intent.getIntExtra("questionTotalCount", 0);

    // Update the UI with the results
    TextView scoreTextView = findViewById(R.id.scoreTextView);
}