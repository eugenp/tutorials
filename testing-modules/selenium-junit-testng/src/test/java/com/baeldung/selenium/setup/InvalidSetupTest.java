package com.baeldung.selenium.setup;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

final class InvalidSetupTest {

    @Test
    void givenInvalidChromeSetup_whenInit_thenFail() {
        assertThrowsExactly(IllegalStateException.class, ChromeDriver::new);
    }

    @Test
    void givenInvalidFirefoxSetup_whenInit_thenFail() {
        assertThrowsExactly(IllegalStateException.class, FirefoxDriver::new);
    }

    @Test
    void givenInvalidEdgeSetup_whenInit_thenFail() {
        assertThrowsExactly(IllegalStateException.class, EdgeDriver::new);
    }
}