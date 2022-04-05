package com.baeldung.greeter.sample;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.greeter.library.Greeter;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GreeterSampleApplication.class)
public class GreeterSampleApplicationIntegrationTest {

    @Autowired
    private Greeter greeter;
    
    @Test
    public void givenMorningTime_ifMorningMessage_thenSuccess() {
        String expected = "Hello Baeldung, Good Morning";
        String actual = greeter.greet(LocalDateTime.of(2017, 3, 1, 6, 0));
        assertEquals(expected, actual);
    }

    @Test
    public void givenAfternoonTime_ifAfternoonMessage_thenSuccess() {
        String expected = "Hello Baeldung, Woha Afternoon";
        String actual = greeter.greet(LocalDateTime.of(2017, 3, 1, 13, 0));
        assertEquals(expected, actual);
    }

    @Test
    public void givenEveningTime_ifEveningMessage_thenSuccess() {
        String expected = "Hello Baeldung, Good Evening";
        String actual = greeter.greet(LocalDateTime.of(2017, 3, 1, 19, 0));
        assertEquals(expected, actual);
    }

    @Test
    public void givenNightTime_ifNightMessage_thenSuccess() {
        String expected = "Hello Baeldung, Good Night";
        String actual = greeter.greet(LocalDateTime.of(2017, 3, 1, 21, 0));
        assertEquals(expected, actual);
    }
    
}
