package com.baeldung.virtualthread.synchronize;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Map;

import org.junit.jupiter.api.Test;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;

public class CartServiceTest {

    private final CartService cartService = new CartService();

    @Test
    void givenJFRRecIsEnabled_whenVThreadIsBlocked_thenDetectVThreadPinned() throws Exception {
        Path file = Path.of("pinning_4.jfr");

        try (Recording recording = new Recording()) {
            recording.enable("jdk.VirtualThreadPinned")
                .withThreshold(Duration.ofMillis(1));
            recording.start();

            Thread th = Thread.ofVirtual().start(() ->
                cartService.update("test1", 2));

            th.join();

            recording.stop();
            recording.dump(file);
        }

        try (RecordingFile rf = new RecordingFile(file)) {
            assertTrue(rf.hasMoreEvents());

            while (rf.hasMoreEvents()) {
                RecordedEvent event = rf.readEvent();

                System.out.println(event);
                assertEquals("jdk.VirtualThreadPinned", event.getEventType().getName());
                assertEquals("Virtual Thread Pinned", event.getEventType().getLabel());
            }
        }

        Files.delete(file);
    }

    @Test
    void givenProductsIsPresent_whenProductIsAdded_thenProductIsUpdated() throws InterruptedException {
        String productId = "test2";
        Thread th1 = Thread.ofVirtual().start(() ->
            cartService.update(productId, 2));

        Thread th2 = Thread.ofVirtual().start(() ->
            cartService.update(productId, 3));

        th1.join();
        th2.join();

        Map<String, Integer> products = cartService.getProducts();

        assertTrue(products.containsKey(productId));
        assertEquals(5, products.get(productId));
    }

    @Test
    void givenProductIsNotPresent_whenProductIsAdded_thenProductIsUpdated() throws InterruptedException {
        String productId = "test3";
        Thread th = Thread.ofVirtual().start(() ->
            cartService.update(productId, 2));
        th.join();

        Map<String, Integer> products = cartService.getProducts();

        assertTrue(products.containsKey(productId));
        assertEquals(2, products.get(productId));
    }
}
