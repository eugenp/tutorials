package com.baeldung.diffstringstringbuffer;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class MemoryAddress {

    public long getMemoryAddress(Object object) {
        Object[] array = new Object[]{object};
        Unsafe unsafe = null;
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        assert unsafe != null;
        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        int addressSize = unsafe.addressSize();
        long objectAddress = 0;
        if (addressSize == 4) {
            objectAddress = unsafe.getInt(array, baseOffset);
        } else {
            if (addressSize == 8) {
                objectAddress = unsafe.getLong(array, baseOffset);
            } else {
                throw new Error("Error: Size not supported: " + addressSize);
            }
        }
        return objectAddress;
    }
}
