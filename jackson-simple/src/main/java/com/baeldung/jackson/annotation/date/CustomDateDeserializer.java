package com.baeldung.jackson.annotation.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

public class CustomDateDeserializer extends StdDeserializer<Date> {

    private static final long serialVersionUID = -5451717385630622729L;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    public CustomDateDeserializer() {
        this(null);
    }

    public CustomDateDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public Date deserialize(final JsonParser jsonparser, final DeserializationContext context) throws JacksonException {
        final String date = jsonparser.getString();
        try {
            return formatter.parse(date);
        } catch (final ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
