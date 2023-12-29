package com.baeldung.nullfileds;

import static com.baeldung.nullfileds.UserEchoController.USERS;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(UserEchoController.class)
class UserEchoControllerIntegrationTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void smokeTest() {
        assumeThat(mockMvc).isNull();
    }

    @ParameterizedTest
    @CsvSource({"1, John, Doe"})
    void giveEndpointWhenSendUserThanReceiveThatUserBack(
        final long id, final String firstName, final String secondName) throws Exception {
        final User expected = new User(id, firstName, secondName);
        final String payload = OBJECT_MAPPER.writeValueAsString(expected);
        final MvcResult result = mockMvc.perform(post(USERS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        final String response = result.getResponse().getContentAsString();
        final User actual = OBJECT_MAPPER.readValue(response, User.class);
        assumeThat(actual).isEqualTo(expected);
    }
}