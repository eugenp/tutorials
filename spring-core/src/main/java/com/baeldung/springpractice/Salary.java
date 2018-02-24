package com.baeldung.springpractice;

public class Salary {

    private int experience;
    private int amount;

    public Salary(int experience, int amount) {
        this.experience = experience;
        this.amount = amount;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
