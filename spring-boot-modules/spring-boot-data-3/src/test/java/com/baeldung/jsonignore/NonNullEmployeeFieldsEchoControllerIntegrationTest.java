package com.baeldung.jsonignore;

import static com.baeldung.jsonignore.EmployeeEchoController.USERS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.baeldung.jsonignore.nullfields.Employee;
import com.fasterxml.jackson.databind.JsonNode;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

class NonNullEmployeeFieldsEchoControllerIntegrationTest extends AbstractEmployeeEchoControllerBaseTest {

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
        final Predicate<Field> nullField = s -> isFieldNull(expected, s);
        List<String> nullFields = filterFieldsAndGetNames(expected, nullField);
        List<String> nonNullFields = filterFieldsAndGetNames(expected, nullField.negate());
        final String payload = mapper.writeValueAsString(expected);
        final MvcResult result = mockMvc.perform(post(USERS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        final String response = result.getResponse().getContentAsString();
        final JsonNode jsonNode = mapper.readTree(response);

        nullFieldsShouldBeMissing(nullFields, jsonNode);
        nonNullFieldsShouldNonBeMissing(nonNullFields, jsonNode);
    }

}