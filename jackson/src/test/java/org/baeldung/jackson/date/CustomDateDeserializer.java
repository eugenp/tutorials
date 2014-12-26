package org.baeldung.jackson.date;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomDateDeserializer extends JsonDeserializer<Date> {

    private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    @Override
    public Date deserialize(final JsonParser jsonparser, final DeserializationContext context) throws IOException, JsonProcessingException {
        final String date = jsonparser.getText();
        try {
            return formatter.parse(date);
        } catch (final ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
