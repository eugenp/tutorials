package com.baeldung.netflix.mantis.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mantisrx.runtime.codec.JsonType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LogAggregate implements JsonType {

    private static final ObjectMapper mapper = new ObjectMapper();

    private Integer count;
    private String level;

    public String toJsonString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
