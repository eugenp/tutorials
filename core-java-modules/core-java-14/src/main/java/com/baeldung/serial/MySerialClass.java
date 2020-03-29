package com.baeldung.serial;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.ObjectStreamField;
import java.io.Serial;
import java.io.Serializable;

/**
 * Class showcasing the usage of the Java 14 @Serial annotation.
 *
 * @author Donato Rimenti
 */
public class MySerialClass implements Serializable {

    @Serial
    private static final ObjectStreamField[] serialPersistentFields = null;

    @Serial
    private static final long serialVersionUID = 1;

    @Serial
    private void writeObject(ObjectOutputStream stream) throws IOException {
        // ...
    }

    @Serial
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        // ...
    }

    @Serial
    private void readObjectNoData() throws ObjectStreamException {
        // ...
    }

    @Serial
    private Object writeReplace() throws ObjectStreamException {
        // ...
        return null;
    }

    @Serial
    private Object readResolve() throws ObjectStreamException {
        // ...
        return null;
    }
}