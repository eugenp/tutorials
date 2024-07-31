package com.baeldung.tieredcompilation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlFormatter implements Formatter {

    private static final XmlMapper mapper = new XmlMapper();

    @Override
    public <T> String format(T object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

}
