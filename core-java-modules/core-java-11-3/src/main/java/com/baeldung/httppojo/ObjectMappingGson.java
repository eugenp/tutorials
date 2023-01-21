package com.baeldung.httppojo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.List;

public class ObjectMappingGson {
    List<Todo> readValue(String content) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(content, new TypeToken<List<Todo>>() {
        }.getType());

    }

}