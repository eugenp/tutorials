package com.baeldung.springai;

import static com.baeldung.springai.ScriptedChatModel.answer;
import static com.baeldung.springai.ScriptedChatModel.toolCall;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.ToolCallingAdvisor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.tool.ToolCallback;

import com.baeldung.springai.service.BookingService;
import com.baeldung.springai.service.WeatherService;
import com.baeldung.springai.tools.BookingTools;
import com.baeldung.springai.tools.WeatherTools;

class ToolCallingLoopUnitTest {

    private final WeatherTools weatherTools = new WeatherTools(new WeatherService());
    private final BookingService bookingService = new BookingService();
    private final BookingTools bookingTools = new BookingTools(bookingService);

    private final ScriptedChatModel chatModel = new ScriptedChatModel(
        toolCall("getCurrentWeather", "{\"city\":\"Amsterdam\"}"),
        toolCall("bookFlight", "{\"origin\":\"LHR\",\"destination\":\"AMS\",\"date\":\"2026-11-12\"}"),
        answer("Amsterdam is sunny. Your flight is booked."));

    @Test
    void whenModelRequestsTwoTools_thenLoopExecutesBothAndReturnsFinalAnswer() {
        ChatClient chatClient = ChatClient.builder(chatModel)
            .defaultTools(weatherTools)
            .build();

        String answer = chatClient.prompt()
            .user("Check the weather in Amsterdam. If it is sunny, book a flight from LHR to AMS for 2026-11-12.")
            .tools(bookingTools)
            .call()
            .content();

        assertThat(answer).isEqualTo("Amsterdam is sunny. Your flight is booked.");
        assertThat(chatModel.getPrompts()).hasSize(3);
        assertThat(bookingService.getBookings())
            .singleElement()
            .satisfies(booking -> assertThat(booking.date()).isEqualTo(LocalDate.of(2026, 11, 12)));
    }

    @Test
    void whenToolsAreAddedPerCall_thenTheyAreAppendedToDefaultTools() {
        ChatClient chatClient = ChatClient.builder(chatModel)
            .defaultTools(weatherTools)
            .build();

        chatClient.prompt()
            .user("Check the weather in Amsterdam and book a flight")
            .tools(bookingTools)
            .call()
            .content();

        assertThat(toolNames(chatModel.getPrompts().getFirst()))
            .containsExactlyInAnyOrder("getCurrentWeather", "bookFlight");
    }

    @Test
    void givenAdvisorsAroundAndInsideToolLoop_whenCalling_thenOnlyInnerAdvisorSeesEveryIteration() {
        RecordingAdvisor outer = new RecordingAdvisor("outer", ToolCallingAdvisor.DEFAULT_ORDER - 100);
        RecordingAdvisor inner = new RecordingAdvisor("inner", ToolCallingAdvisor.DEFAULT_ORDER + 100);

        ChatClient chatClient = ChatClient.builder(chatModel)
            .defaultTools(weatherTools, bookingTools)
            .defaultAdvisors(outer, inner)
            .build();

        chatClient.prompt()
            .user("Check the weather in Amsterdam and book a flight")
            .call()
            .content();

        assertThat(outer.getResponses()).hasSize(1);
        assertThat(inner.getResponses()).hasSize(3);
        assertThat(inner.getResponses())
            .extracting(response -> response.chatResponse().hasToolCalls())
            .containsExactly(true, true, false);
    }

    @Test
    void givenTwoToolAdvisors_whenCalling_thenChatClientRejectsTheChain() {
        ChatClient chatClient = ChatClient.builder(chatModel)
            .defaultAdvisors(ToolCallingAdvisor.builder().build(), ToolCallingAdvisor.builder()
                .advisorOrder(ToolCallingAdvisor.DEFAULT_ORDER + 1)
                .build())
            .build();

        assertThatThrownBy(() -> chatClient.prompt()
            .user("Hello")
            .call()
            .content())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("At most one ToolAdvisor");
    }

    private static List<String> toolNames(Prompt prompt) {
        return ((ToolCallingChatOptions) prompt.getOptions()).getToolCallbacks()
            .stream()
            .map(ToolCallback::getToolDefinition)
            .map(definition -> definition.name())
            .toList();
    }
}
