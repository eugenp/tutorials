package com.baeldung.objectcopy.shallow;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ShallowCopyUnitTest {
    @Test
    void whenOriginalObjectHasBeenModified_thenCopyWillBeChangedAsWell() throws CloneNotSupportedException {
        Department department = new Department("IT Department", "Khao San Road", "Bangkok");
        Person person = new Person("Joe", department, 18);
        Person shallowCopyOfPerson = (Person) person.clone();

        department.setStreet("Soi Rambuttri");

        assertThat(shallowCopyOfPerson).usingRecursiveComparison()
          .isEqualTo(person);
    }
}