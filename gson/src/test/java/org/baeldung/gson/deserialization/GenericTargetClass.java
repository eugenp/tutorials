package org.baeldung.gson.deserialization;

public class GenericTargetClass<INTEGER> {

    public INTEGER intField;

    GenericTargetClass(final INTEGER value) {
        intField = value;
    }

    //

    @Override
    public String toString() {
        return "GenericTargetClass{" + "intField=" + intField + '}';
    }

}
