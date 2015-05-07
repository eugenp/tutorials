package org.baeldung.jackson.date;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomDateSerializer extends JsonSerializer<Date> {

    private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    @Override
    public void serialize(final Date value, final JsonGenerator gen, final SerializerProvider arg2) throws IOException, JsonProcessingException {
        gen.writeString(formatter.format(value));
    }
}
