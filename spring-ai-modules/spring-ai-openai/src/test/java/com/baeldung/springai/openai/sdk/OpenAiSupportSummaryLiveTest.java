package com.baeldung.springai.openai.sdk;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = "\\S+")
class OpenAiSupportSummaryLiveTest {

    private static final Logger logger = LoggerFactory.getLogger(OpenAiSupportSummaryLiveTest.class);

    @Autowired
    private SupportSummaryService summaryService;

    @Test
    @Timeout(60)
    void givenSupportRequest_whenCallingOpenAi_thenReturnSummary() {
        String summary = summaryService
                .summarize("I was charged twice for my subscription. Please refund the duplicate charge.");

        assertThat(summary).isNotBlank();
        logger.info("OpenAI support summary: {}", summary);
    }
}
