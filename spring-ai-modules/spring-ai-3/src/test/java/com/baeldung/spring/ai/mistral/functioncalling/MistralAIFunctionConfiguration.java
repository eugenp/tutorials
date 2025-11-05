package com.baeldung.spring.ai.mistral.functioncalling;

import com.baeldung.springai.dto.HealthStatus;
import com.baeldung.springai.dto.Patient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;

@Configuration
public class MistralAIFunctionConfiguration {
    public static final Map<Patient, HealthStatus> HEALTH_DATA = Map.of(
      new Patient("P001"), new HealthStatus("Healthy",
        LocalDate.of(2024,1, 20)),
      new Patient("P002"), new HealthStatus("Has cough",
        LocalDate.of(2024,3, 15)),
      new Patient("P003"), new HealthStatus("Healthy",
        LocalDate.of(2024,4, 12)),
      new Patient("P004"), new HealthStatus("Has increased blood pressure",
        LocalDate.of(2024,5, 19)),
      new Patient("P005"), new HealthStatus("Healthy",
        LocalDate.of(2024,6, 1)));

    @Bean
    @Description("Get patient health status")
    public Function<Patient, String> retrievePatientHealthStatus() {
        return (patient) -> HEALTH_DATA.get(patient).status();
    }

    @Bean
    @Description("Get when patient health status was updated")
    public Function<Patient, LocalDate> retrievePatientHealthStatusChangeDate() {
        return (patient) -> HEALTH_DATA.get(patient).changeDate();
    }
}
