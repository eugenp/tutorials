package com.baeldung.functional;

import java.util.Random;

public class MyService {

    public int getRandomNumber() {
        return (new Random().nextInt(10));
    }

}
