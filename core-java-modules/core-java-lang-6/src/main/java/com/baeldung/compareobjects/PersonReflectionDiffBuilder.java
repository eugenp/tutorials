package com.baeldung.compareobjects;

import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ReflectionDiffBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PersonReflectionDiffBuilder {
    public static DiffResult compare(Person first, Person second) {
        return new ReflectionDiffBuilder<>(first, second, ToStringStyle.SHORT_PREFIX_STYLE).build();
    }
}
