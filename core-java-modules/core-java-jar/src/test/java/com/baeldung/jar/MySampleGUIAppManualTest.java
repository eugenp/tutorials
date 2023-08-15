package com.baeldung.jar;

import org.junit.jupiter.api.Test;

class MySampleGUIAppManualTest {

    @Test
    void testMain() {
        System.setProperty("java.awt.headless", "true");
        String [] args = null;
        System.exit(0);
        MySampleGUIAppn.main(args);
    }

}