package com.baeldung.escape;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.json.JSONObject;

class JsonEscape {

    String escapeJson(String input) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", input);
        return jsonObject.toString();
    }

    String escapeGson(String input) {
        JsonObject gsonObject = new JsonObject();
        gsonObject.addProperty("message", input);
        return gsonObject.toString();
    }

    String escapeJackson(String input) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(new Payload(input));
    }

    static class Payload {
        String message;

        Payload(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
