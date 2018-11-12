package org.baeldung.gson.serialization;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.baeldung.gson.entities.Employee;

import com.google.gson.*;

public class MapDeserializer implements JsonDeserializer<Map<String, Object>> {

    @Override
    public Map<String, Object> deserialize(JsonElement elem, Type type, JsonDeserializationContext context) throws JsonParseException {

        return elem.getAsJsonObject()
          .entrySet()
          .stream()
          .collect(Collectors.toMap(
            Map.Entry::getKey,
            e -> e.getValue().isJsonPrimitive() ?
              toPrimitive(e.getValue().getAsJsonPrimitive(), context)
              : context.deserialize(e.getValue(), Employee.class)
          ));
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
