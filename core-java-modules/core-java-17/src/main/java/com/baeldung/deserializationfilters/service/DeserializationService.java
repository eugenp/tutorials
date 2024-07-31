package com.baeldung.deserializationfilters.service;

import java.io.InputStream;
import java.util.Set;

import com.baeldung.deserializationfilters.pojo.ContextSpecific;

public interface DeserializationService {

    Set<ContextSpecific> process(InputStream... inputStreams);
}
