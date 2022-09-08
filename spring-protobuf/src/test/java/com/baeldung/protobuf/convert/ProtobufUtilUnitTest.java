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

    public static String jsonStr = "{\r\n"
            + "  \"boolean\": true,\r\n"
            + "  \"color\": \"gold\",\r\n"
            + "  \"object\": {\r\n"
            + "    \"a\": \"b\",\r\n"
            + "    \"c\": \"d\"\r\n"
            + "  },\r\n"
            + "  \"string\": \"Hello World\"\r\n"
            + "}";
    public static String protobufStr = "fromJson\r\n"
            + "     (com.google.protobuf.Struct) fields {\r\n"
            + "  key: \"boolean\"\r\n"
            + "  value {\r\n"
            + "    bool_value: true\r\n"
            + "  }\r\n"
            + "}\r\n"
            + "fields {\r\n"
            + "  key: \"color\"\r\n"
            + "  value {\r\n"
            + "    string_value: \"gold\"\r\n"
            + "  }\r\n"
            + "}\r\n"
            + "fields {\r\n"
            + "  key: \"object\"\r\n"
            + "  value {\r\n"
            + "    struct_value {\r\n"
            + "      fields {\r\n"
            + "        key: \"a\"\r\n"
            + "        value {\r\n"
            + "          string_value: \"b\"\r\n"
            + "        }\r\n"
            + "      }\r\n"
            + "      fields {\r\n"
            + "        key: \"c\"\r\n"
            + "        value {\r\n"
            + "          string_value: \"d\"\r\n"
            + "        }\r\n"
            + "      }\r\n"
            + "    }\r\n"
            + "  }\r\n"
            + "}\r\n"
            + "fields {\r\n"
            + "  key: \"string\"\r\n"
            + "  value {\r\n"
            + "    string_value: \"Hello World\"\r\n"
            + "  }\r\n"
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
