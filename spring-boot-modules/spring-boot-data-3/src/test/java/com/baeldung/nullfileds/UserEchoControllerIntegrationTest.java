package com.baeldung.nullfileds;

import static com.baeldung.nullfileds.UserEchoController.USERS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.platform.commons.util.ReflectionUtils;
import org.junit.platform.commons.util.ReflectionUtils.HierarchyTraversalMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(UserEchoController.class)
class UserEchoControllerIntegrationTest {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void smokeTest() {
        assumeThat(mockMvc).isNotNull();
    }

    @ParameterizedTest
    @CsvSource(value = {
        "1,John,Doe",
        "1,,Doe",
        "1,John,",
        "1,,"
    })
    void giveEndpointWhenSendUserThanReceiveThatUserBackIgnoringNullValues(
        final long id, final String firstName, final String secondName) throws Exception {
        final User expected = new User(id, firstName, secondName);
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
        nonNullFielsShouldNonBeMissing(nonNullFields, jsonNode);
    }

    private static void nonNullFielsShouldNonBeMissing(final List<String> nonNullFields, final JsonNode jsonNode) {
        nonNullFields.forEach(nullField -> {
            final JsonNode nameNode = jsonNode.path(nullField);
            assertThat(nameNode.isMissingNode()).isFalse();
        });
    }

    private static void nullFieldsShouldBeMissing(final List<String> nullFields, final JsonNode jsonNode) {
        nullFields.forEach(nullField -> {
            final JsonNode nameNode = jsonNode.path(nullField);
            assertThat(nameNode.isMissingNode()).isTrue();
        });
    }

    private static <T> List<String> getNullFields(final T object) {
        Predicate<Field> predicate = s -> isFieldNull(object, s);
        return ReflectionUtils.findFields(object.getClass(), predicate, HierarchyTraversalMode.BOTTOM_UP)
            .stream().map(Field::getName).collect(Collectors.toList());
    }
    private static <T> List<String> getNonNullFields(final T object) {
        Predicate<Field> predicate = s -> !isFieldNull(object, s);
        return ReflectionUtils.findFields(object.getClass(), predicate, HierarchyTraversalMode.BOTTOM_UP)
            .stream().map(Field::getName).collect(Collectors.toList());
    }

    private static <T> boolean isFieldNull(final T object, final Field s) {
        try {
            if (Object.class.isAssignableFrom(s.getType())) {
                s.setAccessible(true);
                return s.get(object) == null;
            } else {
                return false;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}