package com.baeldung.protobuf.convert;


import java.io.IOException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.google.protobuf.Message;

public class ProtobufUtilUnitTest {

    public static String jsonStr = "{\r\n"
            + "  \"boolean\": true,\r\n"
            + "  \"color\": \"gold\",\r\n"
            + "  \"object\": {\r\n"
            + "    \"a\": \"b\",\r\n"
            + "    \"c\": \"d\"\r\n"
            + "  },\r\n"
            + "  \"string\": \"Hello World\"\r\n"
            + "}";

    @Test
    public void givenJson_convertToProtobuf() throws IOException {
        Message protobuf = ProtobufUtil.fromJson(jsonStr);
        Assert.assertTrue(protobuf.toString().contains("key: \"boolean\""));
        Assert.assertTrue(protobuf.toString().contains("string_value: \"Hello World\""));
    }

    @Test
    public void givenProtobuf_convertToJson() throws IOException {
        Message protobuf = ProtobufUtil.fromJson(jsonStr);
        String json = ProtobufUtil.toJson(protobuf);
        Assert.assertTrue(json.contains("\"boolean\": true"));
        Assert.assertTrue(json.contains("\"string\": \"Hello World\""));
        Assert.assertTrue(json.contains("\"color\": \"gold\""));
    }
}
