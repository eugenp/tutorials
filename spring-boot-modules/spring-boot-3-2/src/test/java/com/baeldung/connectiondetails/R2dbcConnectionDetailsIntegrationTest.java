package com.baeldung.connectiondetails;

import com.baeldung.connectiondetails.configuration.R2dbcPostgresConnectionDetailsConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConnectionDetailsApplication.class)
@Import(R2dbcPostgresConnectionDetailsConfiguration.class)
@TestPropertySource(locations = {"classpath:connectiondetails/application-r2dbc.properties"})
@ActiveProfiles("r2dbc")
public class R2dbcConnectionDetailsIntegrationTest {
    Logger logger = LoggerFactory.getLogger(R2dbcConnectionDetailsIntegrationTest.class);
    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @Test
    public void givenSecretVault_whenQueryPostgresReactive_thenSuccess() {

        String sql = "select * from information_schema.tables";

        List<String> result = r2dbcEntityTemplate.getDatabaseClient().sql(sql).fetch().all()
                .map(r -> {
                    return "hello " + r.get("table_name").toString();
                }).collectList().block();
        logger.info("count ------" + result.size());
    }
}
