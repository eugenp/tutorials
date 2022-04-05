package com.baeldung.contexttests.mongoconfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.config.MongoConfig;

/**
 * This Live test requires:
 * * mongodb instance running on the environment
 * 
 * (e.g. `docker run -d -p 27017:27017 --name bael-mongo mongo`)
 * 
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoConfig.class)
public class SpringContextLiveTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }

}
