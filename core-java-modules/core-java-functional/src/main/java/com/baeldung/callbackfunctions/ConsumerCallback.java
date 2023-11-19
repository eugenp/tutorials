package com.baeldung.callbackfunctions;

import java.util.function.Consumer;

public class ConsumerCallback {
    public void getAge(int initialAge, Consumer<Integer> callback) {
        callback.accept(initialAge);
    }

    public void increaseAge(int initialAge, int ageDifference, Consumer<Integer> callback) {
        System.out.println("===== Increase age ====");

        int newAge = initialAge + ageDifference;
        callback.accept(newAge);

    }
}
