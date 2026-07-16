##  ENVIRONMENT SETUP
```
OPENAI_API_KEY=<your-key>
```

## TEST EXECUTION

```bash
 mvn test -Dtest=ToolCallReasoningIntegrationTest

```

## SAMPLE OUTPUT

```bash
15:07:21.793 [main] INFO  ToolCallReasoningIntegrationTest - 
========== RESULT (11979 ms) ==========
Recommended: ParkingRecommendation[chosenOption=GARAGE, location=Midtown Manhattan, estimatedTotalCost=90, summary=No feasible street or metered parking was found within the required time and duration constraints. Garage parking was reserved to guarantee on-time arrival and accommodate the 3-hour meeting despite higher cost.]
Reasoning: [ThinkingBlock(content=Tool: [tool=functions.findStreetParking]
Why THIS tool: To explore the availability and location of free street parking near the client meeting area.
Information expected: The real-time availability or likelihood of securing street parking within required proximity and time.
Advantage over alternatives: Street parking is the lowest cost option but uncertain; assessing its availability directly helps gauge risk of delay.
Confidence in this tool selection [confidence=0.85], tagType=TAG, tagValue=tool_use_reasoning), ThinkingBlock(content=Tool: [tool=functions.findMeterParking]
Why THIS tool: To check for metered parking availability for suitable duration nearby, as it offers moderate cost and may partially fit time needs.
Information expected: Metered parking availability, pricing, and time limits relevant to the 3-hour meeting.
Advantage over alternatives: It balances cost and reliability better than street parking, useful for moderate risk tolerance.
Confidence in this tool selection [confidence=0.9], tagType=TAG, tagValue=tool_use_reasoning), ThinkingBlock(content=Tool: [tool=functions.reserveGarage]
Why THIS tool: Since street and metered parking are unavailable or impractical given the time constraint and duration, reserving guaranteed garage parking is a reliable fallback.
Information expected: Confirmation of garage parking availability and reservation to ensure on-time arrival with minimal risk.
Advantage over alternatives: Eliminates uncertainty and risk of being late by guaranteeing parking close to the meeting.
Confidence in this tool selection [confidence=0.95], tagType=TAG, tagValue=tool_use_reasoning), ThinkingBlock(content=The probe for street parking revealed no available spots within a feasible 30-minute timeframe, making it too risky given the tight schedule and meeting start time. Metered parking was also found unavailable very quickly nearby, plus it imposes a 2-hour limit which is insufficient for the 3-hour meeting. These results demonstrate that free and moderate-cost options are either not accessible or impractical due to time and duration constraints. Given these factors, reserving the garage parking emerged as the best alternative, offering guaranteed availability and eliminating the risk of lateness. Although it is the most expensive option ($30 per hour), it aligns best with the client's priority of punctuality and the meeting's long duration. Confidence in this recommendation is high [confidence=0.95]., tagType=TAG, tagValue=final_decision_reasoning)]

Callback stats:
  beforeLlmCall: 3
  afterLlmCall: 3
  afterToolResult: 3


15:07:21.797 [main] INFO  ToolCallReasoningIntegrationTest - Tools invoked: [findStreetParking, findMeterParking, reserveGarage]
15:07:21.797 [main] INFO  ToolCallReasoningIntegrationTest - === TOOL CALL PATTERN ANALYSIS ===
15:07:21.797 [main] INFO  ToolCallReasoningIntegrationTest - Total LLM iterations: 3
15:07:21.797 [main] INFO  ToolCallReasoningIntegrationTest - Total tools called: 3
15:07:21.797 [main] INFO  ToolCallReasoningIntegrationTest - Tool result callbacks: 3
15:07:21.797 [main] INFO  ToolCallReasoningIntegrationTest - PATTERN: Multiple tools called in SAME iteration (parallel tool calls)
15:07:21.797 [main] INFO  ToolCallReasoningIntegrationTest - Thinking blocks captured: 4
15:07:21.797 [main] INFO  ToolCallReasoningIntegrationTest -   Block 1: tagType=TAG, tagValue=tool_use_reasoning, contentLength=475
15:07:21.798 [main] INFO  ToolCallReasoningIntegrationTest -     Attributes: {confidence=0.85, tool=functions.findStreetParking}
15:07:21.798 [main] INFO  ToolCallReasoningIntegrationTest -       Tool: functions.findStreetParking
15:07:21.798 [main] INFO  ToolCallReasoningIntegrationTest -       Confidence: 0.85
15:07:21.798 [main] INFO  ToolCallReasoningIntegrationTest -   Block 2: tagType=TAG, tagValue=tool_use_reasoning, contentLength=473
15:07:21.798 [main] INFO  ToolCallReasoningIntegrationTest -     Attributes: {confidence=0.9, tool=functions.findMeterParking}
15:07:21.798 [main] INFO  ToolCallReasoningIntegrationTest -       Tool: functions.findMeterParking
15:07:21.798 [main] INFO  ToolCallReasoningIntegrationTest -       Confidence: 0.9
15:07:21.798 [main] INFO  ToolCallReasoningIntegrationTest -   Block 3: tagType=TAG, tagValue=tool_use_reasoning, contentLength=515
15:07:21.798 [main] INFO  ToolCallReasoningIntegrationTest -     Attributes: {confidence=0.95, tool=functions.reserveGarage}
15:07:21.798 [main] INFO  ToolCallReasoningIntegrationTest -       Tool: functions.reserveGarage
15:07:21.798 [main] INFO  ToolCallReasoningIntegrationTest -       Confidence: 0.95
15:07:21.798 [main] INFO  ToolCallReasoningIntegrationTest -   Block 4: tagType=TAG, tagValue=final_decision_reasoning, contentLength=808
15:07:21.798 [main] INFO  ToolCallReasoningIntegrationTest -     Attributes: {confidence=0.95}
15:07:21.798 [main] INFO  ToolCallReasoningIntegrationTest -       Confidence: 0.95
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 16.30 s -- in com.baeldung.embabel.agent.api.tool.loop.thinking.ToolCallReasoningIntegrationTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0


```