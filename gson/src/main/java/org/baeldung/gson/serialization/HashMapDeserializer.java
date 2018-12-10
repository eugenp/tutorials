package org.baeldung.gson.serialization;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.baeldung.gson.entities.Employee;

import com.google.gson.*;

public class HashMapDeserializer implements JsonDeserializer<HashMap<String, Object>> {

    @Override
    public HashMap<String, Object> deserialize(JsonElement elem, Type type, JsonDeserializationContext context) throws JsonParseException {
        HashMap<String, Object> map = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : elem.getAsJsonObject().entrySet()) {
            JsonElement jsonValue = entry.getValue();
            Object value = null;
            if (jsonValue.isJsonPrimitive()) {
                value = toPrimitive(jsonValue.getAsJsonPrimitive(), context);
            } else {
                value = context.deserialize(jsonValue, Employee.class);
            }
            map.put(entry.getKey(), value);
        }
        return map;

    }

    private Object toPrimitive(JsonPrimitive jsonValue, JsonDeserializationContext context) {
        if (jsonValue.isBoolean())
            return jsonValue.getAsBoolean();
        else if (jsonValue.isString())
            return jsonValue.getAsString();
        else {
            BigDecimal bigDec = jsonValue.getAsBigDecimal();
            Long l;
            Integer i;
            if ((i = toInteger(bigDec)) != null) {
                return i;
            } else if ((l = toLong(bigDec)) != null) {
                return l;
            } else {
                return bigDec.doubleValue();
            }
        }
    }

    private Long toLong(BigDecimal val) {
        try {
            return val.toBigIntegerExact().longValue();
        } catch (ArithmeticException e) {
            return null;
        }
    }

    private Integer toInteger(BigDecimal val) {
        try {
            return val.intValueExact();
        } catch (ArithmeticException e) {
            return null;
        }
    }

}
