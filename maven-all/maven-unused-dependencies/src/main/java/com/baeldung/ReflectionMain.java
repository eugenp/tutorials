package com.baeldung;

public class ReflectionMain {

    public static void main(String[] args) {
        ClassLoader classLoader = Main.class.getClassLoader();
        try {
            System.out.println(classLoader.loadClass("org.apache.commons.collections.CollectionUtils")
                .getSimpleName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}