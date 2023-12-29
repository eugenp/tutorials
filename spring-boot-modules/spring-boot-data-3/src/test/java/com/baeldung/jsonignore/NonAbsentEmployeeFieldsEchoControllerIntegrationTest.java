package com.baeldung.jsonignore;

import static com.baeldung.jsonignore.AbsentEmployeeEchoController.USERS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.baeldung.jsonignore.absentfields.Employee;
import com.baeldung.jsonignore.absentfields.Salary;
import com.fasterxml.jackson.databind.JsonNode;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

class NonAbsentEmployeeFieldsEchoControllerIntegrationTest extends AbstractEmployeeEchoControllerBaseTest {

    @ParameterizedTest
    @MethodSource
    void giveEndpointWhenSendEmployeeThanReceiveThatUserBackIgnoringNullValues(final Employee expected) throws Exception {
        final Predicate<Field> nullField = s -> isFieldNull(expected, s);
        final Predicate<Field> absentField = s -> isFieldAbsent(expected, s);
        List<String> nullOrAbsentFields = filterFieldsAndGetNames(expected, nullField.or(absentField));
        List<String> nonNullAndNonAbsentFields = filterFieldsAndGetNames(expected, nullField.negate().and(absentField.negate()));
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

        nullFieldsShouldBeMissing(nullOrAbsentFields, jsonNode);
        nonNullFieldsShouldNonBeMissing(nonNullAndNonAbsentFields, jsonNode);
    }

    static Stream<Arguments> giveEndpointWhenSendEmployeeThanReceiveThatUserBackIgnoringNullValues() {
        final Salary baseSalary = new Salary(BigDecimal.TEN);
        return Stream.of(
            Arguments.of(new Employee(1L,"John","Doe", Optional.empty())),
            Arguments.of(new Employee(1L,null,"Doe", Optional.of(baseSalary))),
            Arguments.of(new Employee(1L,"John",null, Optional.empty())),
            Arguments.of(new Employee(1L,null,null, Optional.of(baseSalary)))
        );
    }

}