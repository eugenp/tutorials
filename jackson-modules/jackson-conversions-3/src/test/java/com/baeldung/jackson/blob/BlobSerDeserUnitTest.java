package com.baeldung.jackson.blob;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlobSerDeserUnitTest {

    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        this.mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Blob.class, new SqlBlobSerializer());
        module.addDeserializer(Blob.class, new SqlBlobDeserializer());
        this.mapper.registerModule(module);
    }

    @Test
    public void givenUserWithBlob_whenSerialize_thenCorrectJsonDataProduced() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("John Doe");

        byte[] profilePictureData = "example data".getBytes();
        Blob profilePictureBlob = new SerialBlob(profilePictureData);
        user.setProfilePicture(profilePictureBlob);

        String json = mapper.writeValueAsString(user);

        String expectedJson = "{\"id\":1,\"name\":\"John Doe\",\"profilePicture\":\"ZXhhbXBsZSBkYXRh\"}";
        assertEquals(expectedJson, json);
    }

    @Test
    public void givenUserJsonWithBlob_whenDeserialize_thenCorrectDataRecieved() throws Exception {

        String json = "{\"id\":1,\"name\":\"John Doe\",\"profilePicture\":\"ZXhhbXBsZSBkYXRh\"}";


        User deserializedUser = mapper.readValue(json, User.class);
        assertEquals(1, deserializedUser.getId());
        assertEquals("John Doe", deserializedUser.getName());

        byte[] expectedProfilePictureData = "example data".getBytes();
        Blob deserializedProfilePictureBlob = deserializedUser.getProfilePicture();
        byte[] deserializedData = deserializedProfilePictureBlob.getBytes(1, (int) deserializedProfilePictureBlob.length());
        assertArrayEquals(expectedProfilePictureData, deserializedData);
    }
}
