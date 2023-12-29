package com.baeldung.jsonignore;

import static com.baeldung.jsonignore.EmployeeEchoController.USERS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.baeldung.jsonignore.absentfields.Employee;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

class NonAbsentEmployeeFieldsEchoControllerIntegrationTest extends AbstractEmployeeEchoControllerBaseTest {

    @ParameterizedTest
    @CsvSource(value = {
        "1,John,Doe",
        "1,,Doe",
        "1,John,",
        "1,,"
    })
    void giveEndpointWhenSendEmployeeThanReceiveThatUserBackIgnoringNullValues(
        final long id, final String firstName, final String secondName) throws Exception {
        final Employee expected = new Employee(id, firstName, secondName);
        List<String> nullFields = getNullFields(expected);
        List<String> nonNullFields = getNonNullFields(expected);
        final String payload = JSON_MAPPER.writeValueAsString(expected);
        final MvcResult result = mockMvc.perform(post(USERS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        final String response = result.getResponse().getContentAsString();
        final JsonNode jsonNode = JSON_MAPPER.readTree(response);

        nullFieldsShouldBeMissing(nullFields, jsonNode);
        nonNullFieldsShouldNonBeMissing(nonNullFields, jsonNode);
    }

}