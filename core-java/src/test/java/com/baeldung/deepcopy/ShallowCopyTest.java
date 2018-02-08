package com.baeldung.deepcopy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ShallowCopyTest {


    @Test
    public void whenShallowCoping_thenObjectsShouldNotBeSame() {

        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime", "Minister", address);
        User shallowCopy = new User(pm.getFirstName(), pm.getLastName(), pm.getAddress());

        assertThat(shallowCopy)
                .isNotSameAs(pm);
    }

    @Test
    public void whenModifyingOriginalObject_ThenCopyShouldChange() {
        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime", "Minister", address);
        User shallowCopy = new User(pm.getFirstName(), pm.getLastName(), pm.getAddress());

        address.setCountry("Great Britain");
        assertThat(shallowCopy.getAddress().getCountry())
                .isNotEqualTo(pm.getAddress().getCountry());
    }
}
