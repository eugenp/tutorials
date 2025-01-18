package com.baeldung.logallrequests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith({ SpringExtension.class, OutputCaptureExtension.class })
@SpringBootTest
@AutoConfigureMockMvc
public class LoggingFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRequestIsLogged(CapturedOutput output) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/hello")
            .header("X-Test-Header", "HeaderValue"))
          .andReturn();

        assertThat(output.getAll()).contains("Incoming Request: [GET] /api/hello");
    }

    @Test
    public void testResponseIsLogged(CapturedOutput output) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/hello"))
          .andReturn();

        assertThat(output.getAll()).contains("Outgoing Response for [GET] /api/hello: Status = 200");
    }

}