package com.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.spring.data.es.config.Config;

/**
 * This Manual test requires: * Elasticsearch instance running on localhost:9200.
 * The following docker command can be used:
 * docker run -d --name es761 -p 9200:9200 -e "discovery.type=single-node" elasticsearch:7.6.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class SpringContextManualTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
