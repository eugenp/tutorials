package com.baeldung.deepcopy;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeepCopyUnitTest {

    private Item deepCopy(Item x) throws Exception {
        return (Item) x.clone();
    }

    @Test
    public void whenModifyingSourceObject_thenTargetObjectShouldNotChange() throws Exception {
        User user = new User(1001, "original_u101");
        Item source = new Item(101, "original_i1001", user);
        Item target = deepCopy(source);

        source.getUser().setId(1002);
        assertThat(target.getUser().getId())
          .isNotEqualTo(source.getUser().getId());
    }
}