package com.example.android.spacequiz;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.android.spacequiz.QuestionRadioButton.numberOfQuestions;

public class QuizActivity extends Activity {

    private ArrayList<QuestionRadioButton> questionRadioButtonObjectArray    = new ArrayList<>();

    private int questionIncrement          = 0;
    private int clockIncrementAnimation    = 0;
    private int score                      = 0;
    private boolean clockDone              = false;
    private boolean animationIsStarted     = false;
    private boolean questionWasAnswered    = false;
    private boolean restart                = false;
    private int     timer                  = 10;
    private int     tempTimer              = 0;

    private TextView questionTextView;
    private RadioGroup radioGroup;
    private RadioButton firstAnswerRadioButton;
    private RadioButton secondAnswerRadioButton;
    private RadioButton thirdAnswerRadioButton;
    private ProgressBar clockCounterProgressBar;
    private ProgressBar quizCompletionProgressBar;
    private Button submitButton;
    private TextView counterTextView;
    private TransitionDrawable transition;
    private TransitionDrawable transition1;
    private TransitionDrawable transition2;
    private TransitionDrawable transition3;
    private CountDownTimer mCountDownTimer;

    enum answer {

        CORRECT,
        UNCORRECT,
        MISSING,

    }
    enum radioButtonEnum {
        RADIO_ONE,
        RADIO_TWO,
        RADIO_THREE,

    }

