package com.baeldung.corecopy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class DeepCopyUnitTest {

    @Test
    public void whenCreatingDeepCopyWithCopyConstructor_thenObjectsShouldNotBeSame() {

        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime Minister", address);

        User deepCopy = new User(pm);

        assertThat(deepCopy).isNotSameAs(pm);
    }

    @Test
    public void whenModifyingOriginalObject_thenCopyShouldNotChange() {
        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime Minister", address);
        User deepCopy = new User(pm);

        address.setCountry("Great Britain");

        assertThat(deepCopy.getAddress().getCountry()).isNotEqualTo(pm.getAddress().getCountry());
    }

    @Test
    public void whenModifyingOriginalObject_thenCloneCopyShouldNotChange() {
        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime Minister", address);
        User deepCopy = (User) pm.clone();

        address.setCountry("Great Britain");

        assertThat(deepCopy.getAddress().getCountry()).isNotEqualTo(pm.getAddress().getCountry());
    }

}
