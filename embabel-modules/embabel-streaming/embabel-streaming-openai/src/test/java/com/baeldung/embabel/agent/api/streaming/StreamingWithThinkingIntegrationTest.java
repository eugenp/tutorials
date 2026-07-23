/*
 * Example got patterned after:
 * <link>https://github.com/embabel/embabel-agent/blob/main/embabel-agent-autoconfigure/models/embabel-agent-openai-autoconfigure/src/test/java/com/embabel/agent/config/models/openai/LLMOpenAiStreamingBuilderIT.java</link>
 *
 * Original code (see link above) was developed by Embabel Pty Ltd, 2026
 */
package com.baeldung.embabel.agent.api.streaming;

import com.embabel.agent.AgentTestApplication;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.common.PromptRunner;
import com.embabel.agent.api.streaming.StreamingPromptRunnerBuilder;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
    classes = AgentTestApplication.class,
    properties = {
        "embabel.models.cheapest=gpt-4.1-mini",
        "embabel.models.best=gpt-4.1-mini",
        "embabel.models.default-llm=gpt-4.1-mini",
        "embabel.agent.platform.llm-operations.prompts.defaultTimeout=240s",
        "embabel.agent.platform.llm-operations.data-binding.fixedBackoffMillis=6000",
        "embabel.agent.platform.scanning.annotation=false"
    }
)
@ActiveProfiles("tool-reasoning")
class StreamingWithThinkingIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(StreamingWithThinkingIntegrationTest.class);

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

    private static final String PARKING_PROMPT =
        """
        An advisor is driving to a client meeting in Midtown Manhattan.
        Constraints: 30 minutes until the meeting, meeting lasts 3 hours.
        Options: street parking (free, uncertain), metered ($5/hr, 2-hour limit), garage ($30/hr, guaranteed).
        Recommend the best parking option.
        """;

    private static final String TIMED_PARKING_PROMPT =
        """
        Provide parking recommendations for an advisor visiting Midtown Manhattan across three time scenarios:
        1. Early morning (before 8am): street meters are free before 8am
        2. Business hours (9am-5pm): all paid options apply, 30-minute window, 3-hour stay
        3. Evening (after 6pm): meters are free after 6pm, but garages may close at 9pm (3-hour stay at risk)

        Return all three recommendations.
        """;

    @Nested
    class SimpleStreaming {

        /** Verifies that a single parking recommendation is received via streaming. */
        @Test
        void whenStreaming_thenReceivesParkingRecommendation() {
            PromptRunner runner = ai.withDefaultLlm();
            assertTrue(runner.supportsStreaming(), "Default LLM must support streaming");

            List<ParkingRecommendation> received = new CopyOnWriteArrayList<>();
            AtomicReference<Throwable> errorOccurred = new AtomicReference<>();
            AtomicBoolean completionCalled = new AtomicBoolean(false);

            Flux<ParkingRecommendation> stream = new StreamingPromptRunnerBuilder(runner)
                .streaming()
                .withPrompt(PARKING_PROMPT)
                .createObjectStream(ParkingRecommendation.class);

            stream
                .timeout(Duration.ofSeconds(120))
                .doOnNext(rec -> {
                    received.add(rec);
                    logger.info("Received parking recommendation: option={}, cost={}, summary={}",
                        rec.chosenOption(), rec.estimatedTotalCost(), rec.summary());
                })
                .doOnError(error -> {
                    errorOccurred.set(error);
                    logger.error("Stream error: {}", error.getMessage());
                })
                .doOnComplete(() -> {
                    completionCalled.set(true);
                    logger.info("Stream completed successfully");
                })
                .blockLast(Duration.ofSeconds(240));

            assertNull(errorOccurred.get(), "Streaming should not produce errors");
            assertTrue(completionCalled.get(), "Stream should complete successfully");
            assertFalse(received.isEmpty(), "Should receive at least one parking recommendation");
        }
    }

    /**
     * Verifies text-instructed thinking on OpenAI via {@code <think>} format tags.
     *
     * <p>{@link Thinking#withTokenBudget(int)} sets {@code thinkingEnabled=true} in the streaming
     * converter, which includes {@code <think>} format instructions in the system prompt.
     * OpenAI ignores the budget parameter — there is no native thinking API call.
     *
     * <p>{@code gpt-4.1-mini} follows the instructions and outputs reasoning wrapped in
     * {@code <think>...</think>} tags. When emitted as a single line, tags are stripped and
     * one clean reasoning event is produced. When emitted as multiple lines, {@code <think>}
     * appears in the first event and {@code </think>} in the last — tags are not stripped in
     * that case. The number of events and whether tags appear depends on how the model chunks
     * its output.
     *
     * <p>Note: {@code gpt-5.4-mini} does not follow {@code <think>} format instructions and
     * produces 0 reasoning blocks.
     */
    @Nested
    class StreamingWithInstructedThinking {

        @Test
        void whenStreamingWithInstructedThinking_thenReceivesReasoningBlocks() {
            // withTokenBudget sets thinkingEnabled=true → sends <think> format instructions.
            // OpenAI ignores the budget; gpt-4.1-mini outputs multi-line reasoning inside <think> tags.
            LlmOptions thinkingOptions = new LlmOptions().withThinking(Thinking.withTokenBudget(10000));
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
    class StreamingCollection {

        /** Verifies that multiple parking recommendations stream in, one per time scenario. */
        @Test
        void whenStreamingMultipleScenarios_thenReceivesRecommendationPerScenario() {
            PromptRunner runner = ai.withDefaultLlm();
            assertTrue(runner.supportsStreaming(), "Default LLM must support streaming");

            List<ParkingRecommendation> received = new CopyOnWriteArrayList<>();
            AtomicReference<Throwable> errorOccurred = new AtomicReference<>();
            AtomicBoolean completionCalled = new AtomicBoolean(false);

            Flux<ParkingRecommendation> stream = new StreamingPromptRunnerBuilder(runner)
                .streaming()
                .withPrompt(TIMED_PARKING_PROMPT)
                .createObjectStream(ParkingRecommendation.class);

            stream
                .timeout(Duration.ofSeconds(120))
                .doOnNext(rec -> {
                    received.add(rec);
                    logger.info("Received recommendation: scenario={}, option={}, cost={}",
                        rec.scenario(), rec.chosenOption(), rec.estimatedTotalCost());
                })
                .doOnError(error -> {
                    errorOccurred.set(error);
                    logger.error("Stream error: {}", error.getMessage());
                })
                .doOnComplete(() -> {
                    completionCalled.set(true);
                    logger.info("Stream completed with {} recommendations", received.size());
                })
                .blockLast(Duration.ofSeconds(240));

            assertNull(errorOccurred.get(), "Streaming should not produce errors");
            assertTrue(completionCalled.get(), "Stream should complete successfully");
            assertTrue(received.size() >= 2, "Should receive multiple scenario recommendations, got: " + received.size());
        }
    }
}
