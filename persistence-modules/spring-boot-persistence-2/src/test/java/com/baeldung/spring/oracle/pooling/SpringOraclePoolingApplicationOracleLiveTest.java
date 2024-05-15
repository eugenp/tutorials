package com.baeldung.spring.oracle.pooling;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {SpringOraclePoolingApplication.class})
@ActiveProfiles({"oracle-pooling-basic", "oracle"})
class SpringOraclePoolingApplicationOracleLiveTest {
    
    @Autowired
    private DataSource dataSource;
    
    @Test
    void givenOracleConfiguration_thenBuildsOracleDataSource() {
        assertTrue(dataSource instanceof oracle.jdbc.pool.OracleDataSource);
    }

}
