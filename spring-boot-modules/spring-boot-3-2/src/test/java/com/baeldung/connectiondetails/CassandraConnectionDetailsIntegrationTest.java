package com.baeldung.connectiondetails;

import com.baeldung.connectiondetails.configuration.CustomCassandraConnectionDetailsConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConnectionDetailsApplication.class)
@Import(CustomCassandraConnectionDetailsConfiguration.class)
@ComponentScan(basePackages = "com.baeldung.connectiondetails")
@TestPropertySource(locations = {"classpath:connectiondetails/application-cassandra.properties"})
@ActiveProfiles("cassandra")
public class CassandraConnectionDetailsIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger(CassandraConnectionDetailsIntegrationTest.class);
    @Autowired
    private CassandraTemplate cassandraTemplate;
    @Test
    public void givenHashicorpVault_whenRunQuery_thenSuccess() {
        boolean result = cassandraTemplate.getCqlOperations()
                .execute("CREATE KEYSPACE IF NOT EXISTS spring_cassandra"
                        + " WITH replication = {'class':'SimpleStrategy', 'replication_factor':3}");
        logger.info("the result -" + result);
        assertTrue(result);
    }
}
