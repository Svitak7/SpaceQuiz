package com.example.android.spacequiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.TextView;

public class EndScreen extends Activity {

    private RatingBar       ratingBar;
    private TextView        scoreView;
    private int             score;
    private float           percentScore;
    private int             questionNumber;
    private String          scorePercentageString;


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
    }
}