    private radioButtonEnum radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_quiz);
        initViews();
        createQuestionsRadio(questionRadioButtonObjectArray);
        if(savedInstanceState!=null) {
            questionIncrement           = savedInstanceState.getInt("questionIncrement");
            clockIncrementAnimation     = savedInstanceState.getInt("clockIncrementAnimation");
            score                       = savedInstanceState.getInt("score");
            clockDone                   = savedInstanceState.getBoolean("clockDone");
            animationIsStarted          = savedInstanceState.getBoolean("animationIsStarted");
            questionWasAnswered         = savedInstanceState.getBoolean("questionWasAnswered");
            tempTimer                   = savedInstanceState.getInt("tempTimer");
            clockIncrementAnimation-=1000;
            restart = true; //flag for reseting timer
        }

        displayQuestion(questionIncrement);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putInt("questionIncrement",      questionIncrement);
        savedInstanceState.putInt("score",                  score);
        savedInstanceState.putInt("clockIncrementAnimation",clockIncrementAnimation);
        savedInstanceState.putBoolean("clockDone",          clockDone);
        savedInstanceState.putBoolean("animationIsStarted", animationIsStarted);
        savedInstanceState.putBoolean("questionWasAnswered",questionWasAnswered);
        savedInstanceState.putInt("tempTimer",tempTimer);

        super.onSaveInstanceState(savedInstanceState);
    }

    private void initViews() {
        questionTextView            = (TextView)    findViewById(R.id.questionTextView);
        firstAnswerRadioButton      = (RadioButton) findViewById(R.id.radioButton1);
        secondAnswerRadioButton     = (RadioButton) findViewById(R.id.radioButton2);
        thirdAnswerRadioButton      = (RadioButton) findViewById(R.id.radioButton3);
        clockCounterProgressBar     = (ProgressBar) findViewById(R.id.counterProgressBar);
        quizCompletionProgressBar   = (ProgressBar) findViewById(R.id.questionsProgressBar);
        radioGroup                  = (RadioGroup)  findViewById(R.id.radioGroup);
        submitButton                = (Button)      findViewById(R.id.submitButton);
        counterTextView             = (TextView)    findViewById(R.id.counterTextView);
    }

    private void createQuestionsRadio(ArrayList<QuestionRadioButton> list) {
        String[] questionArray;
        String[] firstAnswerArray;
        String[] secondAnswerArray;
        String[] thirdAnswerArray;
        String[] correctAnswerArray;


        questionArray = getResources().getStringArray(R.array.questionArray);
        firstAnswerArray = getResources().getStringArray(R.array.firstAnswers);
        secondAnswerArray = getResources().getStringArray(R.array.secondAnswers);
        thirdAnswerArray = getResources().getStringArray(R.array.thirdAnswers);
        correctAnswerArray = getResources().getStringArray(R.array.correctAnswers);

        for (int i = 0; i < numberOfQuestions; i++) {
            list.add(new QuestionRadioButton(questionArray[i], firstAnswerArray[i], secondAnswerArray[i], thirdAnswerArray[i], correctAnswerArray[i]));
        }
    }

    private void displayQuestion(int questionNumber)
    {
        if(goToTheEndScreen())return;

        questionTextView.setText(questionRadioButtonObjectArray.get(questionNumber).getQuestion());
        firstAnswerRadioButton.setText(questionRadioButtonObjectArray.get(questionNumber).getAnswerOne());
        secondAnswerRadioButton.setText(questionRadioButtonObjectArray.get(questionNumber).getAnswerTwo());
        thirdAnswerRadioButton.setText(questionRadioButtonObjectArray.get(questionNumber).getAnswerThree());
        radioGroup.clearCheck();


        questionWasAnswered = false;
        clockDone = false;

        if(!restart)
        {
            clockCounterProgressBar.setProgress(0);
            clockIncrementAnimation = 0;
            countDown(timer, 1);
        }
        else
        {
            countDown(tempTimer, 1);
            restart = false;
        }


    }

    private boolean goToTheEndScreen()
    {
         if(questionIncrement>=QuestionRadioButton.getNumberOfQuestions()) {
             Intent intent = new Intent(getBaseContext(), EndScreen.class);
             intent.putExtra("score", score);
             intent.putExtra("numberOfQuestions", QuestionRadioButton.getNumberOfQuestions());
             intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
             startActivity(intent);
             overridePendingTransition(R.anim.fadeout, R.anim.fadein);
             finish();
             return true;
         }else return false;
    }

    private void animateAfterClockIsDone()
    {

        firstAnswerRadioButton.setBackground(getDrawable(R.drawable.button_boarder_transition_uncorrect));
        secondAnswerRadioButton.setBackground(getDrawable(R.drawable.button_boarder_transition_uncorrect));
        thirdAnswerRadioButton.setBackground(getDrawable(R.drawable.button_boarder_transition_uncorrect));

        transition1 = (TransitionDrawable) firstAnswerRadioButton.getBackground();
        transition2 = (TransitionDrawable) secondAnswerRadioButton.getBackground();
        transition3 = (TransitionDrawable) thirdAnswerRadioButton.getBackground();


        animateBoardersAndNextQuestion(transition1,false);
        animateBoardersAndNextQuestion(transition2,false);
        animateBoardersAndNextQuestion(transition3,true);
    }

    public void submitButton(View v)
    {
        questionMain();
    }

    private answer checkAnswersRadio() {
        String radioButtonText;

        if (firstAnswerRadioButton.isChecked()) {
            radioButtonText = firstAnswerRadioButton.getText().toString();

            radioButton = radioButtonEnum.RADIO_ONE;

        } else if (secondAnswerRadioButton.isChecked()) {
            radioButtonText = secondAnswerRadioButton.getText().toString();
            radioButton = radioButtonEnum.RADIO_TWO;
        } else if (thirdAnswerRadioButton.isChecked()) {
            radioButtonText = thirdAnswerRadioButton.getText().toString();
            radioButton = radioButtonEnum.RADIO_THREE;
        } else {
            return answer.MISSING;
        }

        if (radioButtonText.equals(questionRadioButtonObjectArray.get(questionIncrement).getCorrectAnswer())) {
            return answer.CORRECT;
        } else return answer.UNCORRECT;
    }

    private void countDown(int maxSeconds, int interval)
    {
        mCountDownTimer = new CountDownTimer((maxSeconds*1000)+2000, interval*1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {
                ObjectAnimator animation = ObjectAnimator.ofInt(clockCounterProgressBar, "progress", clockIncrementAnimation);
                animation.setDuration(1000);
                animation.setInterpolator(new DecelerateInterpolator());
                animation.start();
                tempTimer = (int) (millisUntilFinished/1000)-1;
                counterTextView.setText(String.valueOf((millisUntilFinished / 1000)-1));
                clockIncrementAnimation+=1000;
                if(questionWasAnswered)
                {
                    mCountDownTimer.cancel();
                }

            }
            @Override
            public void onFinish() {
                clockDone = true;
                animateAfterClockIsDone();
            }
        }.start();

    }

    private TransitionDrawable setAccordingBackgroundToAnimate(answer userAnswer)
    {
        if (radioButton == radioButtonEnum.RADIO_ONE)
        {
            if(userAnswer == answer.CORRECT) {
                firstAnswerRadioButton.setBackground(getDrawable(R.drawable.buttonboardertransistion));
            }else if(userAnswer == answer.UNCORRECT){
                firstAnswerRadioButton.setBackground(getDrawable(R.drawable.button_boarder_transition_uncorrect));
            }
            transition = (TransitionDrawable) firstAnswerRadioButton.getBackground();
        }
        else
        if (radioButton == radioButtonEnum.RADIO_TWO)
        {
            if(userAnswer == answer.CORRECT) {
                secondAnswerRadioButton.setBackground(getDrawable(R.drawable.buttonboardertransistion));
            }else if(userAnswer == answer.UNCORRECT){
                secondAnswerRadioButton.setBackground(getDrawable(R.drawable.button_boarder_transition_uncorrect));
            }
            transition = (TransitionDrawable) secondAnswerRadioButton.getBackground();
        }
        else
        if (radioButton == radioButtonEnum.RADIO_THREE)
        {
            if(userAnswer == answer.CORRECT) {
                thirdAnswerRadioButton.setBackground(getDrawable(R.drawable.buttonboardertransistion));
            }else if(userAnswer == answer.UNCORRECT){
                thirdAnswerRadioButton.setBackground(getDrawable(R.drawable.button_boarder_transition_uncorrect));
            }
            transition = (TransitionDrawable) thirdAnswerRadioButton.getBackground();
        }else return null;
        return transition;
    }

    private void questionMain()
    {
            answer userAnswer = checkAnswersRadio();
            TransitionDrawable transition;

            if(!clockDone && !animationIsStarted)
            {
                    if (userAnswer == answer.MISSING) {
                        Toast.makeText(this, "Select the answer", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (userAnswer == answer.CORRECT) score++;
                    transition = setAccordingBackgroundToAnimate(userAnswer);

                    if (transition == null) {
                    } else {
                        questionWasAnswered = true;
                        animateBoardersAndNextQuestion(transition, true);
                    }
            }
    }

    private void animateBoardersAndNextQuestion(final TransitionDrawable transition, final boolean nextQuestion)
    {
        transition.setCrossFadeEnabled(true);
        transition.startTransition(500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                transition.reverseTransition(1000);
                animationIsStarted = true;
            }
        }, 500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animation = ObjectAnimator.ofInt(quizCompletionProgressBar, "progress", (questionIncrement+1)*100);
                animation.setDuration(300);
                animation.setInterpolator(new DecelerateInterpolator());
                animation.start();

                if(nextQuestion) {
                    questionIncrement++;
                    displayQuestion(questionIncrement);
                    animationIsStarted = false;
                }



            }
        }, 1500);

    }


}