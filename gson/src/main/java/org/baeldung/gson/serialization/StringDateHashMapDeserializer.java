package org.baeldung.gson.serialization;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.baeldung.gson.entities.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class StringDateHashMapDeserializer implements JsonDeserializer<HashMap<String, Date>> {

    private static final Logger logger = LoggerFactory.getLogger(StringDateHashMapDeserializer.class);

    private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public HashMap<String, Date> deserialize(JsonElement elem,
          Type type,
          JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return elem.getAsJsonObject()
          .entrySet()
          .stream()
          .filter(e -> e.getValue().isJsonPrimitive()
            && e.getValue().getAsJsonPrimitive().isString())
          .collect(
            Collectors.toMap(
              e -> e.getKey(),
              e -> {
                  try {
                      return format.parse(e.getValue().getAsString());
                  } catch (ParseException ex) {
                      throw new JsonParseException("Cannot parse date", ex);
                  }
              },
              (v1, v2) -> v1,
              HashMap::new
            )
          );
    }

}
