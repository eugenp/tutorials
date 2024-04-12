package com.baeldung.uncheckedconversion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class UncheckedConversion {
    public static List getRawList() {
        List result = new ArrayList();
        result.add("I am the 1st String.");
        result.add("I am the 2nd String.");
        result.add("I am the 3rd String.");
        return result;
    }

    public static List getRawListWithMixedTypes() {
        List result = new ArrayList();
        result.add("I am the 1st String.");
        result.add("I am the 2nd String.");
        result.add("I am the 3rd String.");
        result.add(new Date());
        return result;
    }

    public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> rawCollection) {
        List<T> result = new ArrayList<>(rawCollection.size());
        for (Object o : rawCollection) {
            try {
                result.add(clazz.cast(o));
            } catch (ClassCastException e) {
                // log the exception or other error handling
            }
        }
        return result;
    }

    public static <T> List<T> castList2(Class<? extends T> clazz, Collection<?> rawCollection) throws ClassCastException {
        List<T> result = new ArrayList<>(rawCollection.size());
        for (Object o : rawCollection) {
            result.add(clazz.cast(o));
        }
        return result;
    }
}
