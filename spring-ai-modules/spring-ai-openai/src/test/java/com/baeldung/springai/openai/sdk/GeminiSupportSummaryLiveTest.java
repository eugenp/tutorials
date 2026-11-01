package com.baeldung.springai.openai.sdk;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("gemini")
@EnabledIfEnvironmentVariable(named = "GEMINI_API_KEY", matches = "\\S+")
class GeminiSupportSummaryLiveTest {

    private static final Logger logger = LoggerFactory.getLogger(GeminiSupportSummaryLiveTest.class);

    @Autowired
    private SupportSummaryService summaryService;

    @Test
    @Timeout(60)
    void givenSupportRequest_whenCallingGemini_thenReturnSummary() {
        String summary = summaryService
                .summarize("I was charged twice for my subscription. Please refund the duplicate charge.");

        assertThat(summary).isNotBlank();
        logger.info("Gemini support summary: {}", summary);
    }
}
