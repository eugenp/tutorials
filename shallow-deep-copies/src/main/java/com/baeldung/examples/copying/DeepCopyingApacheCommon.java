package com.baeldung.examples.copying;

import org.apache.commons.lang3.SerializationUtils;

public class DeepCopyingApacheCommon implements CopyExample<Person> {

    @Override
    public Person copy(Person source) {
        return SerializationUtils.clone(source);
    }

}
