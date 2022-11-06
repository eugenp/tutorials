package com.baeldung.clone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;

import org.junit.Test;

public class ShallowCopyUnitTest {

    @Test
    public void whenShadowCopy_thenListsAreEqual() {
        ValueObject object1 = new ValueObject("first");
        ValueObject object2 = new ValueObject("second");
        ValueObject object3 = new ValueObject("third");

        ArrayList<ValueObject> original = new ArrayList<>();
        original.add(object1);
        original.add(object2);
        original.add(object3);

        ArrayList<ValueObject> shallow = (ArrayList<ValueObject>) original.clone();

        assertEquals(original.size(), shallow.size());
        assertSame(original.get(0), shallow.get(0));
        assertSame(original.get(1), shallow.get(1));
        assertSame(original.get(2), shallow.get(2));
    }

    @Test
    public void givenShallowCopy_whenElementChanges_thenOriginalListIsChanged() {
        ValueObject object1 = new ValueObject("first");
        ValueObject object2 = new ValueObject("second");
        ValueObject object3 = new ValueObject("third");

        ArrayList<ValueObject> original = new ArrayList<>();
        original.add(object1);
        original.add(object2);
        original.add(object3);

        ArrayList<ValueObject> shallow = (ArrayList<ValueObject>) original.clone();

        shallow.add(shallow.remove(0));
        shallow.get(0).setValue("new first");
        shallow.get(1).setValue("new second");
        shallow.get(2).setValue("new third");

        assertEquals(original.size(), shallow.size());
        assertSame(original.get(0), shallow.get(2));
        assertSame(original.get(1), shallow.get(0));
        assertSame(original.get(2), shallow.get(1));
        assertEquals("new third", object1.getValue());
        assertEquals("new first", object2.getValue());
        assertEquals("new second", object3.getValue());
    }

}
