package com.baeldung.shallowcopy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ShallowCopyUnitTest {

    private Item shallowCopy(Item x) throws Exception {
        return (Item) x.clone();
    }

    @Test
    public void whenModifyingSourceReferenceObject_thenTargetReferenceObjectShouldChange() throws Exception {
        User user = new User(1001, "shallow copy user 1001");
        Item source = new Item(101, "shallow copy item 101", user);
        Item target = shallowCopy(source);
        source.getUser()
          .setId(1002);

        assertThat(target.getUser()
          .getId()).isEqualTo(source.getUser()
          .getId());
    }

    @Test
    public void whenModifyingSourcePrimitive_thenTargetPrimitiveShouldNotChange() throws Exception {
        User user = new User(1001, "shallow copy user 1001");
        Item source = new Item(101, "shallow copy item 101", user);
        Item target = shallowCopy(source);
        source.setId(102);

        assertThat(target.getId()).isNotEqualTo(source.getId());
    }

}

