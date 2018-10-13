package org.baeldung.gson.serialization;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        HashMap<String, Date> map = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : elem.getAsJsonObject().entrySet()) {
            JsonElement jsonValue = entry.getValue();
            if (jsonValue.isJsonPrimitive() && jsonValue.getAsJsonPrimitive().isString()) {
                try {
                    Date dt = format.parse(jsonValue.getAsString());
                    map.put(entry.getKey(), dt);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

}
