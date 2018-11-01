package org.baeldung.gson.deserialization;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.baeldung.gson.entities.Employee;
import org.baeldung.gson.serialization.MapDeserializer;
import org.baeldung.gson.serialization.StringDateMapDeserializer;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

public class MapDeserializationUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(MapDeserializationUnitTest.class);

    @Test
    public void whenUsingMapClass_thenShouldReturnMapWithDefaultClasses() {

        String jsonString = "{'employee.name':'Bob','employee.salary':10000, 'employee.active':true, "
          + "'employee':{'id':10, 'name': 'Bob Willis', 'address':'London'}}";

        Gson gson = new Gson();
        Map map = gson.fromJson(jsonString, Map.class);

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
        Map map = gson.fromJson(jsonString, Map.class);

        logger.info("The converted map: {}", map);
    }

    @Test
    public void whenUsingTypeToken_thenShouldReturnMapWithProperClass() {

        String jsonString = "{'Bob':{'id':10, 'name': 'Bob Willis', 'address':'UK'},"
          + "'Jenny':{'id':10, 'name': 'Jenny McCarthy', 'address':'USA'}, "
          + "'Steve':{'id':10, 'name': 'Steven Waugh', 'address':'Australia'}}";

        Gson gson = new Gson();
        Type empMapType = new TypeToken<Map<String, Employee>>(){}.getType();
        Map<String, Employee> nameEmployeeMap = gson.fromJson(jsonString, empMapType);

        logger.info("The converted map: {}", nameEmployeeMap);
        Assert.assertEquals(3, nameEmployeeMap.size());
        Assert.assertEquals(Employee.class, nameEmployeeMap.get("Bob").getClass());
    }

    @Test
    public void whenUsingCustomDeserializer_thenShouldReturnMapWithProperClass() {

        String jsonString = "{'employee.name':'Bob','employee.salary':10000, 'employee.active':true, "
          + "'employee':{'id':10, 'name': 'Bob Willis', 'address':'London'}}";

        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Gson gson = new GsonBuilder()
          .registerTypeAdapter(type, new MapDeserializer())
          .create();
        Map<String, Object> blendedMap = gson.fromJson(jsonString, type);

        logger.info("The converted map: {}", blendedMap);
        Assert.assertEquals(4, blendedMap.size());
        Assert.assertEquals(Integer.class, blendedMap.get("employee.salary").getClass());
        Assert.assertEquals(Employee.class, blendedMap.get("employee").getClass());

    }

    @Test
    public void whenUsingCustomDateDeserializer_thenShouldReturnMapWithDate() {
        String jsonString = "{'Bob': '2017/06/01', 'Jennie':'2015/01/03'}";
        Type type = new TypeToken<Map<String, Date>>(){}.getType();
        Gson gson = new GsonBuilder()
          .registerTypeAdapter(type, new StringDateMapDeserializer())
          .create();
        Map<String, Date> empJoiningDateMap = gson.fromJson(jsonString, type);

        logger.info("The converted map: {}", empJoiningDateMap);
        logger.info("The map class {}", empJoiningDateMap.getClass());
        Assert.assertEquals(2, empJoiningDateMap.size());
        Assert.assertEquals(Date.class, empJoiningDateMap.get("Bob").getClass());
        Date dt = null;
        try {
            dt = DateUtils.parseDate("2017-06-01", "yyyy-MM-dd");
            Assert.assertEquals(dt, empJoiningDateMap.get("Bob"));
        } catch (ParseException e) {
            logger.error("Could not parse date", e);
        }
    }

}
