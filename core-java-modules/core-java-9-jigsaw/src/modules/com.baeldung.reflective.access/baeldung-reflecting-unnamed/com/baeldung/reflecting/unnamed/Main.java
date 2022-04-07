package com.baeldung.reflecting.unnamed;

import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = StringBuilder.class;
        Field f = clazz.getDeclaredField("serialVersionUID");
        f.setAccessible(true);
        Object value = f.get(null);
        System.out.println(value);
    }
}