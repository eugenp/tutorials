package com.baeldung.clone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class DeepCopyUnitTest {

    @Test
    public void whenDeepCopy_thenListsAreEqual() {
        ValueObject object1 = new ValueObject("first");
        ValueObject object2 = new ValueObject("second");
        ValueObject object3 = new ValueObject("third");

        ArrayList<ValueObject> original = new ArrayList<>();
        original.add(object1);
        original.add(object2);
        original.add(object3);

        ArrayList<ValueObject> deep = new ArrayList<>();
        for (int index = 0; index < original.size(); index++) {
            deep.add(new ValueObject(original.get(index).getValue()));
        }

        assertEquals(original.size(), deep.size());
        assertEquals(original.get(0).getValue(), deep.get(0).getValue());
        assertEquals(original.get(1).getValue(), deep.get(1).getValue());
        assertEquals(original.get(2).getValue(), deep.get(2).getValue());
        assertNotSame(original.get(0), deep.get(0));
        assertNotSame(original.get(1), deep.get(1));
        assertNotSame(original.get(2), deep.get(2));
    }

    @Test
    public void givenDeepCopy_whenElementChanges_thenOriginalListIsNotChanged() {
        ValueObject object1 = new ValueObject("first");
        ValueObject object2 = new ValueObject("second");
        ValueObject object3 = new ValueObject("third");

        ArrayList<ValueObject> original = new ArrayList<>();
        original.add(object1);
        original.add(object2);
        original.add(object3);

        ArrayList<ValueObject> deep = new ArrayList<>();
        for (int index = 0; index < original.size(); index++) {
            deep.add(new ValueObject(original.get(index).getValue()));
        }

        deep.add(deep.remove(0));
        deep.get(0).setValue("new first");
        deep.get(1).setValue("new second");
        deep.get(2).setValue("new third");

        assertEquals("first", object1.getValue());
        assertEquals("second", object2.getValue());
        assertEquals("third", object3.getValue());
    }

}
