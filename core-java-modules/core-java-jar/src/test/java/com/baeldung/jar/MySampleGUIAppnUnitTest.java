package com.baeldung.jar;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class MySampleGUIAppnUnitTest {

    @Test
    void testMain() throws IOException {
        System.setProperty("java.awt.headless", "false");
        MySampleGUIAppn instance = new MySampleGUIAppn();
        String [] args = null;
        System.exit(0);
        MySampleGUIAppn.main(args);
    }
}