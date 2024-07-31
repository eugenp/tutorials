package com.baeldung.deserialization.vulnerabilities;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BadThingUnitTest {

    @Test
    @DisplayName("When a BadThing object is deserialized, then code execution in MyCustomAttackObject is run.")
    public void givenABadThingObject_whenItsDeserialized_thenExecutionIsRun() throws Exception {
        BadThing bt = new BadThing();

        bt.looselyDefinedThing = new MyCustomAttackObject();
        bt.methodName = "methodThatTriggersAttack";

        byte[] serializedObject = serialize(bt);

        try (InputStream bis = new ByteArrayInputStream(serializedObject);
             ObjectInputStream ois = new ObjectInputStream(bis)) {

            ois.readObject(); // malicious code is run
        }
    }

    private static byte[] serialize(Object object) throws Exception {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {

            oos.writeObject(object);
            oos.flush();
            return bos.toByteArray();
        }
    }
}