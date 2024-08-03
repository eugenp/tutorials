package com.baeldung.javafeatures.classfile;

import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

class DeepCloneWithAgentTest {

    static final Exception INIT_EX;

    static {
        Exception ex;
        try {
            InstrumentationSupport.getInstrumentation()
            .addTransformer(new SerializableTransformer());

            ex = null;
        } catch (Exception e) {
            ex = e;
        }
        INIT_EX = ex;
    }

    @Test
    void given_non_serializable_class_will_deep_clone() {
        assumeTrue(InstrumentationSupport.REQUIRED_VM_ARGS, INIT_EX == null);

        var original = new Data<>("id", System.currentTimeMillis(), Calendar.getInstance());

        var cloned = DeepCloner.clone(original);

        assertNotSame(original, cloned);
        assertEquals(original, cloned);

        cloned.payload.set(Calendar.YEAR, 1999);

        assertNotEquals(original, cloned);
    }

}
