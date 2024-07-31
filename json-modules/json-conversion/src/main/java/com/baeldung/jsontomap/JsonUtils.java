package com.baeldung.jsontomap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class JsonUtils {
    public static Map<String, Object> jsonFileToMap(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(path), new TypeReference<Map<String, Object>>() {});
    }

    public static Map<String, Object> jsonFileToMapGson(String path) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(new FileReader(path), new TypeToken<Map<String, Object>>() {}.getType());
    }
}
