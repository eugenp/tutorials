package com.baeldung.deserializationfilters.utils;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class SerializationUtils {

    private SerializationUtils() {
    }

    public static void serialize(Object object, OutputStream outStream) throws IOException {
        try (ObjectOutputStream objStream = new ObjectOutputStream(outStream)) {
            objStream.writeObject(object);
        }
    }
}
