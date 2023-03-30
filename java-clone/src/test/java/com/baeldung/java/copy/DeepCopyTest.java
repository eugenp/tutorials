package com.baeldung.java.copy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DeepCopyTest {

    Logger logger = LoggerFactory.getLogger(DeepCopyTest.class);

    @Test
    void whenUpdatingClonedObject_thenDoNotReplicateChanges() throws CloneNotSupportedException {
        DeepCopy original = new DeepCopy();
        original.primitive = 15;
        original.immutable = "A";
        original.mapObject.put("A", "B");
        original.mapObject.put("C", "D");

        DeepCopy copy = (DeepCopy) original.clone();

        copy.primitive = 20;
        copy.immutable = "B";
        copy.mapObject.put("E", "F");

        logger.info(original.toString());
        logger.info(copy.toString());
    }

}