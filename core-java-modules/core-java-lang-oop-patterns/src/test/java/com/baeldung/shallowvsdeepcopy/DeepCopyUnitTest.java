package com.baeldung.shallowvsdeepcopy;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class DeepCopyUnitTest {

    @Test
    public void givenDeepCopyUsingCloneMethod_whenComparing_thenAreNotEqual() {
        Line ab = new Line(new Point("a", "b"), new Point("c", "d"));
        Line cd = ab.clone();

        ab.getStart()
            .setX("z");

        assertNotEquals(ab, cd);
    }

}
