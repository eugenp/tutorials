package com.baeldung.java.copy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ShallowCopyTest {

    Logger logger = LoggerFactory.getLogger(ShallowCopyTest.class);

    @Test
    void whenUpdatingClonedObject_thenReplicateChanges() throws CloneNotSupportedException {
        ShallowCopy original = new ShallowCopy();
        original.primitive = 15;
        original.immutable = "A";
        original.mapObject.put("A", "B");
        original.mapObject.put("C", "D");

        ShallowCopy copy = (ShallowCopy) original.clone();

        copy.primitive = 20;
        copy.immutable = "B";
        copy.mapObject.put("E", "F");

        logger.info(original.toString());
        logger.info(copy.toString());
    }
}