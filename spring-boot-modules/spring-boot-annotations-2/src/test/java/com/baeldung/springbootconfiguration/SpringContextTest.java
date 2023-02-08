package com.baeldung.springbootconfiguration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringContextTest {

    static Logger LOGGER = LoggerFactory.getLogger(SpringContextTest.class);

    @Test
    public void contextLoads() {
        LOGGER.debug("THIS IS DEBUG LEVEL");
        LOGGER.info("THIS IS INFO LEVEL");
        LOGGER.warn("THIS IS WARN LEVEL");
        LOGGER.error("THIS IS ERROR LEVEL");
    }
}
