package com.baeldung.constructordetector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.ConstructorDetector;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConstructorDetectorUnitTest {

    @Test
    public void givenUserJson_whenUsingPropertiesBased_thenCorrect() throws Exception {
        String json = "{\"firstName\": \"John\", \"lastName\": \"Doe\", \"age\": 25}";

        ObjectMapper mapper = JsonMapper.builder()
                .constructorDetector(ConstructorDetector.USE_PROPERTIES_BASED)
                .build();

        User user = mapper.readValue(json, User.class);
        assertEquals("John", user.getFirstName());
        assertEquals(25, user.getAge());
    }

    @Test
    public void givenStringJson_whenUsingDelegating_thenCorrect() throws Exception {
        String json = "\"Hello, world!\"";

        ObjectMapper mapper = JsonMapper.builder()
                .constructorDetector(ConstructorDetector.USE_DELEGATING)
                .build();

        StringWrapper wrapper = mapper.readValue(json, StringWrapper.class);
        assertEquals("Hello, world!", wrapper.getValue());
    }

    @Test
    public void givenProductJson_whenUsingExplicitOnly_thenCorrect() throws Exception {
        String json = "{\"value\": \"Laptop\", \"price\": 999.99}";

        ObjectMapper mapper = JsonMapper.builder()
                .constructorDetector(ConstructorDetector.EXPLICIT_ONLY)
                .build();

        Product product = mapper.readValue(json, Product.class);
        assertEquals("Laptop", product.getName());
        assertEquals(999.99, product.getPrice(), 0.001);
    }

    @Test
    public void givenAddressJson_whenUsingDefault_thenCorrect() throws Exception {
        String json = "{\"street\": \"123 Main St\", \"city\": \"Springfield\"}";

        ObjectMapper mapper = JsonMapper.builder()
                .constructorDetector(ConstructorDetector.DEFAULT)
                .build();

        Address address = mapper.readValue(json, Address.class);
        assertEquals("123 Main St", address.getStreet());
        assertEquals("Springfield", address.getCity());
    }
}
