package com.baeldung.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class MySerializationUtils {

    public static <T extends Serializable> byte[] serialize(T obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        oos.close();
        return baos.toByteArray();
    }

    public static <T extends Serializable> T deserialize(byte[] b, Class<T> cl) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object o = ois.readObject();
        return cl.cast(o);
    }

    public static boolean isSerializable(Class<?> it) {
        boolean serializable = it.isPrimitive() || it.isInterface() || Serializable.class.isAssignableFrom(it);
        if (!serializable) {
            return false;
        }
        Field[] declaredFields = it.getDeclaredFields();
        for (Field field : declaredFields) {
            if (Modifier.isVolatile(field.getModifiers()) || Modifier.isTransient(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            Class<?> fieldType = field.getType();
            serializable = isSerializable(fieldType);
        }
        return serializable;
    }
}
