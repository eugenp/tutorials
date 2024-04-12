package com.baeldung.serialization.protocols;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class GoogleProtocolBufferUnitTest {

    @Test
    public void whenUsingProtocolBuffersSerialization_ThenObjectIsTheSameAfterDeserialization() throws IOException {

        String filePath = "src/test/resources/protocols/usersproto";

        UserProtos.User user = UserProtos.User.newBuilder().setId(1234).setName("John Doe").build();
        FileOutputStream fos = new FileOutputStream(filePath);
        user.writeTo(fos);

        UserProtos.User deserializedUser = UserProtos.User.newBuilder().mergeFrom(new FileInputStream(filePath)).build();
        
        assertEquals(1234, deserializedUser.getId());
        assertEquals("John Doe", deserializedUser.getName());
    }
}