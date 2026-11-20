package com.baeldung.springai.explainableaiagents;

import org.springframework.ai.tool.annotation.Tool;

import java.time.LocalDate;
import java.util.Map;

public class PatientHealthInformationTools {
    private static final Map<String, HealthStatus> HEALTH_DATA = Map.of(
      "P001", new HealthStatus("Healthy", LocalDate.ofYearDay(2025, 100)),
      "P002", new HealthStatus("Has cough", LocalDate.ofYearDay(2025, 200)),
      "P003", new HealthStatus("Healthy", LocalDate.ofYearDay(2025, 300)),
      "P004", new HealthStatus("Has increased blood pressure", LocalDate.ofYearDay(2025, 350)),
      "P005", new HealthStatus("Healthy", LocalDate.ofYearDay(2026, 10)));

    private static final Map<String, String> PATIENTS_IDS = Map.of(
      "John Snow", "P001",
      "Emily Carter", "P002",
      "Michael Brown", "P003",
      "Sophia Williams", "P004",
      "Daniel Johnson", "P005"
    );

    @Tool(description = "Get patient health status")
    public String retrievePatientHealthStatus(String patientId) {
        return HEALTH_DATA.get(patientId).status();
    }

    @Tool(description = "Get patient id for patient name")
    public String retrievePatientId(String patientName) {
        return PATIENTS_IDS.get(patientName);
    }

    @Tool(description = "Get when patient health status was updated")
    public LocalDate retrievePatientHealthStatusChangeDate(String patientId) {
        return HEALTH_DATA.get(patientId).changeDate();
    }
}
