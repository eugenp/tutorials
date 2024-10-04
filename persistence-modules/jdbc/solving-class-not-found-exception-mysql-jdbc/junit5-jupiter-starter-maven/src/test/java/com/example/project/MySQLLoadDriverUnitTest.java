package com.example.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MySQLLoadDriverUnitTest {

    @Test
    void givenADriverClass_whenDriverLoaded_thenEnsureNoExceptionThrown() {
        assertDoesNotThrow(() -> {
            Class.forName("com.mysql.cj.jdbc.Driver");
        });
    }

}
