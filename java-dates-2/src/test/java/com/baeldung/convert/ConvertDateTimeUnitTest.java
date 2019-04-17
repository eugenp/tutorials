package com.baeldung.convert;

import org.junit.Test;

public class ConvertDateTimeUnitTest {

    @Test
    public void givenLocalDateTime_WhenGetMillis() {
        long millis = ConvertDateTime.java8LocalDateTime();
        System.out.println("LocalDateTime in milliseconds : " + millis);
    }

}
