package com.baeldung.springai;

import static com.baeldung.springai.ScriptedChatModel.answer;
import static com.baeldung.springai.ScriptedChatModel.toolCall;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;

import com.baeldung.springai.service.BookingNotApprovedException;
import com.baeldung.springai.service.BookingService;
import com.baeldung.springai.service.ManualToolExecutionService;
import com.baeldung.springai.service.WeatherService;
import com.baeldung.springai.tools.BookingTools;
import com.baeldung.springai.tools.WeatherTools;

class ManualToolExecutionUnitTest {

    private static final String QUESTION = "Book a flight to Amsterdam if the weather is sunny";

    private final BookingService bookingService = new BookingService();

    private final ScriptedChatModel chatModel = new ScriptedChatModel(
        toolCall("getCurrentWeather", "{\"city\":\"Amsterdam\"}"),
        toolCall("bookFlight", "{\"origin\":\"LHR\",\"destination\":\"AMS\",\"date\":\"2026-11-12\"}"),
        answer("Amsterdam is sunny. Your flight is booked."));

    private final ManualToolExecutionService service = new ManualToolExecutionService(
        ChatClient.builder(chatModel).build(),
        new WeatherTools(new WeatherService()),
        new BookingTools(bookingService));

    @Test
    void givenApprovedBooking_whenRunningManualLoop_thenFlightIsBooked() {
        String answer = service.planTrip(QUESTION, toolCall -> true);

        assertThat(answer).isEqualTo("Amsterdam is sunny. Your flight is booked.");
        assertThat(chatModel.getPrompts()).hasSize(3);
        assertThat(bookingService.getBookings()).hasSize(1);
    }

    @Test
    void givenRejectedBooking_whenRunningManualLoop_thenWeatherToolRunsButFlightIsNotBooked() {
        assertThatThrownBy(() -> service.planTrip(QUESTION, toolCall -> false))
            .isInstanceOf(BookingNotApprovedException.class);

        assertThat(chatModel.getPrompts()).hasSize(2);
        assertThat(bookingService.getBookings()).isEmpty();
    }
}
