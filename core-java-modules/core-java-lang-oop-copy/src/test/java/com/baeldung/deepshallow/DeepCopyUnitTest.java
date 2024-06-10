package com.baeldung.deepshallow;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class DeepCopyUnitTest {
    @Test
    public void whenCreatingDeepCopy_thenFieldNotBeTheSame() {
        // create a original object
        Person person = new Person("John Doe", new Contact("City A"));

        // deep copy
        Person person2 = Person.deepCopy(person);

        // modify
        person2.setName("Peter White");
        assertFalse(person.getName().equals(person2.getName()));
    }

    @Test
    public void whenCreatingDeepCopy_thenNestedObjectNotBeTheSame() {
        // create a original object
        Person person = new Person("John Doe", new Contact("City A"));

        // copy
        Person person2 = Person.deepCopy(person);

        // modify city
        person2.getContact().setCity("City B");

        assertFalse(person.getContact().equals(person2.getContact()));
    }

    @Test
    public void whenCreatingDeepCopyWithClone_thenNestedObjectNotBeTheSame() {
        // create a original object
        Person person = new Person("John Doe", new Contact("City A"));

        // copy
        Person person2 = person.deepClone();

        // modify city
        person2.getContact().setCity("City B");

        assertFalse(person.getContact().equals(person2.getContact()));
    }
}
