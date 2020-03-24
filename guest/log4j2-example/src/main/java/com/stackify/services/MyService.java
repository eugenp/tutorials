package com.stackify.services;

import java.time.LocalDate;
import java.time.Period;

import com.stackify.models.User;

public class MyService {

    public int calculateUserAge(User user) {
        return Period.between(user.getDateOfBirth(), LocalDate.now()).getYears();
    }

}
