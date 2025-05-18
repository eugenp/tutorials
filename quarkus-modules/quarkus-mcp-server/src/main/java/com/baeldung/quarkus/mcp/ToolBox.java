package com.baeldung.quarkus.mcp;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;

public class ToolBox {

    @Tool(description = "Get the current time in a specific timezone.")
    public String getTimeInTimezone(
            @ToolArg(description = "Timezone ID (e.g., America/Los_Angeles)") String timezoneId) {
        try {
            ZoneId zoneId = ZoneId.of(timezoneId);
            ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(java.time.format.FormatStyle.FULL)
                    .withLocale(Locale.getDefault());
            return zonedDateTime.format(formatter);
        } catch (Exception e) {
            return "Invalid timezone ID: " + timezoneId + ". Please provide a valid IANA timezone ID.";
        }
    }

    @Tool(description = "Provides system information such as available processors, free memory, total memory, and max memory.")
    public String getSystemInfo() {
        StringBuilder systemInfo = new StringBuilder();

        // Get available processors
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        systemInfo.append("Available processors (cores): ").append(availableProcessors).append("\n");

        // Get free memory
        long freeMemory = Runtime.getRuntime().freeMemory();
        systemInfo.append("Free memory (bytes): ").append(freeMemory).append("\n");

        // Get total memory
        long totalMemory = Runtime.getRuntime().totalMemory();
        systemInfo.append("Total memory (bytes): ").append(totalMemory).append("\n");

        // Get max memory
        long maxMemory = Runtime.getRuntime().maxMemory();
        systemInfo.append("Max memory (bytes): ").append(maxMemory).append("\n");
        return systemInfo.toString();
    }


}