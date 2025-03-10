package com.baeldung.beanshell;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Profile("JMeter-Beanshell")
class JmeterBeanshellIntegrationTest {

    MockMvc mvc;

    public JmeterBeanshellIntegrationTest(WebApplicationContext wac) {
        this.mvc = MockMvcBuilders.webAppContextSetup(wac)
            .build();
    }

    @Test
    void whenPostingItem_thenUuidReturned() throws Exception {
        MockHttpServletResponse response = mvc.perform(post("/api").contentType(MediaType.APPLICATION_JSON)
            .content("{\"key\":\"foo\", \"value\": 1}"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();

        assertNotNull(UUID.fromString(response.getContentAsString()));
    }
}
