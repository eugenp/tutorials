package com.baeldung.jsonignore;

import static com.baeldung.jsonignore.EmployeeEchoController.USERS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.baeldung.jsonignore.nullfields.Employee;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.platform.commons.util.ReflectionUtils;
import org.junit.platform.commons.util.ReflectionUtils.HierarchyTraversalMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(EmployeeEchoController.class)
abstract class AbstractEmployeeEchoControllerBaseTest {

    protected static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Autowired
    protected MockMvc mockMvc;


    protected static void nonNullFieldsShouldNonBeMissing(final List<String> nonNullFields, final JsonNode jsonNode) {
        nonNullFields.forEach(nullField -> {
            final JsonNode nameNode = jsonNode.path(nullField);
            assertThat(nameNode.isMissingNode()).isFalse();
        });
    }

    protected static void nullFieldsShouldBeMissing(final List<String> nullFields, final JsonNode jsonNode) {
        nullFields.forEach(nullField -> {
            final JsonNode nameNode = jsonNode.path(nullField);
            assertThat(nameNode.isMissingNode()).isTrue();
        });
    }

    protected static <T> List<String> getNullFields(final T object) {
        Predicate<Field> predicate = s -> isFieldNull(object, s);
        return ReflectionUtils.findFields(object.getClass(), predicate, HierarchyTraversalMode.BOTTOM_UP)
            .stream().map(Field::getName).collect(Collectors.toList());
    }

    protected static <T> List<String> getNonNullFields(final T object) {
        Predicate<Field> predicate = s -> !isFieldNull(object, s);
        return ReflectionUtils.findFields(object.getClass(), predicate, HierarchyTraversalMode.BOTTOM_UP)
            .stream().map(Field::getName).collect(Collectors.toList());
    }

    protected static <T> boolean isFieldNull(final T object, final Field s) {
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