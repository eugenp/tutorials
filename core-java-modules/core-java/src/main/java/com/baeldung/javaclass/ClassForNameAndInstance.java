package com.baeldung.javaclass;

public class ClassForNameAndInstance {
    public Class createClassForName(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

    public Object createNewInstance(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        return Class.forName(className).newInstance();
    }
}
