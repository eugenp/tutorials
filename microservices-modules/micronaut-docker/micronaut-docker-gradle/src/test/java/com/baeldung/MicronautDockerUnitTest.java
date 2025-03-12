package com.baeldung;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class MicronautDockerUnitTest {

    Application application = new Application();

    @Test
    void testItWorks() {
        Application.main(new String[]{});
        Assertions.assertTrue(application.isRunning());
    }

}
