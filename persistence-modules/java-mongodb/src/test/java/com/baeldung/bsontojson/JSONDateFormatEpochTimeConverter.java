package com.baeldung.bsontojson;

import org.bson.json.Converter;
import org.bson.json.StrictJsonWriter;

/**
 * Convertor to epoch time
 */
public class JSONDateFormatEpochTimeConverter implements Converter<Long> {

    @Override
    public void convert(Long value, StrictJsonWriter writer) {
        writer.writeStartObject();
        writer.writeName("$date");
        writer.writeNumber(String.valueOf(value));
        writer.writeEndObject();
    }
}
