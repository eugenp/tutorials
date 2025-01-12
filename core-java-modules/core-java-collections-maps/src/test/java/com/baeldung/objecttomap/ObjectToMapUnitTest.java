package com.baeldung.objecttomap;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class ObjectToMapUnitTest {
    Employee employee = new Employee("John", 3000.0);

    @Test
    public void givenJavaObject_whenUsingReflection_thenConvertToMap() throws IllegalAccessException {
        Map<String, Object> map = convertUsingReflection(employee);
        assertEquals(employee.getName(), map.get("name"));
        assertEquals(employee.getSalary(), map.get("salary"));
    }

    private Map<String, Object> convertUsingReflection(Object object) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(object));
        }

        return map;
    }

    @Test
    public void givenJavaObject_whenUsingJackson_thenConvertToMap() {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(employee, new TypeReference<Map<String, Object>>() {});
        assertEquals(employee.getName(), map.get("name"));
        assertEquals(employee.getSalary(), map.get("salary"));
    }

    @Test
    public void givenJavaObject_whenUsingGson_thenConvertToMap() {
        Gson gson = new Gson();
        String json = gson.toJson(employee);
        Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
        assertEquals(employee.getName(), map.get("name"));
        assertEquals(employee.getSalary(), map.get("salary"));
    }

    private static class Employee {
        private String name;
        private Double salary;

        public Employee(String name, Double salary) {
            this.name = name;
            this.salary = salary;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getSalary() {
            return salary;
        }

        public void setSalary(Double age) {
            this.salary = salary;
        }
    }

}
