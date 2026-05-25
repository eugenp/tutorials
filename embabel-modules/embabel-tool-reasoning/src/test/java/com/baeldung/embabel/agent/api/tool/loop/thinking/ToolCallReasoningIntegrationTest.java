/*
 *
 *
 * Example got pattened after:
 *
 * <link>https://github.com/embabel/embabel-agent-experimental/blob/main/embabel-experimental-integration-tests/src/test/java/com/embabel/agent/api/tool/loop/thinking/ToolCallReasoningIT.java</link>
 *
 * Original code (see link above)) was developed by Embabel Pty Ltd, 2026
 */
package com.baeldung.embabel.agent.api.tool.loop.thinking;

import com.embabel.agent.AgentTestApplication;
import com.embabel.agent.api.annotation.LlmTool;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.tool.callback.*;
import com.embabel.chat.Message;
import com.embabel.chat.SystemMessage;
import com.embabel.common.core.thinking.ThinkingResponse;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration test demonstrating DefaultToolLoop with inspectors and transformers.
 * <p>
 * Shows how to:
 * - Create tools that fetch real data (restaurant menus via Jsoup)
 * - Use ToolLoopInspector for observability
 * - Use ToolLoopTransformer for System Message positioning
 * - Have LLM use tools and summarize results
 */
@SpringBootTest(
        classes = AgentTestApplication.class,
        properties = {
                "embabel.models.cheapest=gpt-4.1-mini",
                "embabel.models.best=gpt-4.1-mini",
                "embabel.models.default-llm=gpt-4.1-mini",
                "embabel.agent.platform.llm-operations.prompts.defaultTimeout=240s",
                "embabel.agent.platform.llm-operations.data-binding.fixedBackoffMillis=6000"
        }
)
@ActiveProfiles("tool-reasoning")
class ToolCallReasoningIntegrationTest {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Ai ai;

    @BeforeAll
    static void setUp() {
        System.setProperty("embabel.agent.shell.interactive.enabled", "false");
    }

    public record ParkingRecommendation(
            Option chosenOption,          // which option was selected
            String location,              // e.g. "Midtown Manhattan"
            int estimatedTotalCost,       // total expected cost
            String summary                // short human-readable explanation
    ) {

        public enum Option {
            STREET,
            METER,
            GARAGE
        }
    }

