package com.baeldung.map.castingmaptoobject;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CastingMapToObjectUnitTest {

    private static final Map<String, Object> map = Map.of(
        "id", 1L,
        "name", "Baeldung",
        "addresses", List.of(
            new Address("La Havana", new Country("Cuba")),
            new Address("Paris", new Country("France"))
        )
    );

    @Test
    void givenMap_whenCasting_thenThrow() {
        assertThrows(ClassCastException.class, () -> { User user = (User) map; });
    }

    @Test
    void givenMap_whenUsingBeanUtils_thenConvertToObject() throws InvocationTargetException, IllegalAccessException {
        User user = new User();
        BeanUtils.populate(user, map);

        assertEqualsMapAndUser(map, user);
    }

    @Test
    void givenMap_whenUsingJackson_thenConvertToObject() {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.convertValue(map, User.class);

        assertEqualsMapAndUser(map, user);
    }

    @Test
    void givenMap_whenUsingJacksonWithWrongAttrs_thenThrow() {
        Map<String, Object> modifiedMap = new HashMap<>(map);
        modifiedMap.put("enabled", true);

        ObjectMapper objectMapper = new ObjectMapper();

        assertThrows(IllegalArgumentException.class, () -> objectMapper.convertValue(modifiedMap, User.class));
    }

    @Test
    void givenMap_whenUsingJacksonIgnoreUnknownProps_thenConvertToObject() {
        Map<String, Object> modifiedMap = new HashMap<>(map);
        modifiedMap.put("enabled", true);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        User user = objectMapper.convertValue(modifiedMap, User.class);

        assertEqualsMapAndUser(modifiedMap, user);
    }

    @Test
    void givenMap_whenUsingGson_thenConvertToObject() {
        Gson gson = new Gson();
        String jsonMap = gson.toJson(map);
        User user = gson.fromJson(jsonMap, User.class);

        assertEqualsMapAndUser(map, user);
    }

    private static void assertEqualsMapAndUser(Map<String, Object> map, User user) {
        assertEquals(map.get("id"), user.getId());
        assertEquals(map.get("name"), user.getName());
        assertEquals(map.get("addresses"), user.getAddresses());
    }

}
