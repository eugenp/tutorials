package org.baeldung.jackson.bidirection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomListSerializer extends JsonSerializer<List<ItemWithSerializer>> {

    @Override
    public void serialize(final List<ItemWithSerializer> items, final JsonGenerator generator, final SerializerProvider provider) throws IOException, JsonProcessingException {
        final List<Integer> ids = new ArrayList<Integer>();
        for (final ItemWithSerializer item : items) {
            ids.add(item.id);
        }
        generator.writeObject(ids);
    }
}
