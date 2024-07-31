package com.baeldung.spring.oracle.pooling;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {SpringOraclePoolingApplication.class})
@ActiveProfiles("oracle-pooling-basic")
class SpringOraclePoolingApplicationHikariCPLiveTest {
    
    @Autowired
    private DataSource dataSource;
    
    @Test
    void givenHikariCPConfiguration_thenBuildsHikariCP() {
        assertTrue(dataSource instanceof com.zaxxer.hikari.HikariDataSource);
    }

}
