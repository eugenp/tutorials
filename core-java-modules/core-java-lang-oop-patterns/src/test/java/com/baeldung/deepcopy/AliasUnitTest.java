package com.baeldung.deepcopy;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AliasUnitTest {

    @Test
    public void whenAssigningAliasAndChangingValues_thenObjectsShouldNotBeSame() {
        List<Integer> code = Arrays.asList(2, 5, 9);
        LockBox lockBox = new LockBox(code);

        code.set(1, Integer.valueOf(0));
        assertThat(lockBox.getCombination().get(1))
                .isEqualTo(0);
    }
}
