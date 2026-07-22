package com.baeldung.virtualthread.synchronize.fixed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Map;

import org.junit.jupiter.api.Test;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordingFile;

public class CartServiceTest {

    private final CartService cartService = new CartService();

    @Test
    void givenJFRIsEnabled_whenVThreadIsBlocked_thenDetectVirtualThreadPinned() throws Exception {
        Path file = Path.of("no-pinning.jfr");

        try (Recording recording = new Recording()) {
            recording.enable("jdk.VirtualThreadPinned")
                .withThreshold(Duration.ofMillis(1));
            recording.start();

            Thread th1 = Thread.ofVirtual().start(() ->
                cartService.update("test1", 2));

            Thread th2 = Thread.ofVirtual().start(() ->
                cartService.update("test1", 3));

            th1.join();
            th2.join();

            recording.stop();
            recording.dump(file);
        }

        try (RecordingFile rf = new RecordingFile(file)) {
            assertFalse(rf.hasMoreEvents());
        }

        Files.delete(file);
    }

    @Test
    void givenProductsIsPresent_whenProductIsAdded_thenProductIsUpdated() throws InterruptedException {
        String productId = "test4";
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
    void givenProductIsNotPresent_whenProductIsAdded_thenProductIsUpdate() throws InterruptedException {
        String productId = "test5";
        Thread th = Thread.ofVirtual().start(() ->
            cartService.update(productId, 2));
        th.join();

        Map<String, Integer> products = cartService.getProducts();

        assertTrue(products.containsKey(productId));
        assertEquals(2, products.get(productId));
    }
}
