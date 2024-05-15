package com.baeldung.coverageaggregation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MyServiceUnitTest {

    MyService myService = new MyService();
    
    @Test
    void whenUnitTestedOnly_thenCorrectText() {
        assertEquals("unit tested only", myService.unitTestedOnly());
    }

    @Test
    void whenTestedMethod_thenCorrectText() {
        assertEquals("covered by unit and integration tests", myService.coveredByUnitAndIntegrationTests());
    }

}