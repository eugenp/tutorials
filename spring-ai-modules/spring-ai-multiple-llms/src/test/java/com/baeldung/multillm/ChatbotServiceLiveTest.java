package com.baeldung.multillm;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariables;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.SetEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariables({
    @EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".*"),
    @EnabledIfEnvironmentVariable(named = "ANTHROPIC_API_KEY", matches = ".*")
})
@TestPropertySource(properties = {
    "logging.level.com.baeldung.multillm=DEBUG"
})
@ExtendWith(OutputCaptureExtension.class)
class ChatbotServiceLiveTest {

    private static final String LLM_PROMPT = "What is the capital of France?";
    private static final String EXPECTED_LLM_RESPONSE = "Paris";

    private static final String CORRECT_PRIMARY_LLM = "gpt-5";
    private static final String CORRECT_SECONDARY_LLM = "claude-opus-4-20250514";
    private static final String CORRECT_TERTIARY_LLM = "claude-3-haiku-20240307";

    private static final String INCORRECT_PRIMARY_LLM = "gpt-100";
    private static final String INCORRECT_SECONDARY_LLM = "claude-opus-200";

    @Autowired
    private ChatbotService chatbotService;

    @Nested
    @DirtiesContext
    @SetEnvironmentVariable.SetEnvironmentVariables({
        @SetEnvironmentVariable(key = "PRIMARY_LLM", value = CORRECT_PRIMARY_LLM),
        @SetEnvironmentVariable(key = "SECONDARY_LLM", value = CORRECT_SECONDARY_LLM),
        @SetEnvironmentVariable(key = "TERTIARY_LLM", value = CORRECT_TERTIARY_LLM)
    })
    class PrimaryLLMSucceedsLiveTest {

        @Test
        void whenPrimaryLLMAvailable_thenFallbackNotInitiated(CapturedOutput capturedOutput) {
            String response = chatbotService.chat(LLM_PROMPT);

            assertThat(response)
                .isNotEmpty()
                .containsIgnoringCase(EXPECTED_LLM_RESPONSE);
            assertThat(capturedOutput.getOut())
                .contains("Attempting to process prompt '" + LLM_PROMPT + "' with primary LLM. Attempt #1")
                .doesNotContain("Attempting to process prompt '" + LLM_PROMPT + "' with primary LLM. Attempt #2")
                .doesNotContain("Primary LLM failure");
        }
    }

    @Nested
    @DirtiesContext
    @SetEnvironmentVariable.SetEnvironmentVariables({
        @SetEnvironmentVariable(key = "PRIMARY_LLM", value = INCORRECT_PRIMARY_LLM),
        @SetEnvironmentVariable(key = "SECONDARY_LLM", value = CORRECT_SECONDARY_LLM),
        @SetEnvironmentVariable(key = "TERTIARY_LLM", value = CORRECT_TERTIARY_LLM)
    })
    class PrimaryLLMFailsLiveTest {

        @Test
        void whenPrimaryLLMFails_thenChatbotFallbacksToSecondaryLLM(CapturedOutput capturedOutput) {
            String response = chatbotService.chat(LLM_PROMPT);

            assertThat(response)
                .isNotEmpty()
                .containsIgnoringCase(EXPECTED_LLM_RESPONSE);
            assertThat(capturedOutput.getOut())
                .contains("Attempting to process prompt '" + LLM_PROMPT + "' with primary LLM. Attempt #1")
                .contains("Attempting to process prompt '" + LLM_PROMPT + "' with primary LLM. Attempt #2")
                .contains("Attempting to process prompt '" + LLM_PROMPT + "' with primary LLM. Attempt #3")
                .contains("Primary LLM failure")
                .contains("Attempting to process prompt '" + LLM_PROMPT + "' with secondary LLM")
                .doesNotContain("Secondary LLM failure");
        }
    }

    @Nested
    @DirtiesContext
    @SetEnvironmentVariable.SetEnvironmentVariables({
        @SetEnvironmentVariable(key = "PRIMARY_LLM", value = INCORRECT_PRIMARY_LLM),
        @SetEnvironmentVariable(key = "SECONDARY_LLM", value = INCORRECT_SECONDARY_LLM),
        @SetEnvironmentVariable(key = "TERTIARY_LLM", value = CORRECT_TERTIARY_LLM)
    })
    class PrimaryAndSecondaryLLMsFailLiveTest {

        @Test
        void whenPrimaryAndSecondaryLLMFail_thenChatbotFallbacksToTertiaryLLM(CapturedOutput capturedOutput) {
            String response = chatbotService.chat(LLM_PROMPT);

            assertThat(response)
                .isNotEmpty()
                .containsIgnoringCase(EXPECTED_LLM_RESPONSE);
            assertThat(capturedOutput.getOut())
                .contains("Attempting to process prompt '" + LLM_PROMPT + "' with primary LLM. Attempt #1")
                .contains("Attempting to process prompt '" + LLM_PROMPT + "' with primary LLM. Attempt #2")
                .contains("Attempting to process prompt '" + LLM_PROMPT + "' with primary LLM. Attempt #3")
                .contains("Primary LLM failure")
                .contains("Attempting to process prompt '" + LLM_PROMPT + "' with secondary LLM")
                .contains("Secondary LLM failure")
                .contains("Attempting to process prompt '" + LLM_PROMPT + "' with tertiary LLM");
        }
    }

}