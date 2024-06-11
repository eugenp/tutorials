package com.baeldung.tomcatconnectionpool.test.application;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.baeldung.tomcatconnectionpool.application.SpringBootConsoleApplication;

@SpringBootTest(classes = {SpringBootConsoleApplication.class})
@TestPropertySource(properties = "spring.datasource.type=org.apache.tomcat.jdbc.pool.DataSource")
class SpringBootTomcatConnectionPoolIntegrationTest {
    
    @Autowired
    private DataSource dataSource;
    
    @Test
    void givenTomcatConnectionPoolInstance_whenCheckedPoolClassName_thenCorrect() {
        assertThat(dataSource.getClass().getName()).isEqualTo("org.apache.tomcat.jdbc.pool.DataSource");
    }
}
