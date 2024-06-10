package com.baeldung.deepshallow;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


public class ShallowCopyUnitTest {

    @Test
    public void whenCreatingShallowCopy_thenFieldNotBeTheSame() {
        // create a original object
        Person person = new Person("John Doe", new Contact("City A"));
        // copy
        Person person2 = new Person(person);

        // modify
        person2.setName("Peter White");
        assertFalse(person.getName().equals(person2.getName()));
    }

    @Test
    public void whenCreatingShallowCopy_thenFieldBeTheSame() {
        // create a original object
        Person person = new Person("John Doe", new Contact("City A"));
        // copy
        Person person2 = new Person(person);

        // modify
        person2.getContact().setCity("City B");

        assertTrue(person.getContact().equals(person2.getContact()));
    }

    @Test
    public void whenCreatingShallowCopyWithClone_thenFieldBeTheSame() {
        // create a original object
        Person person = new Person("John Doe", new Contact("City A"));
        // copy
        Person person2 = person.clone();

        // modify
        person2.getContact().setCity("City B");

        assertTrue(person.getContact().equals(person2.getContact()));
    }

}
