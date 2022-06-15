package com.baeldung.shallowcopy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ShallowCopyTest {

    @Test
    public void whenModifyingClonedPersonFields_thenOriginalPersonAddressChanges() {
        Address address = new Address("123 Main Street", "New York", "NY 10030");
        Person firstPerson = new Person("John Smith", 26, address);
        Person secondPerson = firstPerson.clone();

        secondPerson.getAddress()
            .setAddressLine("321 Second Street");
        secondPerson.setAge(30);
        secondPerson.setName("Bob James");

        assertThat(firstPerson).isNotSameAs(secondPerson);
        assertThat(firstPerson.getAddress()
            .getAddressLine()).isSameAs(secondPerson.getAddress()
            .getAddressLine());
        assertThat(firstPerson.getAge()).isNotEqualTo(secondPerson.getAge());
        assertThat(firstPerson.getName()).isNotEqualTo(secondPerson.getName());
    }

    @Test
    public void whenModifyingConstructorCopiedPersonFields_thenOriginalPersonAddressChanges() {
        Address address = new Address("123 Main Street", "New York", "NY 10030");
        Person firstPerson = new Person("John Smith", 26, address);
        Person secondPerson = new Person(firstPerson);

        secondPerson.getAddress()
            .setAddressLine("321 Second Street");
        secondPerson.setAge(30);
        secondPerson.setName("Bob James");

        assertThat(firstPerson).isNotSameAs(secondPerson);
        assertThat(firstPerson.getAddress()
            .getAddressLine()).isSameAs(secondPerson.getAddress()
            .getAddressLine());
        assertThat(firstPerson.getAge()).isNotEqualTo(secondPerson.getAge());
        assertThat(firstPerson.getName()).isNotEqualTo(secondPerson.getName());
    }
}
