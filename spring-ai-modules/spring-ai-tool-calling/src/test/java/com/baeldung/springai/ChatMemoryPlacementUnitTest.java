package com.baeldung.springai;

import static com.baeldung.springai.ScriptedChatModel.answer;
import static com.baeldung.springai.ScriptedChatModel.toolCall;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.ToolCallingAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;

import com.baeldung.springai.service.WeatherService;
import com.baeldung.springai.tools.WeatherTools;

class ChatMemoryPlacementUnitTest {

    private static final String CONVERSATION_ID = "travel-session-42";

    private final WeatherTools weatherTools = new WeatherTools(new WeatherService());
    private final ChatMemory chatMemory = MessageWindowChatMemory.builder().build();

    private final ScriptedChatModel chatModel = new ScriptedChatModel(
        toolCall("getCurrentWeather", "{\"city\":\"Amsterdam\"}"),
        answer("Amsterdam is sunny and 18°C."));

    @Test
    void givenDefaultMemoryOrder_whenCalling_thenOnlyUserMessageAndFinalAnswerAreStored() {
        MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory)
            .build();

        askForWeather(memoryAdvisor);

        assertThat(chatMemory.get(CONVERSATION_ID))
            .extracting(Message::getMessageType)
            .containsExactly(MessageType.USER, MessageType.ASSISTANT);
    }

    @Test
    void givenMemoryInsideToolLoop_whenCalling_thenFullToolTranscriptIsStored() {
        MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory)
            .order(ToolCallingAdvisor.DEFAULT_ORDER + 100)
            .build();

        askForWeather(memoryAdvisor);

        assertThat(chatMemory.get(CONVERSATION_ID))
            .extracting(Message::getMessageType)
            .containsExactly(MessageType.USER, MessageType.ASSISTANT, MessageType.TOOL, MessageType.ASSISTANT);
    }

    private void askForWeather(MessageChatMemoryAdvisor memoryAdvisor) {
        ChatClient chatClient = ChatClient.builder(chatModel)
            .defaultTools(weatherTools)
            .defaultAdvisors(memoryAdvisor)
            .build();

        chatClient.prompt()
            .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, CONVERSATION_ID))
            .user("What is the weather in Amsterdam?")
            .call()
            .content();
    }
}
