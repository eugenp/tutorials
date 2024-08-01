package com.baeldung.shallowvsdeepcopy;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class DeepCopyUnitTest {

    @Test
    public void givenDeepCopy_whenChangingProperty_thenAreNotEqual() {
        Line ab = new Line(new Point("a", "b"), new Point("c", "d"));
        Line cd = new Line(ab);

        ab.setStart(new Point("x", "y"));

        assertNotEquals(
            ab.getStart().getX(),
            cd.getStart().getX());
    }

    @Test
    public void givenCloneMethod_whenComparing_thenAreNotEqual() {
        Line ab = new Line(new Point("a", "b"), new Point("c", "d"));
        Line cd = ab.clone();

        assertNotEquals(ab, cd);
    }

}
