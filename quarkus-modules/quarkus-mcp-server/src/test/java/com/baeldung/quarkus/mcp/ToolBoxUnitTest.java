package com.baeldung.quarkus.mcp;

import org.junit.jupiter.api.Test;

import com.baeldung.quarkus.mcp.ToolBox;

import static org.junit.jupiter.api.Assertions.*;

import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;

public class ToolBoxUnitTest {

    private final ToolBox toolBox = new ToolBox();

    @Test
    void givenValidTimezone_whenGetTimeInTimezone_thenContainsFormattedDate() {
        String timezoneId = "America/Los_Angeles";
        String result = toolBox.getTimeInTimezone(timezoneId);
        // Should contain the timezone's display name or a recognizable part of the formatted date
        assertTrue(result.contains("Pacific") || result.contains("Los Angeles") || result.contains(", 20"),
            "Result should contain formatted date for the timezone");
    }

    @Test
    void givenInvalidTimezone_whenGetTimeInTimezone_thenReturnsInvalidTimezoneMessage() {
        String timezoneId = "Invalid/Timezone";
        String result = toolBox.getTimeInTimezone(timezoneId);
        assertTrue(result.startsWith("Invalid timezone ID"));
    }

    @Test
    void givenJVM_whenGetSystemInfo_thenContainsExpectedFields() {
        String result = toolBox.getJVMInfo();
        assertTrue(result.contains("Available processors"));
        assertTrue(result.contains("Free memory"));
        assertTrue(result.contains("Total memory"));
        assertTrue(result.contains("Max memory"));
    }
}
