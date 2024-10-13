package com.baeldung.jsonignore;

import static com.baeldung.jsonignore.controller.AbsentEmployeeEchoController.USERS;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.web.servlet.MvcResult;

import com.baeldung.jsonignore.absentfields.Employee;
import com.baeldung.jsonignore.absentfields.Salary;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

class NonAbsentEmployeeFieldsEchoControllerIntegrationTest extends AbstractEmployeeEchoControllerBaseIntegrationTest {

    @ParameterizedTest
    @MethodSource
    void giveEndpointWhenSendEmployeeThanReceiveThatUserBackIgnoringAbsentValues(final Employee expected) throws Exception {
        final MvcResult result = sendRequestAndGetResult(expected, USERS);
        final String response = result.getResponse().getContentAsString();
        validateJsonFields(expected, response);
    }

    private void validateJsonFields(final Employee expected, final String response) throws JsonProcessingException {
        final JsonNode jsonNode = mapper.readTree(response);
        final Predicate<Field> nullField = s -> isFieldNull(expected, s);
        final Predicate<Field> absentField = s -> isFieldAbsent(expected, s);
        List<String> nullOrAbsentFields = filterFieldsAndGetNames(expected, nullField.or(absentField));
        List<String> nonNullAndNonAbsentFields = filterFieldsAndGetNames(expected, nullField.negate().and(absentField.negate()));
        nullFieldsShouldBeMissing(nullOrAbsentFields, jsonNode);
        nonNullFieldsShouldNonBeMissing(nonNullAndNonAbsentFields, jsonNode);
    }

    static Stream<Arguments> giveEndpointWhenSendEmployeeThanReceiveThatUserBackIgnoringAbsentValues() {
        final Salary baseSalary = new Salary(BigDecimal.TEN);
        return Stream.of(
          Arguments.of(new Employee(1L, "John", "Doe", Optional.empty())),
          Arguments.of(new Employee(1L, null, "Doe", Optional.of(baseSalary))),
          Arguments.of(new Employee(1L, "John", null, Optional.empty())),
          Arguments.of(new Employee(1L, null, null, Optional.of(baseSalary)))
        );
    }

}