    /**
     * Extract tool names from a tooling class using reflection.
     * Finds all methods annotated with @LlmTool.
     */
    private static List<String> extractToolNames() {
        return Arrays.stream(ParkingTooling.class.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(LlmTool.class))
                .map(Method::getName)
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Extracts attributes in [key=value] format from thinking block content.
     *
     * <p>This utility parses thinking blocks for structured attributes embedded in the text.
     * Attributes follow the format: [key=value]
     *
     * <p><b>Usage Example:</b>
     * <pre>{@code
     * // Get thinking block from LLM response
     * ThinkingResponse<ParkingRecommendation> result = ai.thinking().createObject(...);
     * ThinkingBlock block = result.getThinkingBlocks().get(0);
     *
     * // Extract attributes
     * Map<String, String> attributes = extractAttributes(block.getContent());
     *
     * }</pre>
     *
     * <p><b>Input Example:</b>
     * <pre>
     * "Garage parking is best due to time constraints [confidence=0.9] and [priority=high]"
     * </pre>
     *
     * <p><b>Output:</b>
     * <pre>
     * Map: {"confidence" -> "0.9", "priority" -> "high"}
     * </pre>
     *
     * @param thinkingContent the thinking block content containing [key=value] pairs
     * @return map of extracted key-value pairs, empty if no matches found
     */
    private static Map<String, String> extractAttributes(String thinkingContent) {
        Map<String, String> attributes = new HashMap<>();
        // Pattern: [key=value] where key and value don't contain = or ]
        Pattern pattern = Pattern.compile("\\[([^=]+)=([^\\]]+)\\]");
        Matcher matcher = pattern.matcher(thinkingContent);

        while (matcher.find()) {
            String key = matcher.group(1).trim();
            String value = matcher.group(2).trim();
            attributes.put(key, value);
        }

        return attributes;
    }

    /**
     *  Parking Finder Tooling
     */
    public class ParkingTooling {

        private final Random random = new Random();

        @LlmTool(description = "Find free street parking. Uncertain and may take time.")
        public String findStreetParking(String location, int maxMinutes) {

            boolean found = random.nextDouble() < 0.3; // low probability

            if (found) {
                return "Street parking found near " + location + " (free)";
            }
            return "No street parking found within " + maxMinutes + " minutes";
        }

        @LlmTool(description = "Find metered parking. Moderate cost and moderate availability. May have time limits.")
        public String findMeterParking(String location, int maxMinutes) {

            boolean found = random.nextDouble() < 0.6; // medium probability

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


    /**
     * Test with multiple tool probes to verify thinking blocks accumulate correctly
     * across multiple tool calls (either in same iteration or across iterations).
     * <p>
     * This test investigates:
     * - Whether multiple tools are called in the same iteration or separate iterations
     * - Whether multiple tool calls in the same iteration share the same AssistantMessage
     * - Whether thinking blocks accumulate correctly in both scenarios
     */
    @Test
    void parkingDecisionMakerWithMultiProbes() {
        // Create Parking Options Tool
        var tools = new ParkingTooling();
        var loggingInspector = createLoggingInspector();
        var callbackTracker = new CallbackTracker();

        var systemMessageTransformer = new SystemMessageTransformer(
                "You are a helpful decision assistant. Be concise and practical.",
                """
                                CRITICAL WORKFLOW - Two-phase decision process:
                        
                                === PHASE 1: Tool Selection (First Response) ===
                        
                                1. For EACH tool you plan to call, emit a SEPARATE <tool_use_reasoning> block:

                                    <tool_use_reasoning>
                                    Tool: [tool=TOOL_NAME]
                                    Why THIS tool: [explain why this specific tool is needed]
                                    Information expected: [what this tool will reveal]
                                    Advantage over alternatives: [why this tool vs others]
                                    Confidence in this tool selection [confidence=0.XX]
                                    </tool_use_reasoning>

                                    IMPORTANT: Keep the brackets! Example: "Tool: [tool=myTool]" not "Tool: myTool"

                                    Since you must call at least 2 tools, you must emit at least 2 separate blocks.
                        
                                2. Call AT LEAST TWO tools to gather comprehensive information
                                    - You MUST call at least 2 tools to probe different aspects.
                                    - Tools are PROBES for information gathering, not final decisions.
                                    - Multiple probes provide better decision quality.
                        
                                === PHASE 2: Final Decision (After receiving tool results) ===
                        
                                1. Emit final decision reasoning:
                                    <final_decision_reasoning>
                                    Explain:
                                    - What each tool probe revealed
                                    - How the probe results informed your analysis
                                    - Why you chose this option based on probe data and constraints
                                    - Confidence in final recommendation in format [confidence=0.XX]
                                    </final_decision_reasoning>
                        
                                2. Then provide the final structured output
                                    - Your final recommendation should synthesize insights from multiple probes.
                                    - Never copy reasoning blocks into the final structured object.
                        
                                REMINDER: One <tool_use_reasoning> block per tool call. At least 2 tools = at least 2 blocks. Emit reasoning in BOTH phases.
                        """
        );

        String prompt = """
                
                          Scenario:
                          An advisor is driving to a client meeting in Midtown Manhattan.
                
                          Constraints:
                          - 30 minutes remain before the meeting starts
                          - arriving late is not acceptable
                          - the meeting is expected to last about 3 hours
                
                          Parking options:
                          - Street parking: free, but uncertain
                          - Metered parking: $5 per hour, typically limited to 2 hours
                          - Garage parking: $30 per hour, guaranteed availability
                
                          Important decision factors:
                          - available time before the meeting
                          - risk of arriving late
                          - trade-offs between street, metered, and garage parking
                
                          Recommend the best parking option.
                
                          Available tools: %s
                
                
                """.formatted(String.join(", ", extractToolNames()));


        long start = System.currentTimeMillis();
        ThinkingResponse<ParkingRecommendation> result = ai.withDefaultLlm()
                .withToolObject(tools)
                .withToolLoopInspectors(callbackTracker, loggingInspector)
                .withToolLoopTransformers(systemMessageTransformer)
                .thinking().createObject(prompt, ParkingRecommendation.class);
        long elapsed = System.currentTimeMillis() - start;

        logger.info("""
                        
                        ========== RESULT ({} ms) ==========
                        Recommended: {}
                        Reasoning: {}
                        
                        Callback stats:
                          beforeLlmCall: {}
                          afterLlmCall: {}
                          afterToolResult: {}
                        
                        """,
                elapsed,
                result.getResult(),
                result.getThinkingBlocks(),
                callbackTracker.beforeLlmCallCount.get(),
                callbackTracker.afterLlmCallCount.get(),
                callbackTracker.afterToolResultCount.get()
        );

        // Assertions

        // Verify at least 2 tools were called
        assertTrue(callbackTracker.toolsInvoked.size() >= 2,
                "Should invoke at least 2 tools for comprehensive probing, invoked: " + callbackTracker.toolsInvoked);
        logger.info("Tools invoked: {}", callbackTracker.toolsInvoked);

        // Verify thinking blocks were accumulated
        assertFalse(result.getThinkingBlocks().isEmpty(),
                "Should have accumulated thinking blocks");

        // Log analysis of tool call pattern
        int totalIterations = callbackTracker.beforeLlmCallCount.get();
        int toolsCalled = callbackTracker.toolsInvoked.size();
        int toolResultCallbacks = callbackTracker.afterToolResultCount.get();

        logger.info("=== TOOL CALL PATTERN ANALYSIS ===");
        logger.info("Total LLM iterations: {}", totalIterations);
        logger.info("Total tools called: {}", toolsCalled);
        logger.info("Tool result callbacks: {}", toolResultCallbacks);

        if (toolResultCallbacks == toolsCalled && totalIterations < toolsCalled + 1) {
            logger.info("PATTERN: Multiple tools called in SAME iteration (parallel tool calls)");
        } else if (totalIterations >= toolsCalled) {
            logger.info("PATTERN: Tools called across SEPARATE iterations (sequential tool calls)");
        }

        logger.info("Thinking blocks captured: {}", result.getThinkingBlocks().size());
        for (int i = 0; i < result.getThinkingBlocks().size(); i++) {
            var block = result.getThinkingBlocks().get(i);
            logger.info("  Block {}: tagType={}, tagValue={}, contentLength={}",
                    i + 1, block.getTagType(), block.getTagValue(), block.getContent().length());

            // Extract and validate attributes as Map
            Map<String, String> attributes = extractAttributes(block.getContent());
            if (!attributes.isEmpty()) {
                logger.info("    Attributes: {}", attributes);

                // Validate and log tool attribute
                if (block.getTagValue().equals("tool_use_reasoning")) {
                    if (attributes.containsKey("tool")) {
                        logger.info("      Tool: {}", attributes.get("tool"));
                    } else {
                        logger.warn("      Missing [tool=...] attribute in tool_use_reasoning block");
                    }
                }

                // Validate and log confidence attribute
                if (attributes.containsKey("confidence")) {
                    String confidence = attributes.get("confidence");
                    logger.info("      Confidence: {}", confidence);
                    try {
                        double confidenceValue = Double.parseDouble(confidence);
                        if (confidenceValue < 0.0 || confidenceValue > 1.0) {
                            logger.warn("      Confidence value {} is out of range [0.0, 1.0]", confidenceValue);
                        }
                    } catch (NumberFormatException e) {
                        logger.warn("      Invalid confidence value: {}", confidence);
                    }
                } else {
                    logger.warn("      Missing [confidence=...] attribute in block");
                }
            } else {
                logger.warn("    No attributes found in block (tagValue={})", block.getTagValue());
            }
        }
    }


    /**
     * Transformer that adds system messages after existing system messages but before user messages.
     */
    static class SystemMessageTransformer implements ToolLoopTransformer {
        private final List<String> systemMessages;
        private final Logger logger = LoggerFactory.getLogger(getClass());

        SystemMessageTransformer(List<String> systemMessages) {
            this.systemMessages = systemMessages;
        }

        SystemMessageTransformer(String... systemMessages) {
            this.systemMessages = List.of(systemMessages);
        }

        @NotNull
        @Override
        public List<Message> transformBeforeLlmCall(@NotNull BeforeLlmCallContext context) {
            logger.info("Adding {} system message(s) before LLM call (iteration {})",
                    systemMessages.size(), context.getIteration());
            var history = new ArrayList<>(context.getHistory());

            // Find the last SystemMessage index
            int lastSystemMessageIndex = -1;
            for (int i = 0; i < history.size(); i++) {
                if (history.get(i) instanceof SystemMessage) {
                    lastSystemMessageIndex = i;
                }
            }

            // Insert after last SystemMessage, or at beginning if none exist
            int insertIndex = lastSystemMessageIndex + 1;
            for (String content : systemMessages) {
                history.add(insertIndex++, new SystemMessage(content));
            }

            return history;
        }
    }

    /**
     * Custom inspector that tracks callback invocations for testing.
     */
    static class CallbackTracker implements ToolLoopInspector {
        final AtomicInteger beforeLlmCallCount = new AtomicInteger();
        final AtomicInteger afterLlmCallCount = new AtomicInteger();
        final AtomicInteger afterToolResultCount = new AtomicInteger();
        final List<String> toolsInvoked = new ArrayList<>();

        protected final Logger logger = LoggerFactory.getLogger(getClass());

        @Override
        public void beforeLlmCall(@NotNull BeforeLlmCallContext context) {
            beforeLlmCallCount.incrementAndGet();
            var threadName = Thread.currentThread().getName();
            logger.info("Before LLM Call Thread {}", threadName);
        }

        @Override
        public void afterLlmCall(@NotNull AfterLlmCallContext context) {
            afterLlmCallCount.incrementAndGet();
        }

        @Override
        public void afterToolResult(@NotNull AfterToolResultContext context) {
            afterToolResultCount.incrementAndGet();
            synchronized (toolsInvoked) {
                toolsInvoked.add(context.getToolCall().getName());
            }
        }
    }

    /**
     * Create a logging inspector with INFO level.
     */
    protected ToolLoopLoggingInspector createLoggingInspector() {
        return new ToolLoopLoggingInspector(
                LogLevel.INFO,
                LoggerFactory.getLogger(ToolLoopLoggingInspector.class)
        );
    }


}
