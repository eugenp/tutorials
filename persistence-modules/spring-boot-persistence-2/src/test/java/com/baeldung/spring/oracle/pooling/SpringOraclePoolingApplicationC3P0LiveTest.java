package com.baeldung.spring.oracle.pooling;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringOraclePoolingApplication.class})
@ActiveProfiles({"oracle-pooling-basic", "c3p0"})
public class SpringOraclePoolingApplicationC3P0LiveTest {
    
    @Autowired
    private DataSource dataSource;
    
    @Test    
    public void givenC3p0Configuration_thenBuildsComboPooledDataSource() {
        assertTrue(dataSource instanceof com.mchange.v2.c3p0.ComboPooledDataSource);
    }

}
