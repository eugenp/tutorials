package com.baeldung.hexagonalarchitecture.domain.helper;

import com.baeldung.hexagonalarchitecture.domain.Greeting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomGreetingPickerUnitTest {

        private RandomGreetingPicker randomGreetingPicker;

        @BeforeEach void setUp() {
                this.randomGreetingPicker = new RandomGreetingPicker();
        }

        @Test public void whenProvidedSingleElement_thenReturnsThatElement() {
                List<Greeting> greetingList = createGreetings("hello");

                Optional<Greeting> randomGreeting = randomGreetingPicker.pickGreeting(greetingList);
                assertEquals("[hello]", randomGreeting.get().toString());
        }

        @Test public void whenProvidedMultipleElements_thenReturnsRandomElement() {
                List<Greeting> greetingList = createGreetings("hello", "hi", "wass up!!");

                Optional<Greeting> randomGreeting = randomGreetingPicker.pickGreeting(greetingList);
                assertTrue(greetingList.contains(randomGreeting.get()));
        }

        private List<Greeting> createGreetings(String... greetings) {
                return Arrays.stream(greetings).map(Greeting::new).collect(Collectors.toList());

        }

}