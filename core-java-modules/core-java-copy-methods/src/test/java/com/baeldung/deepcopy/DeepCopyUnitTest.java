package com.baeldung.deepcopy;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeepCopyUnitTest {

    private Item deepCopy(Item x) throws Exception {
        return (Item) x.clone();
    }

    @Test
    public void whenModifyingSourceReferenceObject_thenTargetReferenceObjectShouldNotChange() throws Exception {
        User user = new User(1001, "deep copy user 1001");
        Item source = new Item(101, "deep copy item 101", user);
        Item target = deepCopy(source);
        source.getUser()
          .setId(1002);

        assertThat(target.getUser()
          .getId()).isNotEqualTo(source.getUser()
          .getId());
    }

    @Test
    public void whenModifyingSourcePrimitive_thenTargetPrimitiveShouldNotChange() throws Exception {
        User user = new User(1001, "deep copy user 1001");
        Item source = new Item(101, "deep copy item 101", user);
        Item target = deepCopy(source);
        source.setId(102);

        assertThat(target.getId()).isNotEqualTo(source.getId());
    }
}

