package com.baeldung.generics;

public class SenderServiceReflection<T extends Sender> {

    private final Class<T> clazz;

    public SenderServiceReflection(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T createInstance() {
        try {
            return clazz.getDeclaredConstructor()
                .newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error while creating an instance.");
        }
    }

}
