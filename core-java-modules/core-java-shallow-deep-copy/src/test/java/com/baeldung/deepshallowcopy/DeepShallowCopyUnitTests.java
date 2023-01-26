package com.baeldung.deepshallowcopy;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import com.google.gson.Gson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.SerializationUtils;

public class DeepShallowCopyUnitTests {

    @Test
    public void testShallowCopy() {
        DataHolder original = new DataHolder(new int[] {90, 85, 80});
        DataHolder copy = new DataHolder(original.getMarks());
        copy.getMarks()[0] = 95;
        assertArrayEquals(original.getMarks(), copy.getMarks());
    }

    @Test
    public void testDeepCopyUsingClonable() throws CloneNotSupportedException {
        DataHolder original = new DataHolder(new int[] {90, 85, 80});
        DataHolder copy = (DataHolder) original.clone();
        copy.setMarks(new int[] {95, 85, 80});
        assertNotSame(original, copy);
    }

    @Test
    public void testDeepCopyUsingGson() {
        DataHolder original = new DataHolder(new int[] {90, 85, 80});
        String json = new Gson().toJson(original);
        DataHolder copy = new Gson().fromJson(json, DataHolder.class);
        assertNotSame(original, copy);
    }

    @Test
    public void testDeepCopyUsingJackson() throws IOException {
        DataHolder original = new DataHolder(new int[] {90, 85, 80});
        ObjectMapper objectMapper = new ObjectMapper();
        DataHolder copy = objectMapper.readValue(objectMapper.writeValueAsString(original), DataHolder.class);
        assertNotSame(original, copy);
    }

    @Test
    public void testDeepCopyUsingApache() throws CloneNotSupportedException {
        DataHolder original = new DataHolder(new int[] {90, 85, 80});
        DataHolder copy = SerializationUtils.clone(original);
        assertNotSame(original, copy);
    }
}
