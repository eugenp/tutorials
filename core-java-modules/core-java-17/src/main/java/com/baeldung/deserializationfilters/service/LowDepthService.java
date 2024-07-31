package com.baeldung.deserializationfilters.service;

import java.io.InputStream;
import java.io.ObjectInputFilter;
import java.util.Set;

import com.baeldung.deserializationfilters.pojo.ContextSpecific;
import com.baeldung.deserializationfilters.utils.DeserializationUtils;

public class LowDepthService implements DeserializationService {

    public Set<ContextSpecific> process(ObjectInputFilter filter, InputStream... inputStreams) {
        return DeserializationUtils.deserializeIntoSet(filter, inputStreams);
    }

    @Override
    public Set<ContextSpecific> process(InputStream... inputStreams) {
        return process(null, inputStreams);
    }
}
