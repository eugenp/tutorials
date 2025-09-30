package com.baeldung.classnotfound;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class MySQLLoadDriverUnitTest {

    @Test
    void givenADriverClass_whenDriverLoaded_thenEnsureNoExceptionThrown() {
        assertDoesNotThrow(() -> {
            Class.forName("com.mysql.cj.jdbc.Driver");
        });
    }

}
