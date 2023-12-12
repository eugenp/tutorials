package com.baeldung.deserializationfilters.service;

import java.io.InputStream;
import java.util.Set;

import com.baeldung.deserializationfilters.pojo.ContextSpecific;
import com.baeldung.deserializationfilters.utils.DeserializationUtils;

public class LimitedArrayService implements DeserializationService {

    @Override
    public Set<ContextSpecific> process(InputStream... inputStreams) {
        return DeserializationUtils.deserializeIntoSet(inputStreams);
    }
}
