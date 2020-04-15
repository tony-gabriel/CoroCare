package com.example.coronahelpapp;

public class Question {
    private String question;
    private String option1;
    private String option2;
    private int answerNr1;
    private int answerNr2;

    public Question() {
    }

    public Question(String question, String option1, String option2, int answerNr1, int answerNr2) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.answerNr1 = answerNr1;
        this.answerNr2 = answerNr2;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public int getAnswerNr1() {
        return answerNr1;
    }

    public void setAnswerNr1(int answerNr1) {
        this.answerNr1 = answerNr1;
    }

    public int getAnswerNr2() {
        return answerNr2;
    }

    public void setAnswerNr2(int answerNr2) {
        this.answerNr2 = answerNr2;
    }
}
