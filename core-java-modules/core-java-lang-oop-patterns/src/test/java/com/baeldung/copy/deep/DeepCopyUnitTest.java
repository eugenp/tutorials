package com.baeldung.copy.deep;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.baeldung.copy.shallow.Founder;

public class DeepCopyUnitTest {

    @Test
    void whenCloneDeepCopy_thenDataIntegrityPreserved() {

        // create object to be cloned
        Founder founder = new Founder("Eugen", 2011);
        Company original = new Company("Baeldung", founder);

        // create clone and modify
        Company clone = original.clone();
        clone.getFounder()
            .setName("Dan");

        // verify original object remains the same
        assertEquals("Eugen", original.getFounder()
            .getName());
    }

    @Test
    void whenSerializedDeepCopy_thenDataIntegrityPreserved() throws IOException, ClassNotFoundException {

        // create object to be cloned
        Founder founder = new Founder("Eugen", 2011);
        Company original = new Company("Baeldung", founder);

        // create clone from serialization and modify
        Company clone = original.createSerializedClone();

        assertEquals(original, clone);
        clone.getFounder()
            .setName("Dan");
        // verify original object remains the same
        assertEquals("Eugen", original.getFounder()
            .getName());

    }
}
