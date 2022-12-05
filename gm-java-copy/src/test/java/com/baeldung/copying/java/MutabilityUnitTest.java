
package com.baeldung.copying.java;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class MutabilityUnitTest {

    @Test
    void givenMutablePerson_whenNamesListReferenceStored_thenItOutlivesThePerson() {
        List<String> namesReference;
        {
            var person = new Person(arrayListOf("John", "Donald"), "Keane");
            namesReference = person.getNames();
        }

        namesReference.add("William");

        assertThat(namesReference).containsExactly("John", "Donald", "William");
    }

    private List<String> arrayListOf(String... names) {
        List<String> list = new ArrayList<>();
        list.addAll(asList(names));
        return list;
    }
}
