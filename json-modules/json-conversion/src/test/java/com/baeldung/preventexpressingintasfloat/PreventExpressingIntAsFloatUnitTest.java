package com.baeldung.preventexpressingintasfloat;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PreventExpressingIntAsFloatUnitTest {

    public String jsonString = "[{\"id\":4077395,\"field_id\":242566,\"body\":\"\"}, " +
            "{\"id\":4077398,\"field_id\":242569,\"body\":[[273019,0],[273020,1],[273021,0]]}, " +
            "{\"id\":4077399,\"field_id\":242570,\"body\":[[273022,0],[273023,1],[273024,0]]}]";
    public String expectedOutput = "[{body=, field_id=242566, id=4077395}, " +
            "{body=[[273019, 0], [273020, 1], [273021, 0]], field_id=242569, id=4077398}, " +
            "{body=[[273022, 0], [273023, 1], [273024, 0]], field_id=242570, id=4077399}]";
    public String defaultOutput = "[{body=, field_id=242566.0, id=4077395.0}, " +
            "{body=[[273019.0, 0.0], [273020.0, 1.0], [273021.0, 0.0]], field_id=242569.0, id=4077398.0}, " +
            "{body=[[273022.0, 0.0], [273023.0, 1.0], [273024.0, 0.0]], field_id=242570.0, id=4077399.0}]";

    @Test
    public void givenJsonString_whenUsingSetObjectToNumberStrategyMethod_thenValidateOutput() {
        Gson defaultGson = new Gson();
        ArrayList<Hashtable<String, Object>> defaultResponses = defaultGson.fromJson(jsonString, new TypeToken<ArrayList<Hashtable<String, Object>>>() {
        }.getType());

        Gson customGson = new GsonBuilder().setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).create();
        ArrayList<Hashtable<String, Object>> customResponses = customGson.fromJson(jsonString, new TypeToken<ArrayList<Hashtable<String, Object>>>() {
        }.getType());

        assertEquals(defaultOutput, defaultResponses.toString());
        assertEquals(expectedOutput, customResponses.toString());
    }
}
