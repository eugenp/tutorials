package com.baeldung.jackson.deserialization.jsongeneric;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GenericTypeDeserializerUnitTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenJsonObject_whenDeserializeIntoGenericTypeByTypeReference_thenCorrect() throws JsonProcessingException {
        String json = "{\"result\":{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Lewis\"}}";

        TypeReference<JsonResponse<User>> typeRef = new TypeReference<JsonResponse<User>>() {};
        JsonResponse<User> jsonResponse = objectMapper.readValue(json, typeRef);
        User user = jsonResponse.getResult();

        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Lewis");
    }

    @Test
    void givenJsonObject_whenDeserializeIntoGenericTypeByJavaType_thenCorrect() throws JsonProcessingException {
        String json = "{\"result\":{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Lewis\"}}";

        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(JsonResponse.class, User.class);
        JsonResponse<User> jsonResponse = objectMapper.readValue(json, javaType);
        User user = jsonResponse.getResult();

        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Lewis");
    }
}