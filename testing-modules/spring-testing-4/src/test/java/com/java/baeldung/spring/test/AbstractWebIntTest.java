package com.java.baeldung.spring.test;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ContextConfiguration(classes = {
        PostgresTestConfiguration.class,
        DataSourceTestConfiguration.class,
        ArticlesController.class
})
@TestPropertySource(properties = {
        "parameter = value"
})
public abstract class AbstractWebIntTest extends AbstractIntegrationTest {

}
