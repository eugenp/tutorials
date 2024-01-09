package com.baeldung.nplusone.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JsonUtils {

    @Value("users.json")
    private File userFile;

    @Value("groups.json")
    private File groupFile;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public <T> List<T> getUsers(Class<T> userType) {
        return getObjects(userType, userFile);
    }

    public <T> List<T> getGroups(Class<T> userType) {
        return getObjects(userType, groupFile);
    }

    private <T> List<T> getObjects(Class<T> userType, File userFile1) {
        try {
            return MAPPER.readValue(userFile1, MAPPER.getTypeFactory()
              .constructCollectionType(List.class, userType));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
