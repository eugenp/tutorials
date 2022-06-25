package com.baeldung.jar;

import org.junit.jupiter.api.Test;

class MySampleGUIAppnUnitTest extends MySampleGUIAppn {

    @Test
    void testMain() throws IOException {
        MySampleGUIAppn instance = new MySampleGUIAppn();
        String [] args = null;
        System.exit(DO_NOTHING_ON_CLOSE);
        main(args);
    }
}