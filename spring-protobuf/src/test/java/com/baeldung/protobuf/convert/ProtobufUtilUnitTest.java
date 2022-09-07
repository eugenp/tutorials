package com.baeldung.protobuf.convert;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.google.protobuf.Message;

public class ProtobufUtilUnitTest {

    public static String jsonInput = "{\r\n" + "  \"boolean\": true,\r\n" + "  \"color\": \"gold\",\r\n"
            + "  \"object\": {\r\n" + "    \"a\": \"b\",\r\n" + "    \"c\": \"d\"\r\n" + "  },\r\n"
            + "  \"string\": \"Hello World\"\r\n" + "}";

    @Test
    public void givenProtobuf_convertToJson() throws IOException {
        Message fromJson = ProtobuffUtil.fromJson(jsonInput);

        InputStream inputStream = new ByteArrayInputStream(fromJson.toByteArray());

        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(
                new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }
        String json = ProtobuffUtil.toJson(fromJson);
        Assert.assertTrue(json.contains("\"boolean\": true"));
        Assert.assertTrue(json.contains("\"string\": \"Hello World\""));
        Assert.assertTrue(json.contains("\"color\": \"gold\""));
    }

}
