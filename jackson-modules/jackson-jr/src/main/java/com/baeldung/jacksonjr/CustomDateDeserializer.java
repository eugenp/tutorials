package com.baeldung.jacksonjr;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.jr.ob.api.ValueReader;
import com.fasterxml.jackson.jr.ob.impl.JSONReader;
import com.fasterxml.jackson.jr.private_.JsonParser;

public class CustomDateDeserializer extends ValueReader {
    private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public CustomDateDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public Object read(JSONReader jsonReader, JsonParser jsonParser) throws IOException {
        return LocalDate.parse(jsonParser.getText(), dtf);
    }
}
