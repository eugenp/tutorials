package com.baeldung.hexagonalarchitecture2;

import com.baeldung.hexagonalarchitecture2.appointments.scheduling.adapter.inbound.web.ScheduleAppointmentRequest;
import com.baeldung.hexagonalarchitecture2.appointments.scheduling.adapter.outbound.persistence.AppointmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = IntegrationTestInitializer.class)
public class ScheduleAppointmentIntegrationTest {
    static final Faker faker = Faker.instance();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    void scheduleAppointmentSucceeds() throws Exception {
        var requestedAppointment = ScheduleAppointmentRequest.builder().requestedBy(faker.internet().emailAddress()).requestedTime(LocalDateTime.now().plusDays(20)).build();

        mockMvc.perform(
            post("/appointments")
              .accept(MediaType.APPLICATION_JSON)
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(requestedAppointment)))
          .andExpect(status().isCreated());
        assertThat(appointmentRepository.existsByAppointmentTime(requestedAppointment.getRequestedTime())).isTrue();
    }

    @Test
    void scheduleAppointmentFails() throws Exception {
        var requestedAppointment = ScheduleAppointmentRequest.builder().requestedBy(faker.internet().emailAddress()).requestedTime(LocalDateTime.now().plusDays(15)).build();

        var failedRequestAppointment = ScheduleAppointmentRequest.builder().requestedBy(faker.internet().emailAddress()).requestedTime(requestedAppointment.getRequestedTime()).build();

        mockMvc.perform(
          post("/appointments")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestedAppointment)))
          .andExpect(status().isCreated());

        mockMvc.perform(
          post("/appointments")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(failedRequestAppointment)))
          .andExpect(status().is4xxClientError());
    }
}
