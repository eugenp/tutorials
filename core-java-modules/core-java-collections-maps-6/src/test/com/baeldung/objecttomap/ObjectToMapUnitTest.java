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
    Employee employee = new Employee("John", 3000.0);

    @Test
    public void givenJavaObject_whenUsingReflection_thenConvertToMap() throws IllegalAccessException {
        Map<String, Object> map = convertUsingReflection(employee);
        Assert.assertEquals(employee.getName(), map.get("name"));
        Assert.assertEquals(employee.getSalary(), map.get("salary"));
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
        Assert.assertEquals(employee.getName(), map.get("name"));
        Assert.assertEquals(employee.getSalary(), map.get("salary"));
    }

    @Test
    public void givenJavaObject_whenUsingGson_thenConvertToMap() {
        Gson gson = new Gson();
        String json = gson.toJson(employee);
        Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
        Assert.assertEquals(employee.getName(), map.get("name"));
        Assert.assertEquals(employee.getSalary(), map.get("salary"));
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
