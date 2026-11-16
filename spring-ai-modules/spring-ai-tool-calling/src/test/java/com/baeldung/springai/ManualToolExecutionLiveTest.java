package com.baeldung.springai;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.springai.service.BookingNotApprovedException;
import com.baeldung.springai.service.ManualToolExecutionService;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".*")
class ManualToolExecutionLiveTest {

    private static final String QUESTION = "Book a flight from LHR to AMS for 2026-11-12 if the weather in Amsterdam is sunny";

    @Autowired
    private ManualToolExecutionService manualToolExecutionService;

    @Test
    void givenApprovedBooking_whenRunningManualLoop_thenModelReturnsFinalAnswer() {
        String answer = manualToolExecutionService.planTrip(QUESTION, toolCall -> true);

        assertThat(answer).isNotBlank();
    }

    @Test
    void givenRejectedBooking_whenRunningManualLoop_thenBookingIsStopped() {
        assertThatThrownBy(() -> manualToolExecutionService.planTrip(QUESTION, toolCall -> false))
            .isInstanceOf(BookingNotApprovedException.class);
    }
}
