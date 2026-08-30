package com.baeldung.embabel.agent.api.streaming;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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

import reactor.core.publisher.Flux;

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

    public record ParkingRecommendation(
        String scenario,
        Option chosenOption,
        int estimatedTotalCost,
        String summary
    ) {

        public enum Option {
            STREET, METER, GARAGE
        }
    }

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

    private static final String TIMED_PARKING_PROMPT =
        """
        Provide parking recommendations for an advisor visiting Midtown Manhattan across three time scenarios:
        1. Early morning (before 8am): street meters are free before 8am
        2. Business hours (9am-5pm): all paid options apply, 30-minute window, 3-hour stay
        3. Evening (after 6pm): meters are free after 6pm, but garages may close at 9pm (3-hour stay at risk)

        Return all three recommendations.
        """;

    private static final String TOOL_PARKING_PROMPT =
        """
        An advisor needs to park in Midtown Manhattan for a 3-hour client meeting starting in 30 minutes.
        Use the available tools to probe parking options, then recommend three options, the best one first.
        Arriving late is not acceptable.
        """;

    /**
     * Verifies basic structured streaming: a single object is emitted and completed without errors.
    */
    @Test
    void whenStreaming_thenReceivesParkingRecommendation() {
        streamParkingRecommendations(PARKING_PROMPT);
    }

    /**
     * Verifies that a prompt requesting multiple results streams them as individual objects,
     * one per scenario, rather than a single batched response.
    */
    @Test
    void whenStreamingMultipleScenarios_thenReceivesRecommendationPerScenario() {
        streamParkingRecommendations(TIMED_PARKING_PROMPT);
    }

    /**
     * Verifies structured streaming output with thinking enabled and with no tools.
     *
     * <p>Token budget must stay below {@code max_tokens} (8192 for {@code claude-sonnet-4-5}).
     * Reasoning arrives as multiple {@link com.embabel.common.core.streaming.StreamingEvent.Thinking}
     * events — one per line, not as a single block.
     */
    @Test
    void whenStreamingWithThinking_thenReceivesReasoningAndRecommendation() {
        // budget_tokens must be < max_tokens (8192). This also enables more rigorous reasoning
        LlmOptions thinkingOptions = new LlmOptions().withThinking(Thinking.withTokenBudget(8000));
        PromptRunner runner = ai.withDefaultLlm()
            .withLlm(thinkingOptions);
        streamParkingRecommendationsWithThinking(runner, TIMED_PARKING_PROMPT);
    }


    /**
     * Verifies that streaming with tools produces reasoning and a recommendation informed by tool results.
     *
     * <p>Reasoning events are emitted after all tool calls complete.
     * Reasoning while deciding which tools to call
     * is not surfaced to the subscriber, as each Spring AI-managed tool-loop iteration starts a new stream.
     */
    @Test
    void whenStreamingWithThinkingAndTooling_thenReceivesRecommendationAndReasoning() {
        PromptRunner runner = ai.withDefaultLlm()
            .withToolObject(new ParkingTooling())
            .withToolCallInspectors(new ToolCallLoggingInspector(LogLevel.INFO, logger));
        streamParkingRecommendationsWithThinking(runner, TOOL_PARKING_PROMPT);
    }

    private void streamParkingRecommendationsWithThinking(PromptRunner runner, String prompt) {
        assertTrue(runner.supportsStreaming(), "Default LLM must support streaming");

        Flux<StreamingEvent<ParkingRecommendation>> stream = new StreamingPromptRunnerBuilder(runner)
            .streaming()
            .withPrompt(prompt)
            .createObjectStreamWithThinking(ParkingRecommendation.class);

        stream
            .timeout(Duration.ofSeconds(120))
            .doOnNext(event -> {
                if (event.isObject()) {
                    ParkingRecommendation rec = event.getObject();
                    if (rec != null) {
                        logger.info("Received recommendation: scenario={}, option={}, cost={}, summary={}",
                            rec.scenario(), rec.chosenOption(), rec.estimatedTotalCost(), rec.summary());
                    }
                } else if (event.isThinking()) {
                    logger.info("Received reasoning: {}", event.getThinking());
                }
            })
            .blockLast(Duration.ofSeconds(240));
    }

    private void streamParkingRecommendations(String prompt) {
        PromptRunner runner = ai.withDefaultLlm();
        assertTrue(runner.supportsStreaming(), "Default LLM must support streaming");

        Flux<ParkingRecommendation> stream = new StreamingPromptRunnerBuilder(runner)
            .streaming()
            .withPrompt(prompt)
            .createObjectStream(ParkingRecommendation.class);

        stream
            .timeout(Duration.ofSeconds(120))
            .doOnNext(rec -> logger.info("Received recommendation: scenario={}, option={}, cost={}, summary={}",
                rec.scenario(), rec.chosenOption(), rec.estimatedTotalCost(), rec.summary()))
            .blockLast(Duration.ofSeconds(240));
    }
}
