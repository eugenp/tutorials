package com.baeldung.callbackfunctions;

import org.junit.jupiter.api.Test;
import com.baeldung.callbackfunctions.ConsumerCallback;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsumerCallbackUnitTest {

    @Test
    public void whenIncreasingInitialAgeByGivenValueThroughCallback_shouldIncreaseAge(){
        ConsumerCallback consumerCallback = new ConsumerCallback();
        consumerCallback.getAge(20, (initialAge) -> {
            int ageDifference = 10;
            consumerCallback.increaseAge(initialAge, ageDifference, (newAge) -> {
                assertEquals(initialAge + ageDifference, newAge);
            });
        });
    }
}
