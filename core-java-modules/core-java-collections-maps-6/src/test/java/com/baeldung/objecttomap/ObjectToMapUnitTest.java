package com.baeldung.objecttomap;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.junit.Test;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

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

    @Test
    public void given_UnsortedMap_whenSortingByValueDescending_thenValuesAreInDescendingOrder() {
        Map<String, Integer> unsortedMap = new HashMap<>();
        unsortedMap.put("one", 1);
        unsortedMap.put("three", 3);
        unsortedMap.put("five", 5);
        unsortedMap.put("two", 2);
        unsortedMap.put("four", 4);

        Map<String, Integer> sortedMap = sortMapByValueDescending(unsortedMap);

        assertEquals(5, sortedMap.size());
        final Iterator<Integer> iterator = sortedMap.values().iterator();
        assertEquals(5, (int) iterator.next());
        assertEquals(4, (int) iterator.next());
        assertEquals(3, (int) iterator.next());
        assertEquals(2, (int) iterator.next());
        assertEquals(1, (int) iterator.next());
    }

    @Test
    public void given_UnsortedMap_whenUsingTreeMap_thenKeysAreInDescendingOrder() {
        SortedMap<String, Integer> treeMap = new TreeMap<>(Comparator.reverseOrder());
        treeMap.put("one", 1);
        treeMap.put("three", 3);
        treeMap.put("five", 5);
        treeMap.put("two", 2);
        treeMap.put("four", 4);

        assertEquals(5, treeMap.size());
        final Iterator<String> iterator = treeMap.keySet().iterator();
        assertEquals("two", iterator.next());
        assertEquals("three", iterator.next());
        assertEquals("one", iterator.next());
        assertEquals("four", iterator.next());
        assertEquals("five", iterator.next());
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

    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValueDescending(Map<K, V> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.<K, V>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
