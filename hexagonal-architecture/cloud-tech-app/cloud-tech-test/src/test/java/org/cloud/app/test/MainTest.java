package org.cloud.tech.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.cloud.tech.test.Main.main;
import static org.junit.jupiter.api.Assertions.assertAll;

public class MainTest {

    @BeforeAll
    public static void beforeAll() {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
    }

    @Test
    public void testMain() {
        assertAll(() -> main(null));
    }

}