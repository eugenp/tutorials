package com.baeldung.gettersetter;

public class ExampleService {

    public Long getId(IdAndName idAndName) {
        return idAndName.getId();
    }

    public String getName(IdAndName idAndName) {
        return idAndName.getName();
    }

    public String getSuperComplicatedField(NonSimpleClass nonSimpleClass) {
        return nonSimpleClass.getSuperComplicatedField();
    }

}
