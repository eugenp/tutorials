package org.baeldung.springquartz.actuator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class QuartzActuatorUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenValidJobDetails_whenTriggeringJobViaActuator_thenReturnsJobExecutionDetails() throws Exception {
        String jobGroup = "DEFAULT";
        String jobName = "Qrtz_Job_Detail";
        String requestBody = "{\"state\":\"running\"}";

        mockMvc.perform(post("/actuator/quartz/jobs/{group}/{name}", jobGroup, jobName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.group").value(jobGroup))
                .andExpect(jsonPath("$.name").value(jobName))
                .andExpect(jsonPath("$.className").exists())
                .andExpect(jsonPath("$.triggerTime").exists());
    }
}