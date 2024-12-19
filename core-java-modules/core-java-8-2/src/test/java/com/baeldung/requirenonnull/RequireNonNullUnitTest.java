package com.baeldung.requirenonnull;

import java.time.Instant;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RequireNonNullUnitTest {

    void greet(String name) {
        Objects.requireNonNull(name, "Name cannot be null");
        System.out.println("Hello, " + name + "!");
    }

    @Test
    void givenObject_whenGreet_thenNoException() {
        Assertions.assertDoesNotThrow(() -> greet("Baeldung"));
    }

    @Test
    void givenNull_whenGreet_thenException() {
        Assertions.assertThrows(NullPointerException.class, () -> greet(null));
    }

    static class User {

        private final String username;
        private final String password;

        public User(String username, String password) {
            this.username = Objects.requireNonNull(username, "Username is null!");
            this.password = Objects.requireNonNull(password, "Password is null!");
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    @Test
    void givenValidInput_whenNewUser_thenNoException() {
        Assertions.assertDoesNotThrow(() -> new User("Baeldung", "Secret"));
    }

    @Test
    void givenNull_whenNewUser_thenException() {
        Assertions.assertThrows(NullPointerException.class, () -> new User(null, "Secret"));
        Assertions.assertThrows(NullPointerException.class, () -> new User("Baeldung", null));
    }

    void processOrder(UUID orderId) {
        Objects.requireNonNull(orderId, () -> {
            String message = "Order ID cannot be null! Current timestamp: " + getProcessTimestamp();
            message = message.concat("Total number of invalid orders: " + getOrderAmount());
            message = message.concat("Please provide a valid order.");
            return message;
        });
        System.out.println("Processing order with id: " + orderId);
    }

    private static int getOrderAmount() {
        return new Random().nextInt(100_000);
    }

    private static Instant getProcessTimestamp() {
        return Instant.now();
    }

    @Test
    void givenObject_whenProcessOrder_thenNoException() {
        Assertions.assertDoesNotThrow(() -> processOrder(UUID.randomUUID()));
    }

    @Test
    void givenNull_whenProcessOrder_thenException() {
        Assertions.assertThrows(NullPointerException.class, () -> processOrder(null));
    }
}
