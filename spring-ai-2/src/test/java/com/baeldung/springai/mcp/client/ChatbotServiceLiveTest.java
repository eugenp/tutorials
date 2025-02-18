package com.baeldung.springai.mcp.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".*")
class ChatbotServiceLiveTest {

    @Autowired
    private ChatbotService chatbotService;

    @Nested
    @EnabledIfEnvironmentVariable(named = "BRAVE_API_KEY", matches = ".*")
    class BraveSearchLiveTest {

        @Test
        void whenQuestionAskedAfterKnowledgeCutoff_thenCorrectAnswerReturned() {
            String question = "How much was Elon Musk's initial offer to buy OpenAI in 2025?";

            String answer = chatbotService.chat(question);

            assertThat(answer)
                .isNotBlank()
                .contains("97.4", "billion");
        }

    }

    @Nested
    class FileSystemLiveTest {

        private static final String FILE_NAME = "mcp-demo.txt";

        @Test
        void whenCreatingNewFile_thenFileCreatedWithCorrectContent() throws IOException {
            String content = "This is awesome!";
            String question = "Create a text file named '%s' with content '%s'.".formatted(FILE_NAME, content);

            String answer = chatbotService.chat(question);

            assertThat(answer)
                .isNotBlank();
            assertThat(Files.exists(Path.of(FILE_NAME)))
                .isTrue();
            assertThat(Files.readString(Path.of(FILE_NAME)))
                .isEqualTo(content);
        }

        @AfterEach
        void cleanUp() throws IOException {
            Files.deleteIfExists(Path.of(FILE_NAME));
        }

    }

    @Nested
    class AuthorToolsLiveTest {

        @Test
        void whenQueryingWithArticleTile_thenCorrectAuthorDetailsReturned() {
            String question = "Who wrote the article 'Testing CORS in Spring Boot?' on Baeldung, and how can I contact them?";

            String answer = chatbotService.chat(question);

            assertThat(answer)
                .isNotBlank()
                .contains("John Doe", "john.doe@baeldung.com");
        }

        @Test
        void whenQueryingTopAuthors_thenCorrectListOfAuthorsReturned() {
            String question = "Who are some of the top-rated authors on Baeldung?";

            String answer = chatbotService.chat(question);

            assertThat(answer)
                .isNotBlank()
                .contains("John Doe", "Jane Doe");
        }

    }

}