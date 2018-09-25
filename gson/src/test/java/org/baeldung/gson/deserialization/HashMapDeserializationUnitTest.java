package org.baeldung.gson.deserialization;

import java.lang.reflect.Type;
import java.util.HashMap;

import org.baeldung.gson.entities.Employee;
import org.baeldung.gson.serialization.HashMapDeserializer;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

public class HashMapDeserializationUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(HashMapDeserializationUnitTest.class);

    @Test
    public void whenUsingHashMapClass_thenShouldReturnMapWithDefaultClasses() {

        String jsonString = "{'employee.name':'Bob','employee.salary':10000, 'employee.active':true, "
                + "'employee':{'id':10, 'name': 'Bob Willis', 'address':'London'}}";

        Gson gson = new Gson();
        HashMap map = gson.fromJson(jsonString, HashMap.class);

        logger.info("The converted map: {}", map);
        Assert.assertEquals(4, map.size());
        Assert.assertEquals(Double.class, map.get("employee.salary").getClass());
        Assert.assertEquals(LinkedTreeMap.class, map.get("employee").getClass());

    }

    @Test(expected = JsonSyntaxException.class)
    public void whenUsingJsonStringWithDuplicateKey_thenShouldThrowJsonSyntaxException() {

        String jsonString = "{'employee.name':'Bob', 'employee.name':'Jenny','employee.salary':10000, "
                + "'employee.active':true, " + "'employee':{'id':10, 'name': 'Bob Willis', 'address':'London'}}";

        Gson gson = new Gson();
        HashMap map = gson.fromJson(jsonString, HashMap.class);

        logger.info("The converted map: {}", map);
    }

    @Test
    public void whenUsingTypeToken_thenShouldReturnMapWithProperClass() {

        String jsonString = "{'Bob':{'id':10, 'name': 'Bob Willis', 'address':'UK'},"
                + "'Jenny':{'id':10, 'name': 'Jenny McCarthy', 'address':'USA'}, "
                + "'Steve':{'id':10, 'name': 'Steven Waugh', 'address':'Australia'}}";

        Gson gson = new Gson();
        Type empMapType = new TypeToken<HashMap<String, Employee>>() {
        }.getType();
        HashMap<String, Employee> nameEmployeeMap = gson.fromJson(jsonString, empMapType);

        logger.info("The converted map: {}", nameEmployeeMap);
        Assert.assertEquals(3, nameEmployeeMap.size());
        Assert.assertEquals(Employee.class, nameEmployeeMap.get("Bob").getClass());
    }

    @Test
    public void whenUsingCustomDeserializer_thenShouldReturnMapWithProperClass() {

        String jsonString = "{'employee.name':'Bob','employee.salary':10000, 'employee.active':true, "
                + "'employee':{'id':10, 'name': 'Bob Willis', 'address':'London'}}";

        Type type = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        Gson gson = new GsonBuilder().registerTypeAdapter(type, new HashMapDeserializer()).create();
        HashMap<String, Object> blendedMap = gson.fromJson(jsonString, type);

        logger.info("The converted map: {}", blendedMap);
        Assert.assertEquals(4, blendedMap.size());
        Assert.assertEquals(Integer.class, blendedMap.get("employee.salary").getClass());
        Assert.assertEquals(Employee.class, blendedMap.get("employee").getClass());

    }

}
