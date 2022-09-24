package com.baeldung.copy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DeepCopyUnitTest {

    @Test
    public void whenChangePrimitiveType_thenInvisibleToCopiedObject() {
        DeepCopy sourceObject = new DeepCopy();
        DeepCopy copiedObject = new DeepCopy(sourceObject);
        sourceObject.intMember = 100;

        assertEquals(100, sourceObject.intMember);
        assertEquals(1, copiedObject.intMember);
    }

    @Test
    public void whenChangeNonPrimitiveType_thenInVisibleToCopiedObject() {
        DeepCopy sourceObject = new DeepCopy();
        DeepCopy copiedObject = new DeepCopy(sourceObject);
        sourceObject.listMember.add(100);

        assertThat(sourceObject.listMember, contains(1, 100));
        assertThat(copiedObject.listMember, contains(1));
    }
}
