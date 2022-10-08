package com.baeldung.shallowcopy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ShallowCopyUnitTest {

    private Item shallowCopy(Item x) throws Exception {
        return (Item) x.clone();
    }

    @Test
    public void whenModifyingSourceObject_thenTargetObjectShouldChange() throws Exception {
        User user = new User(1001, "original_u101");
        Item source = new Item(101, "original_i1001", user);
        Item target = shallowCopy(source);

        source.getUser().setId(1002);
        assertThat(target.getUser().getId())
          .isEqualTo(source.getUser().getId());
    }
}

