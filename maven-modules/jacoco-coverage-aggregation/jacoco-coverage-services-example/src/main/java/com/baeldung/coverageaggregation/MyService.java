package com.baeldung.coverageaggregation;

import java.lang.String;
import org.springframework.stereotype.Service;

@Service
class MyService {

    String unitTestedOnly() {
        return "unit tested only";
    }

    String coveredByUnitAndIntegrationTests() {
        return "covered by unit and integration tests";
    }

    String coveredByIntegrationTest() {
        return "covered by integration test";
    }

    String notTested() {
        return "not tested";
    }

}