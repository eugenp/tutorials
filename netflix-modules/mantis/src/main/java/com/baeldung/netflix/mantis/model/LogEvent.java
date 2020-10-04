package com.baeldung.netflix.mantis.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mantisrx.runtime.codec.JsonType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LogEvent implements JsonType {

    private static final ObjectMapper mapper = new ObjectMapper();

    private Long index;
    private String level;
    private String message;

    public LogEvent(String[] parts) {
        this.index = Long.valueOf(parts[0]);
        this.level = parts[1];
        this.message = parts[2];
    }

    public String toJsonString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
