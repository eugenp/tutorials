package com.baeldung.jackson.whatsnew;

import tools.jackson.core.json.JsonWriteFeature;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.cfg.DateTimeFeature;
import tools.jackson.databind.json.JsonMapper;

public class MapperFactory {

    public static JsonMapper getMapper() {
        return JsonMapper.builder()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .disable(JsonWriteFeature.ESCAPE_NON_ASCII)
            .configure(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .build();
    }

    public static JsonMapper rebuildPretty(JsonMapper jsonMapper) {
        return jsonMapper.rebuild()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .build();
    }

}
