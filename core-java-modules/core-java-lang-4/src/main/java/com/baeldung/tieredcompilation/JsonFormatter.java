package com.baeldung.tieredcompilation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class JsonFormatter implements Formatter {

    private static final JsonMapper mapper = new JsonMapper();

    @Override
    public String format(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

}
