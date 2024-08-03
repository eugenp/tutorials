package com.baeldung.deepshallowcopy.manual;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PersonManualDeepShallowCopyTest {
    @Test
    public void givenShallowCopy_whenModifyingOriginal_thenCopyChanges() {
        Person original = new Person("Bob", new Address("456 Elm St"));
        Person shallowCopy = original;

        original.getAddress().setStreet("789 Oak St"); // Modify original

        assertEquals("789 Oak St", shallowCopy.getAddress().getStreet()); // Assertion passes, both objects share reference
    }

    @Test
    public void givenDeepCopy_whenModifyingOriginal_thenCopyDoesNotChange() {
        Person original = new Person("Carol", new Address("101 Pine St"));
        Person deepCopy = new Person(original.getName(), new Address(original.getAddress().getStreet()));

        original.getAddress().setStreet("202 Cedar St"); // Modify original

        assertNotEquals("202 Cedar St", deepCopy.getAddress().getStreet()); // Assertion passes, deep copy remains unchanged
    }

    @Test
    public void givenDeepCopy_thenNestedObjectShouldNotBeTheSame() {
        Person original = new Person("Alex", new Address("1st Street"));
        Person deepCopy = new Person(original.getName(), new Address(original.getAddress().getStreet()));

        assertThat(deepCopy.getAddress())
                .isNotSameAs(original.getAddress());
    }
    @Test
    public void givenDeepCopy_whenModifyingDeepCopiedObject_thenOriginalObjectShouldNotChange() {
        Person original = new Person("Alex", new Address("1st Street"));
        Person deepCopy = new Person(original.getName(), new Address(original.getAddress().getStreet()));

        deepCopy.setName("kumar"); // Modify deep copy

        assertThat(deepCopy.getName())
                .isNotSameAs(original.getName());
    }
}