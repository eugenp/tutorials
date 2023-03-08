package com.baeldung.deep_shallow_copy_2;

import org.junit.jupiter.api.Assertions;

import com.baeldung.deep_shallow_copy_2.ForCopying;
import org.junit.jupiter.api.Test;

class CopyingUnitTest {

    @Test
    public void givenForCopyingInstance_whenCreatingDeepAndShallowCopies_thenDeepAndShallowCopiesEqualsToOriginal() {
        ForCopying originalObject = new ForCopying();
        originalObject.setId(12);
        originalObject.setName("Alex");
        ForCopying deepCopy = new ForCopying();
        deepCopy.setId(12);
        deepCopy.setName(new String("Alex"));
        ForCopying shallowCopy = new ForCopying();
        shallowCopy.setName(originalObject.getName());
        shallowCopy.setId(originalObject.getId());
        Assertions.assertEquals(originalObject, deepCopy);
        Assertions.assertEquals(originalObject, shallowCopy);
    }
}