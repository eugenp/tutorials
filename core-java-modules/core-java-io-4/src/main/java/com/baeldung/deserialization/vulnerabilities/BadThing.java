package com.baeldung.deserialization.vulnerabilities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;

public class BadThing implements Serializable {
    private static final long serialVersionUID = 0L;

    Object looselyDefinedThing;
    String methodName;

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        try {
            Method method = looselyDefinedThing.getClass().getMethod(methodName);
            method.invoke(looselyDefinedThing);
        } catch (Exception e) {
            // handle error...
        }
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }
}
