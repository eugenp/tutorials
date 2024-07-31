package com.baeldung.utility;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtil {

    public static List<JsonObject> getListOfJsonObjectFromFile(String filePath) throws IOException {
        List<JsonObject> lstOfJsonObject = new ArrayList<>();
        try(FileReader fileReader = new FileReader(filePath)) {
            JsonArray jsonArray = JsonParser.parseReader(fileReader).getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                lstOfJsonObject.add(jsonObject);
            }
        }
        return lstOfJsonObject;
    }

    public static List<JSONObject> getJsonObjects(String filePath) throws FileNotFoundException {
        List<JSONObject> jsons = new ArrayList<>();
        try(JSONReader jsonReader = new JSONReader(new FileReader(filePath))) {
            jsonReader.startArray();
            while(jsonReader.hasNext()) {
                jsons.add(jsonReader.readObject(JSONObject.class));
            }
            jsonReader.endArray();
        }
        return jsons;
    }


}
