package com.baeldung.springai.explainableaiagents;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("explainableaiagents")
public class ExplainableAIAgentsLiveTest {
    @Autowired
    private PatientHealthStatusService statusService;

    @Test
    void givenPatientHealthStatusService_whenAskingPatientHealthStatusAndChangeDate_thenResponseShouldContainExpectedInformation() {

        String healthStatusResponse = statusService.getPatientStatusInformation("What is the health status of the patient P002?");
        assertThat(healthStatusResponse)
          .contains("cough");

        String healthStatusChangeDateResponse = statusService.getPatientStatusInformation("When the patient P002 health status was changed?");
        assertThat(healthStatusChangeDateResponse)
          .contains("July 19, 2025");

    }

    @Test
    void givenPatientHealthStatusService_whenAskingPatientHealthStatusByPatientName_thenResponseShouldContainExpectedInformation() {

        String healthStatusResponse = statusService.getPatientStatusInformation("What is the health status of the patient. Patient name: John Snow?");
        assertThat(healthStatusResponse)
          .containsIgnoringCase("healthy");
    }
}
