package org.baeldung.gson.deserialization;

public class GenericFoo<INTEGER> {

    public INTEGER intField;

    GenericFoo(final INTEGER value) {
        intField = value;
    }

    //

    @Override
    public String toString() {
        return "GenericTargetClass{" + "intField=" + intField + '}';
    }

}
