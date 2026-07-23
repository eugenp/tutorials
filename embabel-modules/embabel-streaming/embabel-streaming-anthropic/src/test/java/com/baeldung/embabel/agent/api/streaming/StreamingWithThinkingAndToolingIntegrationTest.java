/*
 * Example got patterned after:
 * <link>https://github.com/embabel/embabel-agent/blob/main/embabel-agent-autoconfigure/models/embabel-agent-anthropic-autoconfigure/src/test/java/com/embabel/agent/config/models/anthropic/LLMAnthropicStreamingBuilderIT.java</link>
 *
 * Original code (see link above) was developed by Embabel Pty Ltd, 2026
 */
package com.baeldung.embabel.agent.api.streaming;

import com.embabel.agent.AgentTestApplication;
import com.embabel.agent.api.annotation.LlmTool;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.common.PromptRunner;
import com.embabel.agent.api.streaming.StreamingPromptRunnerBuilder;
import com.embabel.agent.api.tool.callback.LogLevel;
import com.embabel.agent.api.tool.callback.ToolCallLoggingInspector;
import com.embabel.common.ai.model.LlmOptions;
import com.embabel.common.ai.model.Thinking;
import com.embabel.common.core.streaming.StreamingEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
    classes = AgentTestApplication.class,
    properties = {
        "embabel.models.cheapest=claude-sonnet-4-5",
        "embabel.models.best=claude-sonnet-4-5",
        "embabel.models.default-llm=claude-sonnet-4-5",
        "embabel.agent.platform.llm-operations.prompts.defaultTimeout=240s",
        "embabel.agent.platform.llm-operations.data-binding.fixedBackoffMillis=6000",
        "embabel.agent.platform.scanning.annotation=false"
    }
)
@ActiveProfiles("tool-reasoning")
class StreamingWithThinkingAndToolingIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(StreamingWithThinkingAndToolingIntegrationTest.class);

    @Autowired
    private Ai ai;

    @BeforeAll
    static void setUp() {
        System.setProperty("embabel.agent.shell.interactive.enabled", "false");
    }

    // TODO: move to embabel-tutorial-common once available
    public record ParkingRecommendation(
        String scenario,
        Option chosenOption,
        String location,
        int estimatedTotalCost,
        String summary
    ) {
        @SuppressWarnings("unused")
        public enum Option {
            STREET, METER, GARAGE
        }
    }

    // TODO: move to embabel-tutorial-common once available
    static class ParkingTooling {

        @LlmTool(description = "Find free street parking. Uncertain and may take time.")
        public String findStreetParking(String location, int maxMinutes) {
            boolean found = ThreadLocalRandom.current().nextDouble() < 0.3;
            if (found) {
                return "Street parking found near " + location + " (free)";
            }
            return "No street parking found within " + maxMinutes + " minutes";
        }

        @LlmTool(description = "Find metered parking. Moderate cost and moderate availability. May have time limits.")
        public String findMeterParking(String location, int maxMinutes) {
            boolean found = ThreadLocalRandom.current().nextDouble() < 0.6;
            if (found) {
                return "Metered parking found near " + location + " ($5/hour, 2-hour limit)";
            }
            return "No metered parking found within " + maxMinutes + " minutes";
        }

        @LlmTool(description = "Reserve guaranteed garage parking near destination.")
        public String reserveGarage(String location) {
            return "Garage reserved near " + location + " ($30/hour, guaranteed)";
        }
    }

    private static final String PARKING_PROMPT =
        """
        An advisor is driving to a client meeting in Midtown Manhattan.
        Constraints: 30 minutes until the meeting, meeting lasts 3 hours.
        Options: street parking (free, uncertain), metered ($5/hr, 2-hour limit), garage ($30/hr, guaranteed).
        Recommend the best parking option.
        """;

    private static final String TOOLING_PROMPT =
        """
        An advisor needs to park in Midtown Manhattan for a 3-hour client meeting starting in 30 minutes.
        Use the available tools to probe parking options, then recommend the best one.
        Arriving late is not acceptable.
        """;

    /**
     * Verifies structured streaming output with thinking enabled and no tools.
     *
     * <p>{@link Thinking#withTokenBudget(int)} sets {@code thinkingEnabled=true} in the streaming
     * converter, sending {@code <think>} format instructions in the system prompt.
     * Budget must be less than {@code max_tokens}=8192 for {@code claude-sonnet-4-5}.
     *
     * <p>The streaming pipeline emits one {@link com.embabel.common.core.streaming.StreamingEvent.Thinking}
     * event per line, so expect multiple reasoning events rather than a single block.
     */
    @Nested
    class StreamingWithThinkingNoTools {

        @Test
        void whenStreamingWithThinking_thenReceivesReasoningAndRecommendation() {
            // budget_tokens must be < max_tokens (8192). This also enables <think> format instructions.
            LlmOptions thinkingOptions = new LlmOptions().withThinking(Thinking.withTokenBudget(8000));
            PromptRunner runner = ai.withDefaultLlm().withLlm(thinkingOptions);
            assertTrue(runner.supportsStreaming(), "Default LLM must support streaming");

            List<ParkingRecommendation> received = new CopyOnWriteArrayList<>();
            List<String> reasoning = new CopyOnWriteArrayList<>();
            AtomicReference<Throwable> errorOccurred = new AtomicReference<>();
            AtomicBoolean completionCalled = new AtomicBoolean(false);

            Flux<StreamingEvent<ParkingRecommendation>> stream = new StreamingPromptRunnerBuilder(runner)
                .streaming()
                .withPrompt(PARKING_PROMPT)
                .createObjectStreamWithThinking(ParkingRecommendation.class);

            stream
                .timeout(Duration.ofSeconds(120))
                .doOnNext(event -> {
                    if (event.isObject()) {
                        ParkingRecommendation rec = event.getObject();
                        if (rec != null) {
                            received.add(rec);
                            logger.info("Received recommendation: option={}, cost={}, summary={}",
                                rec.chosenOption(), rec.estimatedTotalCost(), rec.summary());
                        }
                    } else if (event.isThinking()) {
                        reasoning.add(event.getThinking());
                        logger.info("Received reasoning: {}", event.getThinking());
                    }
                })
                .doOnError(error -> {
                    errorOccurred.set(error);
                    logger.error("Stream error: {}", error.getMessage());
                })
                .doOnComplete(() -> {
                    completionCalled.set(true);
                    logger.info("Stream completed: {} recommendations, {} reasoning blocks",
                        received.size(), reasoning.size());
                })
                .blockLast(Duration.ofSeconds(240));

            assertNull(errorOccurred.get(), "Streaming should not produce errors");
            assertTrue(completionCalled.get(), "Stream should complete successfully");
            assertFalse(received.isEmpty(), "Should receive at least one parking recommendation");
            assertFalse(reasoning.isEmpty(), "Should receive reasoning blocks");
        }
    }

    @Nested
    class StreamingWithThinkingAndTooling {

        /**
         * Verifies that streaming with tools produces reasoning and a recommendation informed by tool results.
         *
         * <p>Reasoning events are emitted only from the final LLM iteration — after all tool calls
         * complete. Intermediate reasoning (the model's thinking while deciding which tools to call)
         * is not surfaced to the subscriber, as each tool-loop iteration replaces the previous stream.
         */
        @Test
        void whenStreamingWithThinkingAndTooling_thenReceivesRecommendationAndReasoning() {
            PromptRunner runner = ai.withDefaultLlm()
                .withToolObject(new ParkingTooling())
                .withToolCallInspectors(new ToolCallLoggingInspector(LogLevel.INFO, logger));
            assertTrue(runner.supportsStreaming(), "Default LLM must support streaming");

            List<ParkingRecommendation> received = new CopyOnWriteArrayList<>();
            List<String> reasoning = new CopyOnWriteArrayList<>();
            AtomicReference<Throwable> errorOccurred = new AtomicReference<>();
            AtomicBoolean completionCalled = new AtomicBoolean(false);

            Flux<StreamingEvent<ParkingRecommendation>> stream = new StreamingPromptRunnerBuilder(runner)
                .streaming()
                .withPrompt(TOOLING_PROMPT)
                .createObjectStreamWithThinking(ParkingRecommendation.class);

            stream
                .timeout(Duration.ofSeconds(120))
                .doOnNext(event -> {
                    if (event.isObject()) {
                        ParkingRecommendation rec = event.getObject();
                        if (rec != null) {
                            received.add(rec);
                            logger.info("Received recommendation: option={}, cost={}, summary={}",
                                rec.chosenOption(), rec.estimatedTotalCost(), rec.summary());
                        }
                    } else if (event.isThinking()) {
                        reasoning.add(event.getThinking());
                        logger.info("Received reasoning: {}", event.getThinking());
                    }
                })
                .doOnError(error -> {
                    errorOccurred.set(error);
                    logger.error("Stream error: {}", error.getMessage());
                })
                .doOnComplete(() -> {
                    completionCalled.set(true);
                    logger.info("Stream completed: {} recommendations, {} reasoning blocks",
                        received.size(), reasoning.size());
                })
                .blockLast(Duration.ofSeconds(240));

            assertNull(errorOccurred.get(), "Streaming should not produce errors");
            assertTrue(completionCalled.get(), "Stream should complete successfully");
            assertFalse(received.isEmpty(), "Should receive at least one parking recommendation");
            assertFalse(reasoning.isEmpty(), "Should receive reasoning blocks alongside tool results");
        }
    }
}
