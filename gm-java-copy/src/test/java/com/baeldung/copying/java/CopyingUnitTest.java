package com.baeldung.copying.java;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class CopyingUnitTest {

    @Test
    void givenCustomer_whenSecondCustomerShallowCopy_thenNameChangeIsPropagatedToBoth() {
        var firstCustomer = new Person(arrayListOf("John", "William"), "Watson");
        var secondCustomer = new Person(firstCustomer.getNames(), firstCustomer.getSurname());

        secondCustomer.getNames().add("Jake");

        assertThat(firstCustomer).isNotSameAs(secondCustomer);
        assertThat(secondCustomer.getNames()).containsExactly("John", "William", "Jake");
        assertThat(secondCustomer.getNames()).isEqualTo(firstCustomer.getNames());
        assertThat(secondCustomer.getNames()).isSameAs(firstCustomer.getNames());
    }

    @Test
    void givenCustomer_whenSecondCustomerDeepCopy_thenNameChangeAppliesToSecondOnly() {
        var firstCustomer = new Person(arrayListOf("John", "William"), "Watson");
        var secondCustomer = new Person(copyToArrayList(firstCustomer.getNames()), firstCustomer.getSurname());

        secondCustomer.getNames().add("Jake");

        assertThat(firstCustomer).isNotSameAs(secondCustomer);
        assertThat(secondCustomer.getNames()).containsExactly("John", "William", "Jake");
        assertThat(secondCustomer.getNames()).isNotEqualTo(firstCustomer.getNames());
        assertThat(secondCustomer.getNames()).isNotSameAs(firstCustomer.getNames());
    }

    private List<String> arrayListOf(String... names) {
        List<String> list = new ArrayList<>();
        list.addAll(asList(names));
        return list;
    }

    private List<String> copyToArrayList(List<String> names) {
        List<String> list = new ArrayList<>();
        list.addAll(names);
        return list;
    }
}
