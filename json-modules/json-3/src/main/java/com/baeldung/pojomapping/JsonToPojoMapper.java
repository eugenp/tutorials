package com.baeldung.pojomapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.JSONObject;

public class JsonToPojoMapper {

    public static User mapManually(JSONObject jsonObject) {
        User user = new User();
        user.setName(jsonObject.getString("name"));
        user.setAge(jsonObject.getInt("age"));
        user.setEmail(jsonObject.getString("email"));

        JSONObject addressObject = jsonObject.getJSONObject("address");
        Address address = new Address();
        address.setCity(addressObject.getString("city"));
        address.setPostalCode(addressObject.getString("postalCode"));
        user.setAddress(address);

        return user;
    }

    public static User mapWithJackson(JSONObject jsonObject) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonObject.toString(), User.class);
        } catch (Exception e) {
            return null;
        }
    }

    public static User mapWithGson(JSONObject jsonObject) {
        Gson gson = new Gson();
        return gson.fromJson(jsonObject.toString(), User.class);
    }
}