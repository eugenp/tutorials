package com.baeldung.lombok.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

class LombokWithJacksonEmployeeUnitTest {

    @Test
    void withEmployeeObject_thenSerializeSucessfully() throws IOException {
        Employee employee = Employee.builder()
            .identity(5)
            .firstName("Bob")
            .build();

        String json = newObjectMapper().writeValueAsString(employee);
        assertEquals("{\"identity\":5,\"firstName\":\"Bob\"}", json);
    }

        @Test
        public void withEmployeeJSON_thenDeserializeSucessfully() throws IOException {
            String json = "{\"id\":5,\"name\":\"Bob\"}";
            Employee employee = newObjectMapper().readValue(json, Employee.class);
    
            assertEquals(5, employee.getIdentity());
            assertEquals("Bob", employee.getFirstName());
        }

    private ObjectMapper newObjectMapper() {
        return new ObjectMapper();
    }

}
