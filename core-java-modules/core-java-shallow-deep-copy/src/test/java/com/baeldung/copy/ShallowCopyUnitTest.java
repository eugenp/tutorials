package com.baeldung.copy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ShallowCopyUnitTest {

    @Test
    public void whenChangePrimitiveType_thenInvisibleToCopiedObject() {
        ShallowCopy sourceObject = new ShallowCopy();
        ShallowCopy copiedObject = new ShallowCopy(sourceObject);
        sourceObject.intMember = 100;

        assertEquals(100, sourceObject.intMember);
        assertEquals(1, copiedObject.intMember);
    }

    @Test
    public void whenChangeNonPrimitiveType_thenVisibleToCopiedObject() {
        ShallowCopy sourceObject = new ShallowCopy();
        ShallowCopy copiedObject = new ShallowCopy(sourceObject);
        sourceObject.listMember.add(100);

        assertThat(sourceObject.listMember, contains(1, 100));
        assertThat(copiedObject.listMember, contains(1, 100));
    }
}
