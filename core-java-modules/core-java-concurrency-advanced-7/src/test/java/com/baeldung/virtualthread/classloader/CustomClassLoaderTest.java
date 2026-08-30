package com.baeldung.virtualthread.classloader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import org.junit.jupiter.api.Test;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;

public class CustomClassLoaderTest {

    @Test
    void givenJFRRecIsEnabled_whenVThreadIsBlocked_thenDetectVThreadPinned() throws Exception {
        Path classDir = Paths.get(CustomClassLoader.class.getProtectionDomain()
            .getCodeSource()
            .getLocation()
            .toURI());

        CustomClassLoader loader = new CustomClassLoader(classDir);
        Path file = Path.of("pinning_3.jfr");

        try (Recording recording = new Recording()) {
            recording.enable("jdk.VirtualThreadPinned")
                .withThreshold(Duration.ofMillis(1));
            recording.start();

            Thread th = Thread.ofVirtual()
                .start(() -> {
                    try {
                        Class<?> clazz = Class.forName("com.baeldung.virtualthread.classloader.MyClass",
                            true, loader);

                        System.out.println(Thread.currentThread() + " loaded class : " + clazz.getName());
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                });

            th.join();

            recording.stop();
            recording.dump(file);
        }

        try (RecordingFile rf = new RecordingFile(file)) {
            assertTrue(rf.hasMoreEvents());

            while (rf.hasMoreEvents()) {
                RecordedEvent event = rf.readEvent();

                assertEquals("jdk.VirtualThreadPinned", event.getEventType()
                    .getName());
                assertEquals("Virtual Thread Pinned", event.getEventType()
                    .getLabel());
            }
        }

        Files.delete(file);
    }
}
