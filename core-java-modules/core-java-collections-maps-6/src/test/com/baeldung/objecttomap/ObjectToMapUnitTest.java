package java.com.baeldung.objecttomap;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import wiremock.com.fasterxml.jackson.core.JsonGenerator;
import wiremock.com.fasterxml.jackson.core.type.TypeReference;
import wiremock.com.fasterxml.jackson.databind.JsonSerializer;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;
import wiremock.com.fasterxml.jackson.databind.SerializerProvider;
import wiremock.com.fasterxml.jackson.databind.module.SimpleModule;
import wiremock.com.google.common.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ObjectToMapUnitTest {
    Employee employee = new Employee("John", 3000.0, new Address("123 Street", "City"));

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
        ObjectToMapUnitTest test = new ObjectToMapUnitTest();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Address.class, new AddressSerializer());
        objectMapper.registerModule(module);
        Map<String, Object> map = objectMapper.convertValue(test.employee, new TypeReference<>() {});
        Assert.assertEquals(employee.getName(), map.get("name"));
        Assert.assertEquals(employee.getSalary(), map.get("salary"));
        Assert.assertEquals(employee.getAddress().getStreet(), ((Map<?, ?>) map.get("address")).get("street"));
        Assert.assertEquals(employee.getAddress().getCity(), ((Map<?, ?>) map.get("address")).get("city"));
    }

    @Test
    public void givenJavaObject_whenUsingGson_thenConvertToMap() {
        Gson gson = new Gson();
        String json = gson.toJson(employee);
        Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
        Assert.assertEquals(employee.getName(), map.get("name"));
        Assert.assertEquals(employee.getSalary(), map.get("salary"));
        Assert.assertEquals(employee.getAddress().getStreet(), ((Map<?, ?>) map.get("address")).get("street"));
        Assert.assertEquals(employee.getAddress().getCity(), ((Map<?, ?>) map.get("address")).get("city"));
    }

    private static class Employee {
        private String name;
        private Double salary;
        private Address address;

        public Employee(String name, Double salary, Address address) {
            this.name = name;
            this.salary = salary;
            this.address = address;
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

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }
    }
    private static class Address {
        private String street;
        private String city;

        public Address(String street, String city) {
            this.street = street;
            this.city = city;
        }
        public void setStreet(String street){
            this.street = street;
        }
        public String getStreet(){
            return street;
        }
        public void setCity(String city){
            this.city = city;
        }
        public String getCity(){
            return city;
        }
    }
    private static class AddressSerializer extends JsonSerializer<Address> {
        @Override
        public void serialize(Address address, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            Map<String, Object> addressMap = new HashMap<>();
            addressMap.put("street", address.street);
            addressMap.put("city", address.city);
            jsonGenerator.writeObject(addressMap);
        }

    }
}