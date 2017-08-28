package com.baeldung.functional;

import java.util.Random;

import org.springframework.stereotype.Component;

public class MyService {

    public int getRandomNumber(){
        return (new Random().nextInt(10));
    }
    
}
