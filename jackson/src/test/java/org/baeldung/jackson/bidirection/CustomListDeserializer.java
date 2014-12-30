package org.baeldung.jackson.bidirection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomListDeserializer extends JsonDeserializer<List<ItemWithSerializer>> {

    @Override
    public List<ItemWithSerializer> deserialize(final JsonParser jsonparser, final DeserializationContext context) throws IOException, JsonProcessingException {
        return new ArrayList<ItemWithSerializer>();
    }

}