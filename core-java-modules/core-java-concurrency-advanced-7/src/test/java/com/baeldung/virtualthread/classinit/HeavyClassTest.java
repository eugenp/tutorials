package com.baeldung.virtualthread.classinit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

import org.junit.jupiter.api.Test;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;

public class HeavyClassTest {

    @Test
    void givenJFRIsEnabled_whenVThreadIsBlocked_thenDetectVThreadPinned() throws IOException, InterruptedException {
        Path file = Path.of("pinning_1.jfr");

        try (Recording recording = new Recording()) {
            recording.enable("jdk.VirtualThreadPinned")
                .withThreshold(Duration.ofMillis(1));
            recording.start();

            Thread th = Thread.ofVirtual()
                .start(HeavyClass::new);
            th.join();

            recording.stop();
            recording.dump(file);
        }

        try (RecordingFile rf = new RecordingFile(file)) {
            assertTrue(rf.hasMoreEvents());

            while (rf.hasMoreEvents()) {
                RecordedEvent event = rf.readEvent();
                System.out.println(event);
                assertEquals("jdk.VirtualThreadPinned", event.getEventType()
                    .getName());
                assertEquals("Virtual Thread Pinned", event.getEventType()
                    .getLabel());
            }
        }

        Files.delete(file);
    }
}
