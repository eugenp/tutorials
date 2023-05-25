package com.baeldung.scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

class NextVsNextLineUnitTest {

    @Test
    void givenInput_whenUsingNextMethod_thenReturnToken() {
        String input = "Hello world";
        try (Scanner scanner = new Scanner(input)) {
            assertEquals("Hello", scanner.next());
            assertEquals("world", scanner.next());
        }
    }

    @Test
    void givenInput_whenUsingNextMethodWithCustomDelimiter_thenReturnToken() {
        String input = "Hello :world";
        try (Scanner scanner = new Scanner(input)) {
            scanner.useDelimiter(":");

            assertEquals("Hello ", scanner.next());
            assertEquals("world", scanner.next());
        }
    }

    @Test
    void givenInput_whenUsingNextLineMethod_thenReturnEntireLine() {
        String input = "Hello world\nWelcome to baeldung.com";
        try (Scanner scanner = new Scanner(input)) {
            assertEquals("Hello world", scanner.nextLine());
            assertEquals("Welcome to baeldung.com", scanner.nextLine());
        }
    }

    @Test
    void givenInput_whenUsingNextLineWithCustomDelimiter_thenIgnoreDelimiter() {
        String input = "Hello:world\nWelcome:to baeldung.com";
        try (Scanner scanner = new Scanner(input)) {
            scanner.useDelimiter(":");

            assertEquals("Hello:world", scanner.nextLine());
            assertEquals("Welcome:to baeldung.com", scanner.nextLine());
        }
    }

}
