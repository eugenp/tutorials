package com.example.items;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Queue;
import java.util.LinkedList;

public class KeyUnitTest {

    @Test
    void givenKey_whenUseWithKeyContext_thenNoException() {
        Queue<String> doors = new LinkedList<>();
        doors.add("front-door");
        doors.add("back-door");

        Key key = new Key("MasterKey");
        KeyContext ctx = new KeyContext("front-door", doors);

        assertDoesNotThrow(() -> key.use(ctx));
    }
}
