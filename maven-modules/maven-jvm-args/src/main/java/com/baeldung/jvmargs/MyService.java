package com.baeldung.jvmargs;

import java.lang.String;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
class MyService {

    int getLength() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        ArrayList<String> arr = new ArrayList<>();
        Field sizeField = ArrayList.class.getDeclaredField("size");
        sizeField.setAccessible(true);
        return (int) sizeField.get(arr);
    }

}