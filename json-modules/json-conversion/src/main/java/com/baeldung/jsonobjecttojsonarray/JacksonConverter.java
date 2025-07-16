package com.baeldung.jsonobjecttojsonarray;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONObject;

public class JacksonConverter {

    ArrayNode convertToArray(JSONObject jsonObject) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.convertValue(jsonObject.toMap(), JsonNode.class);

        ArrayNode result = mapper.createArrayNode();
        jsonNode.fields().forEachRemaining(entry -> {
            ObjectNode obj = mapper.createObjectNode();
            obj.put("key", entry.getKey());
            obj.set("value", entry.getValue());
            result.add(obj);
        });

        return result;
    }
}
