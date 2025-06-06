package com.baeldung.classtype;

public class ContainerTypeFromTypeParameter<T> {
    private Class<T> clazz;

    public ContainerTypeFromTypeParameter(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Class<T> getClazz() {
        return this.clazz;
    }
}
