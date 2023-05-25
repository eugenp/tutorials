package com.baeldung.staticgc;

public class GarbageCollectionExplicitExample {

    public static final String METHOD_NAME = "printValue";

    public static void main(String[] args) throws InterruptedException {
        loadClass();
        System.gc();
        Thread.sleep(1000);
    }


    /**
     * The method loads a class and creates its instance. After the invocation of this method all the local variables go outside the scope.
     */
    private static void loadClass() {
        try {
            final String className = "com.baeldung.classloader.GarbageCollectedStaticFieldHolder";
            CustomClassloader loader = new CustomClassloader(GarbageCollectionExplicitExample.class.getClassLoader());
            Class<?> clazz = loader.loadClass(className);
            Object instance = clazz.getConstructor().newInstance();
            clazz.getMethod(METHOD_NAME).invoke(instance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
