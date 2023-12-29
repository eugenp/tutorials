package com.baeldung.jsonignore;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.junit.platform.commons.util.ReflectionUtils;
import org.junit.platform.commons.util.ReflectionUtils.HierarchyTraversalMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {EmployeeEchoController.class, AbsentEmployeeEchoController.class,
    EmptyEmployeeEchoController.class})
abstract class AbstractEmployeeEchoControllerBaseTest {

    @Autowired
    protected ObjectMapper mapper = new ObjectMapper();

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

    protected static <T> List<String> filterFieldsAndGetNames(final T object, final Predicate<Field> predicate) {
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

    protected static <T> boolean isFieldAbsent(final T object, final Field s) {
        try {
            if (Optional.class.isAssignableFrom(s.getType())) {
                s.setAccessible(true);
                final Optional<?> optional = ((Optional<?>) s.get(object));
                if (optional != null) {
                    return !optional.isPresent();
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected static <T> boolean isFieldEmpty(final T object, final Field s) {
        try {
            if (String.class.isAssignableFrom(s.getType())) {
                s.setAccessible(true);
                final String string = ((String) s.get(object));
                if (string != null) {
                    return string.isEmpty();
                } else {
                    return false;
                }
            } else if (Collection.class.isAssignableFrom(s.getType())) {
                s.setAccessible(true);
                final Collection collection = ((Collection) s.get(object));
                if (collection != null) {
                    return collection.isEmpty();
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}