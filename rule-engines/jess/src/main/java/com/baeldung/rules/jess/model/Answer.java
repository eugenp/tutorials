package com.baeldung.rules.jess.model;

public class Answer {
    private String answer;
    private int newBalance;

    public Answer(String answer, int newBalance) {
        this.answer = answer;
        this.newBalance = newBalance;
    }

    public int getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(int newBalance) {
        this.newBalance = newBalance;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String toString() {
        return "Answer(answer=" + answer + ", newBalance=" + newBalance + ")";
    }
}
