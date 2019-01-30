package com.baeldung.deepcopy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * 浅克隆测试
 */
public class ShallowCopyUnitTest {


    @Test
    public void whenShallowCopying_thenObjectsShouldNotBeSame() {

        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime", "Minister", address);

        User shallowCopy = new User(
                pm.getFirstName(),
                pm.getLastName(),
                pm.getAddress()
        );

        assertThat(shallowCopy)
                .isNotSameAs(pm);
    }

    @Test
    public void whenModifyingOriginalObject_thenCopyShouldChange() {
        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime", "Minister", address);
        User shallowCopy = new User(pm.getFirstName(), pm.getLastName(), pm.getAddress());

        address.setCountry("Great Britain");

        System.out.println(shallowCopy.getAddress().getCountry());
        System.out.println(pm.getAddress().getCountry());
        
        assertThat(shallowCopy.getAddress().getCountry())
                .isEqualTo(pm.getAddress().getCountry());
    }
}
