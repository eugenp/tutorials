package org.baeldung.gson.serialization;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class StringDateMapDeserializer implements JsonDeserializer<Map<String, Date>> {

    private static final Logger logger = LoggerFactory.getLogger(StringDateMapDeserializer.class);

    private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public Map<String, Date> deserialize(JsonElement elem, Type type, JsonDeserializationContext jsonDeserializationContext) {
        System.out.println("Deserializer called");
        logger.info("Deserializer called");
        return elem.getAsJsonObject()
          .entrySet()
          .stream()
          .filter(e -> e.getValue().isJsonPrimitive())
          .filter(e -> e.getValue().getAsJsonPrimitive().isString())
          .collect(Collectors.toMap(Map.Entry::getKey, e -> formatDate(e.getValue())));
    }

    private Date formatDate(JsonElement value) {
        try {
            return format.parse(value.getAsString());
        } catch (ParseException ex) {
            throw new JsonParseException(ex);
        }
    }

}
