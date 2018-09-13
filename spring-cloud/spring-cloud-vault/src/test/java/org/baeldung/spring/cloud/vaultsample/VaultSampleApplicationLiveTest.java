package org.baeldung.spring.cloud.vaultsample;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VaultSampleApplicationLiveTest {

    @Autowired
    Environment env;

    @Autowired
    DataSource datasource;


    @Test
    public void whenGenericBackendEnabled_thenEnvHasAccessToVaultSecrets() {

        String fooValue = env.getProperty("foo");
        assertEquals("test-bar", fooValue);

    }
    
    @Test
    public void whenKvBackendEnabled_thenEnvHasAccessToVaultSecrets() {

        String fooValue = env.getProperty("foo.versioned");
        assertEquals("bar1", fooValue);


    }
    

    @Test
    public void whenDatabaseBackendEnabled_thenDatasourceUsesVaultCredentials() {

        try (Connection c = datasource.getConnection()) {

            ResultSet rs = c.createStatement()
              .executeQuery("select 1");
            
            rs.next();
            Long value = rs.getLong(1);

            assertEquals(Long.valueOf(1), value);

        } catch (SQLException sex) {
            throw new RuntimeException(sex);
        }

    }

}
