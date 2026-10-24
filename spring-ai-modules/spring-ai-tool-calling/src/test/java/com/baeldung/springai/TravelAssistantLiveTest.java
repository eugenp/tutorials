package com.baeldung.springai;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.springai.service.BookingService;
import com.baeldung.springai.service.TravelAssistantService;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".*")
class TravelAssistantLiveTest {

    @Autowired
    private TravelAssistantService travelAssistantService;

    @Autowired
    private BookingService bookingService;

    @Test
    void whenAskingToBookIfSunny_thenModelChecksWeatherAndBooksFlight() {
        String answer = travelAssistantService.planTrip(
            "Check the weather in Amsterdam. If it is sunny, book a flight from LHR to AMS for 2026-11-12.");

        assertThat(answer).containsIgnoringCase("Amsterdam");
        assertThat(bookingService.getBookings())
            .anySatisfy(booking -> assertThat(answer).contains(booking.reference()));
    }

    @Test
    void whenUsingMemoryInsideToolLoop_thenFollowUpQuestionUsesPreviousToolResult() {
        travelAssistantService.chatWithMemory("memory-session", "What is the weather in Copenhagen?");

        String answer = travelAssistantService.chatWithMemory("memory-session",
            "What temperature did you just report? Reply with the number only.");

        assertThat(answer).contains("11");
    }

    @Test
    void whenUsingFunctionToolCallback_thenWeatherIsReturned() {
        String answer = travelAssistantService.askWithFunctionCallback("What is the weather in Copenhagen?");

        assertThat(answer).containsIgnoringCase("rain");
    }
}
