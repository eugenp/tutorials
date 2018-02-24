package com.baeldung.dipractice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Salary {

    private int experience;
    private int amount;

    @Autowired
    public Salary(@Value(value = "10000") int experience,@Value(value = "25") int amount) {
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
