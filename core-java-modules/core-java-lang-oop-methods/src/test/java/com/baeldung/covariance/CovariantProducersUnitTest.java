package com.baeldung.covariance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CovariantProducersUnitTest {

    @Test
    public void whenInputIsArbitrary_thenProducerProducesString() {
        String arbitraryInput = "just a random text";
        Producer producer = new Producer();

        Object objectOutput = producer.produce(arbitraryInput);

        assertEquals(arbitraryInput, objectOutput);
        assertEquals(String.class, objectOutput.getClass());
    }

    @Test
    public void whenInputIsArbitrary_thenIntegerProducerFails() {
        String arbitraryInput = "just a random text";
        Producer producer = new IntegerProducer();

        assertThrows(NumberFormatException.class, () -> producer.produce(arbitraryInput));
    }

    @Test
    public void whenInputIsSupported_thenProducerCreatesInteger() {
        String integerAsString = "42";
        Producer producer = new IntegerProducer();

        Object result = producer.produce(integerAsString);

        assertEquals(Integer.class, result.getClass());
        assertEquals(Integer.parseInt(integerAsString), result);
    }

    @Test
    public void whenInputIsSupported_thenIntegerProducerCreatesIntegerWithoutCasting() {
        String integerAsString = "42";
        IntegerProducer producer = new IntegerProducer();

        Integer result = producer.produce(integerAsString);

        assertEquals(Integer.parseInt(integerAsString), result);
    }
}
