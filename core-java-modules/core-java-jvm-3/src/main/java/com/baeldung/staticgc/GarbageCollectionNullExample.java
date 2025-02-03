package com.baeldung.staticgc;

public class GarbageCollectionNullExample {

    public static final String METHOD_NAME = "printValue";

public static void main(String[] args) throws InterruptedException {
    CustomClassloader loader;
    Class<?> clazz;
    Object instance;
    try {
        final String className = "com.baeldung.classloader.GarbageCollectedStaticFieldHolder";
        loader = new CustomClassloader(GarbageCollectionNullExample.class.getClassLoader());
        clazz = loader.loadClass(className);
        instance = clazz.getConstructor().newInstance();
        clazz.getMethod(METHOD_NAME).invoke(instance);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    loader = null;
    clazz = null;
    instance = null;
    System.gc();
    Thread.sleep(1000);
}


}
