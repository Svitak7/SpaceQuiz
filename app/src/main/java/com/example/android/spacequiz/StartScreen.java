package com.example.android.spacequiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;


public class StartScreen extends Activity {


    Animation startButtonAnimation;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_screen);
        Toast.makeText(getApplicationContext(),"Tap on the title to begin",Toast.LENGTH_LONG).show();
        startButtonAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        startButtonAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                overridePendingTransition(R.anim.fadeout,R.anim.fadein);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }



    public void startButtonListener(View v)
    {
        intent = new Intent(this,QuizActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        v.startAnimation(startButtonAnimation);
        v.setVisibility(View.GONE);
    }

}
