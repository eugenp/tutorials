package com.baeldung.jar;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class MySampleGUIAppnUnitTest {

    @Test
    void testMain() throws IOException {
        System.setProperty("java.awt.headless", "true");
        String [] args = null;
        System.exit(0);
        MySampleGUIAppn.main(args);
    }
}