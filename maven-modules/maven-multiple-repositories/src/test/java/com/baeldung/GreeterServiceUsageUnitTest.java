package com.baeldung;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import com.baeldung.internal.repo.sample.GreeterServiceExample;
import com.baeldung.internal.repo.sample.Greeting;

public class GreeterServiceUsageUnitTest {
    @Test
    public void whenGreetingInEnglish_thenAnENCodeShouldBeMadeAvailable() {
        GreeterServiceExample greeterService
            = new GreeterServiceExample();
        Greeting englishGreeting
            = greeterService.greetInYourLanguage("English");
        assertEquals(
            "en", englishGreeting.getLanguage().getCode());
    }
}
