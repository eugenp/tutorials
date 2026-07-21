package com.baeldung.springai;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest(
        properties = {
                "judge.score-threshold=0.7",
                "judge.max-refinements=2"
        }
)
class LlmJudgeAdvisorTest {

    @MockitoBean
    ChatModel chatModel;

    @Autowired
    ChatClient chatClient;

    @Test
    void givenLowQualityAnswer_whenAdvisorRuns_thenAnswerIsRefined() {
        when(chatModel.call(any(Prompt.class)))
                .thenReturn(buildChatResponse("It runs Java."))
                .thenReturn(buildChatResponse("""
                        {"score": 0.3, "feedback": "Too vague."}
                        """))
                .thenReturn(buildChatResponse("The JVM executes Java bytecode, manages memory, and enables platform independence."))
                .thenReturn(buildChatResponse("""
                        {"score": 0.9, "feedback": "Complete and accurate."}
                        """));

        String result = chatClient.prompt()
                .user("Explain what a JVM is.")
                .call()
                .content();

        assertThat(result).contains("bytecode");
    }

    @Test
    void givenLowQualityAnswer_whenAdvisorRuns_thenAnswerIsRefinedOnlyTwice() {
        when(chatModel.call(any(Prompt.class)))
                .thenReturn(buildChatResponse("It runs Java."))
                .thenReturn(buildChatResponse("""
                        {"score": 0.3, "feedback": "Too vague."}
                        """))
                .thenReturn(buildChatResponse("The JVM runs Java bytecode."))
                .thenReturn(buildChatResponse("""
                        {"score": 0.4, "feedback": "Still too vague."}
                        """))
                .thenReturn(buildChatResponse("The JVM executes Java bytecode, manages memory, and enables platform independence."));

        String result = chatClient.prompt()
                .user("Explain what a JVM is.")
                .call()
                .content();

        assertThat(result).contains("bytecode");
        Mockito.verify(chatModel, Mockito.times(5)).call(any(Prompt.class));
    }

    private ChatResponse buildChatResponse(String content) {
        return new ChatResponse(List.of(new Generation(new AssistantMessage(content))));
    }

}