package com.baeldung;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FailsafeBuildPhaseIntegrationTest {

    @Test
    public void whenTestExecutes_thenPreAndPostIntegrationBuildPhasesAreExecuted() {
        assertTrue(true);
    }
}
