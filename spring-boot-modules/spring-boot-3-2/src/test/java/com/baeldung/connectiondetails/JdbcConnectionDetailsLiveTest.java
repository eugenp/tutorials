/**
 * These test cases have dependency with docker because they pull the docker images from docker hub
 * and run the container. So, please make sure to install docker before running the tests.
 * For the image details please look into the docker-compose files under resources/connectiondetails/docker
 **/
package com.baeldung.connectiondetails;

import com.baeldung.connectiondetails.configuration.JdbcConnectionDetailsConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConnectionDetailsApplication.class)
@Import(JdbcConnectionDetailsConfiguration.class)
@ComponentScan(basePackages = "com.baeldung.connectiondetails")
@TestPropertySource(locations = {"classpath:connectiondetails/application-jdbc.properties"})
@ActiveProfiles("jdbc")
public class JdbcConnectionDetailsLiveTest {
    private static final Logger logger = LoggerFactory.getLogger(JdbcConnectionDetailsLiveTest.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Test
    public void givenSecretVault_whenIntegrateWithPostgres_thenConnectionSuccessful() {
        String sql = "select current_date;";
        Date date = jdbcTemplate.queryForObject(sql, Date.class);
        assertEquals(LocalDate.now().toString(), date.toString());
    }
}
