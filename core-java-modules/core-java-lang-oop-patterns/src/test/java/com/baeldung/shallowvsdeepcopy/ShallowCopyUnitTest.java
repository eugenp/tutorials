package com.baeldung.shallowvsdeepcopy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ShallowCopyUnitTest {

    @Test
    public void givenShallowCopy_whenComparing_thenAreEqual() {
        Line ab = new Line(new Point("a", "b"), new Point("c", "d"));
        Line cd = ab;

        assertEquals(ab, cd);
    }

    @Test
    public void givenShallowCopy_whenChangingProperty_thenAreEqual() {
        Line ab = new Line(new Point("a", "b"), new Point("c", "d"));
        Line cd = ab;

        ab.getStart()
            .setX("z");

        assertEquals(
            ab.getStart().getX(),
            cd.getStart().getX());
    }

}
