package com.baeldung.deepvsshallowcopy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ObjectCopyUnitTest {

    DeepObject subject;

    @BeforeEach
    void init() {
        List<ObjectAttribute> attributeList = new ArrayList<>();
        attributeList.add(new ObjectAttribute("Type", "Wobbly"));
        attributeList.add(new ObjectAttribute("Texture", "Bumpy"));

        ObjectDescription description = new ObjectDescription("Fun Object", attributeList);

        subject = new DeepObject(1, description);
    }

    @Test
    public void whenDeepCopied_thenChildObjectsAreNotShared() throws CloneNotSupportedException {

        DeepObject clone = (DeepObject) subject.clone();

        // these are true for both shallow/deep copy
        assertEquals(subject.getClass(), clone.getClass());
        assertEquals(subject, clone);
        assertNotSame(subject, clone);

        // but only for deep copy is the same true of all nested objects as well
        assertEquals(subject.getDescription()
          .getClass(), clone.getDescription()
          .getClass());
        assertEquals(subject.getDescription(), clone.getDescription());
        assertNotSame(subject.getDescription(), clone.getDescription());
    }
}