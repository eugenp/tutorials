package com.baeldung.lazy;

public class ClassWithHeavyInitialization {
    private ClassWithHeavyInitialization() {
    }

    private static class LazyHolder {
        public static final ClassWithHeavyInitialization INSTANCE = new ClassWithHeavyInitialization();
    }

    public static ClassWithHeavyInitialization getInstance() {
        return LazyHolder.INSTANCE;
    }
}