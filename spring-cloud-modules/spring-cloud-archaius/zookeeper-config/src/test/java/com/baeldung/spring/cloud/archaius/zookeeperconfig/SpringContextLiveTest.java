package com.baeldung.spring.cloud.archaius.zookeeperconfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * This Live tTest requires:
 * * A Zookeeper instance running locally on port 2181 (e.g. using  `docker run --name bael-zookeeper -p 2181:2181 --restart always zookeeper`) 
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZookeeperConfigApplication.class)
public class SpringContextLiveTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}