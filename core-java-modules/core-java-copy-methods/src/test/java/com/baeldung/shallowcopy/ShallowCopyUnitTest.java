package com.baeldung.shallowcopy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ShallowCopyUnitTest {

    private Item shallowCopy(Item x) throws Exception {
        return (Item) x.clone();
    }

    @Test
    public void whenModifyingSourceObject_thenTargetObjectShouldChange() throws Exception {
        User user = new User();
        user.setId(1001);
        user.setName("original_i1001");
        Item source = new Item();
        source.setId(1001);
        source.setItemName("original_i1001");
        source.setUser(user);
        Item target = shallowCopy(source);

        source.getUser().setId(1002);
        assertThat(target.getUser().getId())
          .isEqualTo(source.getUser().getId());
    }
}

