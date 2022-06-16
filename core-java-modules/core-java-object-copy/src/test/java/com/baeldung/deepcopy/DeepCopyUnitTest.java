package com.baeldung.deepcopy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class DeepCopyUnitTest {

    @Test
    public void whenModifyingCopiedPerson_thenOriginalDoesNotChange() {
        Address address = new Address("123 Main Street", "New York", "NY 10030");
        Person firstPerson = new Person("John Smith", 26, address);
        Person secondPerson = firstPerson.clone();

        secondPerson.getAddress()
            .setAddressLine("321 Second Street");
        secondPerson.setAge(30);
        secondPerson.setName("Bob James");

        assertThat(firstPerson).isNotSameAs(secondPerson);
        assertThat(firstPerson.getAddress()
            .getAddressLine()).isNotSameAs(secondPerson.getAddress()
            .getAddressLine());
        assertThat(firstPerson.getAge()).isNotEqualTo(secondPerson.getAge());
        assertThat(firstPerson.getName()).isNotEqualTo(secondPerson.getName());
    }

    @Test
    public void whenModifyingConstructorCopiedPerson_thenOriginalPersonDoesNotChange() {
        Address address = new Address("123 Main Street", "New York", "NY 10030");
        Person firstPerson = new Person("John Smith", 26, address);
        Person secondPerson = new Person(firstPerson);

        secondPerson.getAddress()
            .setAddressLine("321 Second Street");
        secondPerson.setAge(30);
        secondPerson.setName("Bob James");

        assertThat(firstPerson).isNotSameAs(secondPerson);
        assertThat(firstPerson.getAddress()
            .getAddressLine()).isNotSameAs(secondPerson.getAddress()
            .getAddressLine());
        assertThat(firstPerson.getAge()).isNotEqualTo(secondPerson.getAge());
        assertThat(firstPerson.getName()).isNotEqualTo(secondPerson.getName());
    }

}
