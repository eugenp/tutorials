package com.baeldung.deepvsshallowcopy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ObjectCopyUtils {
    private ObjectCopyUtils() {
        throw new IllegalStateException("Utility class");
    }

    
    @SuppressWarnings("unchecked")
    public static <T> T shallowCopy(T object) throws CloneNotSupportedException {
        if (object instanceof Cloneable) {
            return (T) object.getClass().getMethod("clone").invoke(object);
        } else {
            throw new CloneNotSupportedException();
        }
    }

    
    @SuppressWarnings("unchecked")
    public static <T> T deepCopy(T object) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        objectOutputStream.close();
        outputStream.close();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        T clone = (T) objectInputStream.readObject();
        objectInputStream.close();
        inputStream.close();

        return clone;
    }
    
    // standard setters and getters
}
