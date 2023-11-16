package com.baeldung.deserializationfilters.utils;

import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputFilter;
import java.io.ObjectInputStream;
import java.util.Set;
import java.util.TreeSet;

import com.baeldung.deserializationfilters.pojo.ContextSpecific;

public class DeserializationUtils {
    private DeserializationUtils() {
    }

    public static Object deserialize(InputStream inStream) {
        return deserialize(inStream, null);
    }

    public static Object deserialize(InputStream inStream, ObjectInputFilter filter) {
        try (ObjectInputStream in = new ObjectInputStream(inStream)) {
            if (filter != null) {
                in.setObjectInputFilter(filter);
            }
            return in.readObject();
        } catch (InvalidClassException e) {
            return null;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Set<ContextSpecific> deserializeIntoSet(InputStream... inputStreams) {
        return deserializeIntoSet(null, inputStreams);
    }

    public static Set<ContextSpecific> deserializeIntoSet(ObjectInputFilter filter, InputStream... inputStreams) {
        Set<ContextSpecific> set = new TreeSet<>();

        for (InputStream inputStream : inputStreams) {
            Object object = deserialize(inputStream, filter);
            if (object != null) {
                set.add((ContextSpecific) object);
            }
        }

        return set;
    }
}
