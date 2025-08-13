package com.baeldung.springai.vectorstore.oracle;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".*")
class RAGChatbotLiveTest {

    private static final String OUT_OF_SCOPE_MESSAGE = "This question is outside the scope of the available Breaking Bad quotes.";
    private static final String NO_INFORMATION_MESSAGE = "The provided quotes do not contain information to answer this question.";

    @Autowired
    private ChatClient chatClient;

    @ParameterizedTest
    @ValueSource(strings = {
        "How does the show portray the mentor-student dynamic?",
        "Which characters in the show portray insecurity through their quotes?",
        "Does the show contain quotes with mature themes inappropriate for young viewers?"
    })
    void whenQuestionsRelatedToBreakingBadAsked_thenRelevantAnswerReturned(String userQuery) {
        String response = chatClient
            .prompt(userQuery)
            .call()
            .content();

        assertThat(response)
            .isNotBlank()
            .doesNotContain(OUT_OF_SCOPE_MESSAGE, NO_INFORMATION_MESSAGE);
    }

    @Test
    void whenUnrelatedQuestionAsked_thenOutOfScopeMessageReturned() {
        String response = chatClient
            .prompt("Did Jon Jones duck Tom Aspinall?")
            .call()
            .content();

        assertThat(response)
            .isEqualTo(OUT_OF_SCOPE_MESSAGE);
    }

    @Test
    void whenQuestionWithNoRelevantQuotesAsked_thenNoInformationMessageReturned() {
        String response = chatClient
            .prompt("What does Walter White think about Albuquerque's weather?")
            .call()
            .content();

        assertThat(response)
            .isEqualTo(NO_INFORMATION_MESSAGE);
    }

}