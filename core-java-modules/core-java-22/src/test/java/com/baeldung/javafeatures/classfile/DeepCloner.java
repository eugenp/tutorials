package com.baeldung.javafeatures.classfile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UncheckedIOException;

public final class DeepCloner {

    public static <T> T clone(T o) {
        if (o == null) {
            return null;
        }

        return deserialize(serialize(o));
    }

    @SuppressWarnings("unchecked")
    private static <T> T deserialize(byte[] payload) {
        try (var ois = new ObjectInputStream(new ByteArrayInputStream(payload))) {
            return (T) ois.readObject();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] serialize(Object o) {
        var baos = new ByteArrayOutputStream();
        try (var oos = new ObjectOutputStream(baos)) {
            oos.writeObject(o);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return baos.toByteArray();
    }

}