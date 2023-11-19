package com.baeldung.spring.oracle.pooling;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringOraclePoolingApplication.class})
@ActiveProfiles({"oracle-pooling-basic"})
@TestPropertySource(properties = "spring.datasource.type=oracle.ucp.jdbc.PoolDataSource")
public class SpringOraclePoolingApplicationOracleUCPLiveTest {
    
    @Autowired
    private DataSource dataSource;
    
    @Test    
    public void givenOracleUCPConfiguration_thenBuildsOraclePoolDataSource() {
        assertTrue(dataSource instanceof oracle.ucp.jdbc.PoolDataSource);
    }

}
