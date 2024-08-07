package com.baeldung.shallowvsdeepcopy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ShallowCopyUnitTest {

    @Test
    public void givenShallowCopyCopingFields_whenComparing_thenAreEqual() {
        Line ab = new Line(new Point("a", "b"), new Point("c", "d"));
        Line cd = new Line(ab.getStart(), ab.getEnd());

        ab.getStart()
            .setX("z");

        assertEquals(ab.getStart(), cd.getStart());
    }

}
