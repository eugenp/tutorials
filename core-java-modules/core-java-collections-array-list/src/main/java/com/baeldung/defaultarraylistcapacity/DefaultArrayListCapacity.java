package com.baeldung.defaultarraylistcapacity;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class DefaultArrayListCapacity {

    public static int getDefaultCapacity(ArrayList<?> arrayList) throws Exception {

        if (arrayList == null) {
            return 0;
        }

        Field field = ArrayList.class.getDeclaredField("elementData");
        field.setAccessible(true);

        return ((Object[]) field.get(arrayList)).length;
    }

}
