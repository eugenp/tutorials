package com.baeldung.callbackfunctions;

import org.junit.jupiter.api.Test;
import com.baeldung.callbackfunctions.ConsumerCallback;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsumerCallbackUnitTest {

    @Test
    void whenIncreasingInitialAgeByGivenValueThroughCallback_shouldIncreaseAge(){
        ConsumerCallback consumerCallback = new ConsumerCallback();
        int ageDifference = 10;
        AtomicInteger newAge1 = new AtomicInteger();
        int initialAge = 20;
        consumerCallback.getAge(initialAge, (initialAge1) -> {
            consumerCallback.increaseAge(initialAge, ageDifference, (newAge) -> {
                System.out.printf("New age ==> %s", newAge);
                newAge1.set(newAge);

            });
        });
        assertEquals(initialAge + ageDifference, newAge1.get());
    }
}
