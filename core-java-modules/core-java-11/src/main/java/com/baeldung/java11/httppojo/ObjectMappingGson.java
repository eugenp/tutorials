package com.baeldung.java11.httppojo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.List;

public class ObjectMappingGson {
    List<TodoApp> readValue(String content) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(content, new TypeToken<List<TodoApp>>() {
        }.getType());

    }

}