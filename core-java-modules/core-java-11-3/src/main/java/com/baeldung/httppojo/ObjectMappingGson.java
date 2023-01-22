package com.baeldung.httppojo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.List;

public class ObjectMappingGson {
    Gson gson = new GsonBuilder().create();

    List<Todo> readValue(String content) {

        return gson.fromJson(content, new TypeToken<List<Todo>>() {
        }.getType());

    }

}