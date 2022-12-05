package com.baeldung.copying.java;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ImmutabilityUnitTest {

    @Test
    void givenImmutablePerson_whenNamesListObtained_thenItCanBeModifiedNotPersonStateIsAffected() {
        var person = new ImmutablePerson(arrayListOf("John", "Donald"), "Keane");

        assertThatExceptionOfType(UnsupportedOperationException.class)
          .isThrownBy(() -> person.getNames().add("William"));

        assertThat(person.getNames()).containsExactly("John", "Donald");
    }

    private List<String> arrayListOf(String... names) {
        List<String> list = new ArrayList<>();
        list.addAll(asList(names));
        return list;
    }
}
