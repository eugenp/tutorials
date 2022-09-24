package com.baeldung.objectcopy.deep;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DeepCopyUnitTest {
    @Test
    void whenOriginalObjectHasBeenModified_thenCopyShouldStayTheSameUsingDeepCopyConstructor() {
        Department department = new Department("IT Department", "Khao San Road", "Bangkok");
        Person person = new Person("Joe", department, 18);
        Person deepCopyOfPerson = new Person(person);

        department.setStreet("Soi Rambuttri");

        assertThat(deepCopyOfPerson).usingRecursiveComparison()
          .isNotEqualTo(person);
    }

    @Test
    void whenOriginalObjectHasBeenModified_thenCopyShouldStayTheSameUsingDeepCloneMethod() throws CloneNotSupportedException {
        Department department = new Department("IT Department", "Khao San Road", "Bangkok");
        Person person = new Person("Joe", department, 18);
        Person deepCopyOfPerson = person.clone();

        department.setStreet("Soi Rambuttri");

        assertThat(deepCopyOfPerson).usingRecursiveComparison()
          .isNotEqualTo(person);
    }
}