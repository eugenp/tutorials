package com.baeldung.deserializationfilters.pojo;

public class NestedSample implements ContextSpecific {
    private static final long serialVersionUID = 1L;

    private Sample optional;

    public NestedSample(Sample optional) {
        this.optional = optional;
    }

    public Sample getOptional() {
        return optional;
    }

    public void setOptional(Sample optional) {
        this.optional = optional;
    }
}
