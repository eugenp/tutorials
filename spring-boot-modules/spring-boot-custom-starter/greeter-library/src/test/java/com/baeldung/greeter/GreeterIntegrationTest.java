package com.baeldung.greeter;

import static com.baeldung.greeter.library.GreeterConfigParams.*;
import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import com.baeldung.greeter.library.Greeter;
import com.baeldung.greeter.library.GreetingConfig;
import org.junit.BeforeClass;
import org.junit.Test;

public class GreeterIntegrationTest {

    private static GreetingConfig greetingConfig;

    @BeforeClass
    public static void initalizeGreetingConfig() {
        greetingConfig = new GreetingConfig();
        greetingConfig.put(USER_NAME, "World");
        greetingConfig.put(MORNING_MESSAGE, "Good Morning");
        greetingConfig.put(AFTERNOON_MESSAGE, "Good Afternoon");
        greetingConfig.put(EVENING_MESSAGE, "Good Evening");
        greetingConfig.put(NIGHT_MESSAGE, "Good Night");
    }

    @Test
    public void givenMorningTime_ifMorningMessage_thenSuccess() {
        String expected = "Hello World, Good Morning";
        Greeter greeter = new Greeter(greetingConfig);
        String actual = greeter.greet(LocalDateTime.of(2017, 3, 1, 6, 0));
        assertEquals(expected, actual);
    }

    @Test
    public void givenAfternoonTime_ifAfternoonMessage_thenSuccess() {
        String expected = "Hello World, Good Afternoon";
        Greeter greeter = new Greeter(greetingConfig);
        String actual = greeter.greet(LocalDateTime.of(2017, 3, 1, 13, 0));
        assertEquals(expected, actual);
    }

    @Test
    public void givenEveningTime_ifEveningMessage_thenSuccess() {
        String expected = "Hello World, Good Evening";
        Greeter greeter = new Greeter(greetingConfig);
        String actual = greeter.greet(LocalDateTime.of(2017, 3, 1, 19, 0));
        assertEquals(expected, actual);
    }

    @Test
    public void givenNightTime_ifNightMessage_thenSuccess() {
        String expected = "Hello World, Good Night";
        Greeter greeter = new Greeter(greetingConfig);
        String actual = greeter.greet(LocalDateTime.of(2017, 3, 1, 21, 0));
        assertEquals(expected, actual);
    }
}
