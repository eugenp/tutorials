package com.baeldung.deepcopy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ShallowCopyUnitTest {


    @Test
    public void whenShallowCopying_thenObjectsShouldNotBeSame() {

        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime", "Minister", address);

        User shallowCopy = new User(pm.getFirstName(), pm.getLastName(), pm.getAddress());

        assertThat(shallowCopy)
                .isNotSameAs(pm);
    }

    @Test
    public void whenModifyingOriginalObject_thenCopyShouldChange() {
        Address address = new Address("Downing St 10", "London", "England");
        User pm = new User("Prime", "Minister", address);
        User shallowCopy = new User(pm.getFirstName(), pm.getLastName(), pm.getAddress());

        address.setCountry("Great Britain");
        
        assertThat(shallowCopy.getAddress().getCountry())
                .isEqualTo(pm.getAddress().getCountry());
    }

    @Test
    public void whenShallowCopyingLockBox_thenObjectsShouldNotBeSame() {
        LockBox lockBox = new LockBox(Arrays.asList(2, 5, 9));
        LockBox shallowCopy = lockBox.getShallowCopy();

        assertThat(shallowCopy)
                .isNotSameAs(lockBox);
    }

    @Test
    public void whenModifyingOriginalLockBoxObject_thenCopyShouldChange() {
        LockBox lockBox = new LockBox(Arrays.asList(2, 5, 9));
        LockBox shallowCopy = lockBox.getShallowCopy();

        List<Integer> newCombination = shallowCopy.getCombination();
        newCombination.set(1, 0);

        assertThat(shallowCopy.getCombination().get(1))
                .isEqualTo(0);
        assertThat(shallowCopy.getCombination())
                .isEqualTo(lockBox.getCombination());
    }
}
