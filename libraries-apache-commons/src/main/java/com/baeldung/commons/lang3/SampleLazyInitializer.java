package com.baeldung.commons.lang3;

import org.apache.commons.lang3.concurrent.LazyInitializer;

public class SampleLazyInitializer extends LazyInitializer<SampleObject> {

    @Override
    protected SampleObject initialize() {
        return new SampleObject();
    }
}