package com.baeldung.gson.serialization;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import com.baeldung.gson.entities.Animal;

public class AnimalDeserializer implements JsonDeserializer<Animal> {
    private String animalTypeElementName;
    private Gson gson;
    private Map<String, Class<? extends Animal>> animalTypeRegistry;

    public AnimalDeserializer(String animalTypeElementName) {
        this.animalTypeElementName = animalTypeElementName;
        this.gson = new Gson();
        this.animalTypeRegistry = new HashMap<>();
    }

    public void registerBarnType(String animalTypeName, Class<? extends Animal> animalType) {
        animalTypeRegistry.put(animalTypeName, animalType);
    }

    public Animal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        JsonObject animalObject = json.getAsJsonObject();
        JsonElement animalTypeElement = animalObject.get(animalTypeElementName);

        Class<? extends Animal> animalType = animalTypeRegistry.get(animalTypeElement.getAsString());
        return gson.fromJson(animalObject, animalType);
    }
}