package com.baeldung.h2;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

@SpringBootTest
class H2ApplicationIntegrationTest {


    @Test
    void givenApplication_whenBootstrapped_thenDataAvailable() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "password");
        ResultSet rs = RunScript.execute(connection, new FileReader(new ClassPathResource("db/script.sql").getFile()));
        Assertions.assertTrue(rs.next());
        Assertions.assertEquals(1, rs.getInt("id"));
        Assertions.assertEquals("John Doe", rs.getString("name"));
    }
}