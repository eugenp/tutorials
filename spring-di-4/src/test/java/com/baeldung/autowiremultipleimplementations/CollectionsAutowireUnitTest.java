package com.baeldung.autowiremultipleimplementations;

import com.baeldung.autowiremultipleimplementations.components.CollectionsAutowire;
import com.baeldung.autowiremultipleimplementations.candidates.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {CollectionsAutowire.class, GoodServiceD.class, GoodServiceC.class, GoodServiceB.class, GoodServiceA.class})
public class CollectionsAutowireUnitTest {

    @Autowired
    private CollectionsAutowire collectionsAutowire;

    @Test
    public void testSetAutowiring() throws NoSuchFieldException, IllegalAccessException {
        Set<?> rawServices = (Set<?>) getPrivateField(collectionsAutowire, "goodServices");
        assertNotNull(rawServices, "Set of GoodService should not be null");
        assertFalse(rawServices.isEmpty(), "Set of GoodService should not be empty");

        Set<GoodService> services = rawServices.stream()
                .map(service -> (GoodService) service)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Check for all specific types
        assertTrue(services.stream().anyMatch(s -> s instanceof GoodServiceA), "Should contain GoodServiceA");
        assertTrue(services.stream().anyMatch(s -> s instanceof GoodServiceB), "Should contain GoodServiceB");
        assertTrue(services.stream().anyMatch(s -> s instanceof GoodServiceC), "Should contain GoodServiceC");

        String actualMessage = collectionsAutowire.hello();
        assertTrue(actualMessage.contains("Hello from A!"), "Message should contain greeting from A");
        assertTrue(actualMessage.contains("Hello from B!"), "Message should contain greeting from B");
        assertTrue(actualMessage.contains("Hello from C!"), "Message should contain greeting from C");
    }

    @Test
    public void testMapAutowiring() throws NoSuchFieldException, IllegalAccessException {
        Map<?, ?> rawServiceMap = (Map<?, ?>) getPrivateField(collectionsAutowire, "goodServiceMap");
        assertNotNull(rawServiceMap, "Map of GoodService should not be null");
        assertFalse(rawServiceMap.isEmpty(), "Map of GoodService should not be empty");

        Map<String, GoodService> serviceMap = rawServiceMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> (String) entry.getKey(),
                        entry -> (GoodService) entry.getValue()
                ));

        // Check keys and specific instances
        assertTrue(serviceMap.containsKey("goodServiceA"), "Map should contain a key for GoodServiceA");
        assertTrue(serviceMap.containsKey("goodServiceB"), "Map should contain a key for GoodServiceB");
        assertTrue(serviceMap.containsKey("goodServiceC"), "Map should contain a key for GoodServiceC");

        assertInstanceOf(GoodServiceA.class, serviceMap.get("goodServiceA"), "goodServiceA should be an instance of GoodServiceA");
        assertInstanceOf(GoodServiceB.class, serviceMap.get("goodServiceB"), "goodServiceB should be an instance of GoodServiceB");
        assertInstanceOf(GoodServiceC.class, serviceMap.get("goodServiceC"), "goodServiceC should be an instance of GoodServiceC");
    }

    private Object getPrivateField(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }
}
