package com.example.android.spacequiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class EndScreen extends Activity {

    private RatingBar       ratingBar;
    private TextView        scoreView;
    private int             score;
    private float           percentScore;
    private int             questionNumber;
    private String          scorePercentageString;
    private Intent          intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_end_screen);

        ratingBar = (RatingBar) findViewById(R.id.ratingBarView);
        scoreView = (TextView) findViewById(R.id.scoreTextView);
        ratingBar.setMax(10);
        ratingBar.setNumStars(5);
        score = getIntent().getIntExtra("score",0);
        questionNumber = getIntent().getIntExtra("numberOfQuestions",0);

        percentScore = ((float)score/questionNumber)*100f;
        ratingBar.setProgress(score);
        scorePercentageString = String.format("%.1f", percentScore);

        scoreView.setText(scorePercentageString+"%");

        intent = new Intent(this,StartScreen.class);

        Button retryButton = (Button) findViewById(R.id.retry_button);
        retryButton.setText("Retry");

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        Button exitButton = (Button) findViewById(R.id.exit_button);
        exitButton.setText("Exit");
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
