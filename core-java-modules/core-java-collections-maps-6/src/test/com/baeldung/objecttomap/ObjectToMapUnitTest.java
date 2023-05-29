package java.com.baeldung.objecttomap;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import wiremock.com.fasterxml.jackson.core.type.TypeReference;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;
import wiremock.com.google.common.reflect.TypeToken;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ObjectToMapUnitTest {
    Person person = new Person("John", 30);

    @Test
    public void givenJavaObject_whenUsingReflection_thenConvertToMap() throws IllegalAccessException {
        Map<String, Object> map = convertUsingReflection(person);
        Assert.assertEquals(person.getName(), map.get("name"));
        Assert.assertEquals(person.getAge(), map.get("age"));
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
        Map<String, Object> map = objectMapper.convertValue(person, new TypeReference<Map<String, Object>>() {});
        Assert.assertEquals(person.getName(), map.get("name"));
        Assert.assertEquals(person.getAge(), map.get("age"));
    }

    @Test
    public void givenJavaObject_whenUsingGson_thenConvertToMap() {
        Gson gson = new Gson();
        String json = gson.toJson(person);
        Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
        int age = ((Double) map.get("age")).intValue();
        map.put("age", age);
        Assert.assertEquals(person.getName(), map.get("name"));
        Assert.assertEquals(person.getAge(), map.get("age"));
    }

    private static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        // Getters and setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
