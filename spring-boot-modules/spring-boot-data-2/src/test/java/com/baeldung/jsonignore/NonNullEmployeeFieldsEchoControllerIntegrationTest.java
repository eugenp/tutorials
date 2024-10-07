package com.baeldung.jsonignore;

import static com.baeldung.jsonignore.controller.EmployeeEchoController.USERS;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.web.servlet.MvcResult;

import com.baeldung.jsonignore.nullfields.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

class NonNullEmployeeFieldsEchoControllerIntegrationTest extends AbstractEmployeeEchoControllerBaseIntegrationTest {

    @ParameterizedTest
    @MethodSource
    void giveEndpointWhenSendEmployeeThanReceiveThatUserBackIgnoringNullValues(final Employee expected) throws Exception {
        final MvcResult result = sendRequestAndGetResult(expected, USERS);
        final String response = result.getResponse().getContentAsString();
        validateJsonFields(expected, response);
    }

    private void validateJsonFields(final Employee expected, final String response) throws JsonProcessingException {
        final JsonNode jsonNode = mapper.readTree(response);
        final Predicate<Field> nullField = s -> isFieldNull(expected, s);
        List<String> nullFields = filterFieldsAndGetNames(expected, nullField);
        List<String> nonNullFields = filterFieldsAndGetNames(expected, nullField.negate());
        nullFieldsShouldBeMissing(nullFields, jsonNode);
        nonNullFieldsShouldNonBeMissing(nonNullFields, jsonNode);
    }

    static Stream<Arguments> giveEndpointWhenSendEmployeeThanReceiveThatUserBackIgnoringNullValues() {
        return Stream.of(
          Arguments.of(new Employee(1L, "John", "Doe")),
          Arguments.of(new Employee(1L, null, "Doe")),
          Arguments.of(new Employee(1L, "John", null)),
          Arguments.of(new Employee(1L, null, null))
        );
    }

}