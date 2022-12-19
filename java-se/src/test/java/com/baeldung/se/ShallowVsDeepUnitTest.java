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
        Version v = new Version("1");
        Letter c = new Letter("I have the letter C.", v);
        Letter d = c;

        d.value = "I have the letter D.";
        d.version.setVersion("2");

        assertEquals(c.value, d.value);
        assertEquals(c.version, d.version);
        assertEquals(c.hashCode(), d.hashCode());
    }

    @Test
    public void whenDeepCopyLetterObject_thenObjectsAreNotEqual() {
        Version v = new Version("1");
        Letter g = new Letter("I have the letter G.", v);
        Letter h = new Letter(g.value, new Version(v.getVersion()));

        assertNotEquals(g.version, h.version);
        assertNotEquals(g.hashCode(), h.hashCode());
    }
}
