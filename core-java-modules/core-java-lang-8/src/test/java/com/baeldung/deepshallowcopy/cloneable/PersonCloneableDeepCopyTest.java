package com.baeldung.deepshallowcopy.cloneable;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotEquals;

public class PersonCloneableDeepCopyTest {

    @Test
    public void givenDeepCopy_whenModifyingOriginal_thenCopyDoesNotChange() {
        Person original = new Person("Carol", new Address("101 Pine St"));
        Person deepCopy = original.clone();

        original.getAddress().setStreet("202 Cedar St"); // Modify original

        assertNotEquals("202 Cedar St", deepCopy.getAddress().getStreet()); // Assertion passes, deep copy remains unchanged
    }

    @Test
    public void givenDeepCopy_thenObjectsShouldNotBeTheSame() {
        Person original = new Person("Alex", new Address("1st Street"));
        Person deepCopy = original.clone();
        assertThat(deepCopy)
                .isNotSameAs(original);
    }

    @Test
    public void givenDeepCopy_whenModifyingDeepCopiedObject_thenOriginalObjectShouldNotChange() {
        Person original = new Person("Alex", new Address("1st Street"));
        Person deepCopy = original.clone();

        deepCopy.setName("kumar"); // Modify deep copy

        assertThat(deepCopy.getName())
                .isNotSameAs(original.getName());
    }
}