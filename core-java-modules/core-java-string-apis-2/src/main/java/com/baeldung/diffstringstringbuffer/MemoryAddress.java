package com.baeldung.diffstringstringbuffer;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class MemoryAddress {
    public long getMemoryAddress(Object object) {
        Object[] arrayOfObject = new Object[]{object};
        Unsafe unsafe = null;
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        assert unsafe != null;
        long arrayBaseOffset = unsafe.arrayBaseOffset(Object[].class);
        int addressSize = unsafe.addressSize();
        long objectAddress;
        if (addressSize == 4) {
            objectAddress = unsafe.getInt(arrayOfObject, arrayBaseOffset);
        } else {
            if (addressSize == 8) {
                objectAddress = unsafe.getLong(arrayOfObject, arrayBaseOffset);
            } else {
                throw new Error("Error: Size not supported: " + addressSize);
            }
        }
        return objectAddress;
    }
}
