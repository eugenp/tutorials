package com.baeldung.spring.hexagonal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.Charset;
import org.springframework.http.MediaType;

public class JsonUtil {



    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    public static String convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String wRet = mapper.writeValueAsString(object);
        return wRet;
    }

    public static Object convertJsonBytesToObject(String json, Class pClazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, pClazz);
    }

    public static String createStringWithLength(int length) {
        StringBuilder builder = new StringBuilder();

        for (int index = 0; index < length; index++) {
            builder.append("a");
        }

        return builder.toString();
    }

}
