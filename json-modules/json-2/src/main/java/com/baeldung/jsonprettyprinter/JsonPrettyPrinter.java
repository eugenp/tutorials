package core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/*
 * Class to print string JSON to well-formatted JSON using Jackson and Gson.
 */
public class JsonPrettyPrinter {
    private ObjectMapper mapper = new ObjectMapper();

    public String prettyPrintJsonUsingDefaultPrettyPrinter(String uglyJsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Object jsonObject = objectMapper.readValue(uglyJsonString, Object.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
    }

    public String prettyPrintUsingGlobalSetting(String uglyJsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        Object jsonObject = mapper.readValue(uglyJsonString, Object.class);
        return mapper.writeValueAsString(jsonObject);
    }

    public String prettyPrintUsingGson(String uglyJsonString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Object jsonObject = gson.fromJson(uglyJsonString, Object.class);
        return gson.toJson(jsonObject);
    }
}
