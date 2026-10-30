package com.baeldung.springai.openai.sdk;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import com.openai.errors.UnauthorizedException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

class OpenAiSdkConfigurationUnitTest {

    private static final String SUPPORT_REQUEST = "I was charged twice for my subscription. Please refund the duplicate charge.";
    private static final String SUMMARY = "The customer requests a refund for a duplicate subscription charge.";

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withInitializer(new ConfigDataApplicationContextInitializer())
            .withUserConfiguration(OpenAiSdkApplication.class)
            .withPropertyValues("OPENAI_API_KEY=test-openai-key", "GEMINI_API_KEY=test-gemini-key");

    @ParameterizedTest
    @CsvSource({ "default, /v1/, gpt-6-luna, test-openai-key, none, 256",
            "gemini, /v1beta/openai/, gemini-3.8-flash, test-gemini-key, low, 1024" })
    void givenProviderConfiguration_whenSummarizing_thenUseConfiguredSdkRequest(String profile, String basePath,
            String model, String apiKey, String reasoningEffort, int maxCompletionTokens) throws Exception {
        try (MockWebServer server = new MockWebServer()) {
            server.start();
            server.enqueue(
                    new MockResponse().setHeader("Content-Type", "application/json").setBody(chatCompletion(model)));

            contextRunner.withPropertyValues("spring.profiles.active=" + profile,
                    "spring.ai.openai.base-url=" + server.url(basePath)).run(context -> {
                        assertThat(context).hasNotFailed().hasSingleBean(OpenAiChatModel.class);

                        String summary = context.getBean(SupportSummaryService.class).summarize(SUPPORT_REQUEST);

                        assertThat(summary).isEqualTo(SUMMARY);
                        RecordedRequest request = server.takeRequest(5, TimeUnit.SECONDS);
                        assertThat(request).isNotNull();
                        assertThat(request.getMethod()).isEqualTo("POST");
                        assertThat(request.getPath()).isEqualTo(basePath + "chat/completions");
                        assertThat(request.getHeader("Authorization")).isEqualTo("Bearer " + apiKey);

                        JsonNode body = JsonMapper.builder().build().readTree(request.getBody().readUtf8());
                        assertThat(body.path("model").asString()).isEqualTo(model);
                        assertThat(body.path("reasoning_effort").asString()).isEqualTo(reasoningEffort);
                        assertThat(body.path("max_completion_tokens").asInt()).isEqualTo(maxCompletionTokens);
                        assertThat(body.has("max_tokens")).isFalse();
                        assertThat(body.path("messages").get(1).path("content").asString()).isEqualTo(SUPPORT_REQUEST);
                    });
        }
    }

    @Test
    void givenInvalidCredentials_whenSummarizing_thenPropagateSdkAuthenticationError() throws Exception {
        try (MockWebServer server = new MockWebServer()) {
            server.start();
            server.enqueue(
                    new MockResponse().setResponseCode(401).setHeader("Content-Type", "application/json").setBody(
                            "{\"error\":{\"message\":\"Invalid API key\",\"type\":\"invalid_request_error\",\"code\":\"invalid_api_key\"}}"));

            contextRunner.withPropertyValues("spring.ai.openai.base-url=" + server.url("/v1/")).run(context -> {
                assertThat(context).hasNotFailed();
                SupportSummaryService service = context.getBean(SupportSummaryService.class);

                assertThatThrownBy(() -> service.summarize(SUPPORT_REQUEST)).isInstanceOf(UnauthorizedException.class);
                assertThat(server.getRequestCount()).isEqualTo(1);
            });
        }
    }

    private String chatCompletion(String model) {
        return """
                {
                  "id": "chatcmpl-test",
                  "object": "chat.completion",
                  "created": 1,
                  "model": "%s",
                  "choices": [{"index": 0, "message": {"role": "assistant", "content": "%s"}, "finish_reason": "stop"}],
                  "usage": {"prompt_tokens": 20, "completion_tokens": 10, "total_tokens": 30}
                }
                """.formatted(model, SUMMARY);
    }
}
