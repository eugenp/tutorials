package com.baeldung.rules.jess.model;

public class Question {
    private String question;
    private int balance;

    public Question(String question, int balance) {
        this.question = question;
        this.balance = balance;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String toString() {
        return "Question(question=" + question + ", balance=" + balance + ")";
    }

}
