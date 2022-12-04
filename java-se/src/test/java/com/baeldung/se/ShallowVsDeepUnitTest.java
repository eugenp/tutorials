package com.baeldung.se;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;



@ExtendWith(SpringExtension.class)
public class ShallowVsDeepUnitTest {

    @Test
    public void whenShallowCopyLetterObject_thenObjectsAreEqual() {
        Letter c = new Letter("I have the letter C.");
        Letter d = c;

        d.value = "I have the letter D.";

        assertEquals(c.value, d.value);
        assertEquals(c.hashCode(), d.hashCode());
    }

    @Test
    public void whenDeepCopyLetterObject_thenObjectsAreNotEqual() {
        Letter g = new Letter();
        Letter h = new Letter();

        g.value = "I have the letter G.";
        h.value = "I have the letter H.";

        assertNotEquals(g.value, h.value);
        assertNotEquals(g.hashCode(), h.hashCode());
    }
}
