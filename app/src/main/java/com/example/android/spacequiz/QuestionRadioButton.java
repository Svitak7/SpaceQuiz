package com.example.android.spacequiz;

/**
 * Created by Adam on 03.04.2017.
 */

class QuestionRadioButton {


    public static  int numberOfQuestions;
    private String question;
    private String answerOne;
    private String answerTwo;
    private String answerThree;
    private String correctAnswer;

    public QuestionRadioButton(String question, String answerOne, String answerTwo, String answerThree, String correctAnswer)
    {
        this.question = question;
        this.answerOne = answerOne;
        this.answerTwo = answerTwo;
        this.answerThree = answerThree;
        this.correctAnswer = correctAnswer;
    }

    public QuestionRadioButton(String question, String answerOne, String answerTwo, String answerThree)
    {
        this.question = question;
        this.answerOne = answerOne;
        this.answerTwo = answerTwo;
        this.answerThree = answerThree;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public static int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public static void setNumberOfQuestions(int numberOfQuestions) {
        QuestionRadioButton.numberOfQuestions = numberOfQuestions;
    }

    public String getAnswerOne() {
        return answerOne;
    }

    public void setAnswerOne(String answerOne) {
        this.answerOne = answerOne;
    }

    public String getAnswerTwo() {
        return answerTwo;
    }

    public void setAnswerTwo(String answerTwo) {
        this.answerTwo = answerTwo;
    }

    public String getAnswerThree() {
        return answerThree;
    }

    public void setAnswerThree(String answerThree) {
        this.answerThree = answerThree;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
