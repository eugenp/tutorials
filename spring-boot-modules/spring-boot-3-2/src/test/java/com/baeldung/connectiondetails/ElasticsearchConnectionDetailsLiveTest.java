/**
 * These test cases have dependency with docker because they pull the docker images from docker hub
 * and run the container. So, please make sure to install docker before running the tests.
 * For the image details please look into the docker-compose files under resources/connectiondetails/docker
 **/
package com.baeldung.connectiondetails;

import com.baeldung.connectiondetails.configuration.CustomElasticsearchConnectionDetailsConfiguration;
import com.baeldung.connectiondetails.entity.elastic.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConnectionDetailsApplication.class)
@Import(CustomElasticsearchConnectionDetailsConfiguration.class)
@ComponentScan(basePackages = "com.baeldung.connectiondetails")
@TestPropertySource(locations = {"classpath:connectiondetails/application-elastic.properties"})
@ActiveProfiles("elastic")
public class ElasticsearchConnectionDetailsLiveTest {
    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConnectionDetailsLiveTest.class);
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Before
    public void prepare() {
        if (elasticsearchTemplate.indexOps(Person.class).exists()) {
            elasticsearchTemplate.indexOps(Person.class).delete();
        }
    }
    @Test
    public void givenHashicorpVault_whenCreateIndexInElastic_thenSuccess() {

        boolean result = elasticsearchTemplate.indexOps(Person.class).create();
        logger.info("index created:" + result);
        assertTrue(result);
    }
}
