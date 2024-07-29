package com.baeldung.shallow_deep;

public class DeepCopyUtil { 
    private static final Gson gson = new Gson(); 
    public static <T> T deepCopy(T object, Class<T> className) { 
        return gson.fromJson(gson.toJson(object), className); 
    } 
}