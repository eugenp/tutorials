package com.baeldung.jsonpointer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonPointer;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonStructure;

public class JsonPointerCrud {

    private JsonStructure jsonStructure = null;

    public JsonPointerCrud(String fileName) throws IOException {

        try (JsonReader reader = Json.createReader(Files.newBufferedReader(Paths.get(fileName)))) {
            jsonStructure = reader.read();
        } catch (FileNotFoundException e) {
            System.out.println("Error to open json file: " + e.getMessage());
        }

    }

    public JsonPointerCrud(InputStream stream) {

        JsonReader reader = Json.createReader(stream);
        jsonStructure = reader.read();
        reader.close();

    }

    public JsonStructure insert(String key, String value) {

        JsonPointer jsonPointer = Json.createPointer("/" + key);
        JsonString jsonValue = Json.createValue(value);
        jsonStructure = jsonPointer.add(jsonStructure, jsonValue);

        return jsonStructure;

    }

    public JsonStructure update(String key, String newValue) {

        JsonPointer jsonPointer = Json.createPointer("/" + key);
        JsonString jsonNewValue = Json.createValue(newValue);
        jsonStructure = jsonPointer.replace(jsonStructure, jsonNewValue);

        return jsonStructure;
    }

    public JsonStructure delete(String key) {

        JsonPointer jsonPointer = Json.createPointer("/" + key);
        jsonPointer.getValue(jsonStructure);
        jsonStructure = jsonPointer.remove(jsonStructure);

        return jsonStructure;

    }

    public String fetchValueFromKey(String key) {
        JsonPointer jsonPointer = Json.createPointer("/" + key);
        JsonString jsonString = (JsonString) jsonPointer.getValue(jsonStructure);

        return jsonString.getString();
    }

    public String fetchListValues(String key) {
        JsonPointer jsonPointer = Json.createPointer("/" + key);
        JsonObject jsonObject = (JsonObject) jsonPointer.getValue(jsonStructure);

        return jsonObject.toString();
    }

    public String fetchFullJSON() {
        JsonPointer jsonPointer = Json.createPointer("");
        JsonObject jsonObject = (JsonObject) jsonPointer.getValue(jsonStructure);

        return jsonObject.toString();

    }

    public boolean check(String key) {
        JsonPointer jsonPointer = Json.createPointer("/" + key);
        boolean found = jsonPointer.containsValue(jsonStructure);

        return found;
    }
}
