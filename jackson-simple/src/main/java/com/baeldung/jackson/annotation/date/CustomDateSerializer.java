package com.baeldung.jackson.annotation.date;

import java.text.SimpleDateFormat;
import java.util.Date;

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

public class CustomDateSerializer extends StdSerializer<Date> {

    private static final long serialVersionUID = -2894356342227378312L;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    public CustomDateSerializer() {
        this(null);
    }

    public CustomDateSerializer(final Class<Date> t) {
        super(t);
    }

    @Override
    public void serialize(final Date value, final JsonGenerator gen, final SerializationContext arg2) throws JacksonException {
        gen.writeString(formatter.format(value));
    }
}
