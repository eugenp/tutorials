package com.baeldung.ambassadorpattern;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

@WebMvcTest(HttpAmbassadorController.class)
@Import({ HttpAmbassadorNamesApiClient.class, TestConfig.class })
@AutoConfigureMockMvc(addFilters = false)
class HttpAmbassadorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void givenExternalCallMock_whenGetNames_thenReturnExpectedName() throws Exception {
        String expectedResponse = "{'name': 'Baeldung'}";
        when(restTemplate.getForObject(eq("https://domain.com/names/api"), eq(String.class))).thenReturn(expectedResponse);

        mockMvc.perform(get("/v1/http-ambassador/names"))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));
    }
}