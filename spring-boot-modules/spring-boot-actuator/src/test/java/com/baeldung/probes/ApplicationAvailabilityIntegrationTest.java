package com.baeldung.probes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;
import static org.springframework.test.annotation.DirtiesContext.MethodMode.AFTER_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_CLASS)
public class ApplicationAvailabilityIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ApplicationContext context;
    @Autowired private ApplicationAvailability applicationAvailability;

    @Test
    public void givenApplication_whenStarted_thenShouldBeAbleToRetrieveReadinessAndLiveness() {
        assertThat(applicationAvailability.getLivenessState()).isEqualTo(LivenessState.CORRECT);
        assertThat(applicationAvailability.getReadinessState()).isEqualTo(ReadinessState.ACCEPTING_TRAFFIC);

        assertThat(applicationAvailability.getState(ReadinessState.class)).isEqualTo(ReadinessState.ACCEPTING_TRAFFIC);
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    public void givenCorrectState_whenPublishingTheEvent_thenShouldTransitToBrokenState() throws Exception {
        assertThat(applicationAvailability.getLivenessState()).isEqualTo(LivenessState.CORRECT);
        mockMvc.perform(get("/actuator/health/liveness"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.status").value("UP"));

        AvailabilityChangeEvent.publish(context, LivenessState.BROKEN);

        assertThat(applicationAvailability.getLivenessState()).isEqualTo(LivenessState.BROKEN);
        mockMvc.perform(get("/actuator/health/liveness"))
          .andExpect(status().isServiceUnavailable())
          .andExpect(jsonPath("$.status").value("DOWN"));
    }

    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    public void givenAcceptingState_whenPublishingTheEvent_thenShouldTransitToRefusingState() throws Exception {
        assertThat(applicationAvailability.getReadinessState()).isEqualTo(ReadinessState.ACCEPTING_TRAFFIC);
        mockMvc.perform(get("/actuator/health/readiness"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.status").value("UP"));

        AvailabilityChangeEvent.publish(context, ReadinessState.REFUSING_TRAFFIC);

        assertThat(applicationAvailability.getReadinessState()).isEqualTo(ReadinessState.REFUSING_TRAFFIC);
        mockMvc.perform(get("/actuator/health/readiness"))
          .andExpect(status().isServiceUnavailable())
          .andExpect(jsonPath("$.status").value("OUT_OF_SERVICE"));
    }
}
