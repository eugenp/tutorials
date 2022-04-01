package com.baeldung.copy;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.Test;

public class ShallowCopyTest {

    @Test
    public void testThatCopyArrayNumber() {
        int[] vals = {3, 7, 9};
        ShallowCopy e = new ShallowCopy(vals);
        e.showData(); // prints out [3, 7, 9]

        assertContains(3, e.showData);
        assertContains(7, e.showData);
        assertContains(9, e.showData);

    }

}
