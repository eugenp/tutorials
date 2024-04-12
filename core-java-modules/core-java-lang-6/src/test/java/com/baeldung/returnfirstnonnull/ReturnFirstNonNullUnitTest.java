package com.baeldung.returnfirstnonnull;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.MoreObjects;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;

public class ReturnFirstNonNullUnitTest {

    private List<String> objects;

    @BeforeEach
    public void init() {
        objects = Arrays.asList(null, "first non null", "second nun null");
    }

    @Test
    void givenListOfObjects_whenFilterIsLambdaNullCheckInStream_thenReturnFirstNonNull() {
        Optional<String> object = objects.stream()
            .filter(o -> o != null)
            .findFirst();

        assertThat(object).contains("first non null");
    }

    @Test
    void givenListOfObjects_whenFilterIsMethodRefNullCheckInStream_thenReturnFirstNonNull() {
        Optional<String> object = objects.stream()
            .filter(Objects::nonNull)
            .findFirst();

        assertThat(object).contains("first non null");
    }

    @Test
    void givenListOfObjects_whenIteratingWithForLoop_thenReturnFirstNonNull() {
        String object = null;
        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i) != null) {
                object = objects.get(i);
                break;
            }
        }

        assertEquals("first non null", object);
    }

    @Test
    void givenListOfObjects_whenUsingApacheCommonsLang3_thenReturnFirstNonNull() {
        String object = ObjectUtils.firstNonNull(objects.toArray(new String[0]));

        assertEquals("first non null", object);
    }

    @Test
    void givenListOfObjects_whenUsingGoogleGuavaIterables_thenReturnFirstNonNull() {
        String object = Iterables.find(objects, Predicates.notNull());

        assertEquals("first non null", object);
    }

    @Test
    void givenTwoObjects_whenUsingGoogleGuavaMoreObjects_thenReturnFirstNonNull() {
        String nullObject = null;
        String nonNullObject = "first non null";
        String object = MoreObjects.firstNonNull(nullObject, nonNullObject);

        assertEquals("first non null", object);
    }
}