package com.baeldung.hollow.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.baeldung.hollow.model.MonitoringEvent;

public class MonitoringDataService {

    private final Random random = new Random();

    private final String[] devices = new String[] {
        "device-001", "device-002", "device-003", "device-004", "device-005"
    };

    private final String[] eventTypes = new String[] {
        "CPU_USAGE", "MEMORY_USAGE", "APPLICATION_AVAILABILITY", "DISK_IO", "NETWORK_THROUGHPUT"
    };

    /**
     * Retrieve a random list of MonitoringEvent instances (between 5 and 20 events).
     */
    public List<MonitoringEvent> retrieveEvents() {
        int count = 5 + random.nextInt(16); // 5..20 events
        List<MonitoringEvent> events = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            String id = UUID.randomUUID().toString();
            String device = devices[random.nextInt(devices.length)];
            String type = eventTypes[random.nextInt(eventTypes.length)];
            double value = generateValueForType(type);
            long timestamp = System.currentTimeMillis();

            MonitoringEvent evt = buildMonitoringEvent(id, device, type, value, timestamp);
            if (evt != null) {
                events.add(evt);
            }
        }

        return events;
    }

    private double generateValueForType(String type) {
        switch (type) {
            case "CPU_USAGE":
                return Math.round(random.nextDouble() * 10000.0) / 100.0; // 0.00 - 100.00 percent
            case "MEMORY_USAGE":
                return Math.round((random.nextDouble() * 32_000) * 100.0) / 100.0; // MB, up to ~32GB
            case "APPLICATION_AVAILABILITY":
                return random.nextBoolean() ? 1.0 : 0.0; // 1 = up, 0 = down
            case "DISK_IO":
                return Math.round((random.nextDouble() * 5000) * 100.0) / 100.0; // IOPS-ish
            case "NETWORK_THROUGHPUT":
                return Math.round((random.nextDouble() * 1000) * 100.0) / 100.0; // Mbps
            default:
                return random.nextDouble() * 100.0;
        }
    }

    /**
     * Build a MonitoringEvent instance using direct setters on the MonitoringEvent POJO.
     */
    private MonitoringEvent buildMonitoringEvent(String id, String device, String type, double value, long timestamp) {
        try {
            MonitoringEvent evt = new MonitoringEvent();

            // Map generated values to the MonitoringEvent fields
            evt.setEventId(random.nextInt(10_000));
            evt.setDeviceId(device);
            evt.setEventType(type);

            // Use a human-friendly event name
            String name = type + "-" + id.substring(0, 8);
            evt.setEventName(name);

            // creationDate as ISO-8601 string
            evt.setCreationDate(Instant.ofEpochMilli(timestamp).toString());

            // status: for availability events, use UP/DOWN; otherwise store numeric value as string
            if ("APPLICATION_AVAILABILITY".equals(type)) {
                evt.setStatus(value == 1.0 ? "UP" : "DOWN");
            } else {
                evt.setStatus(String.format("%.2f", value));
            }

            return evt;
        } catch (Exception e) {
            // In case of unexpected error, return null (caller will skip)
            return null;
        }
    }

}
