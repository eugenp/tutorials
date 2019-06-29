package com.baeldung.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringRedis.class)
public class SpringDataRedisIntegrationTest {

    @Autowired
    private ApplicationContext context;

    @Test(expected = NoSuchBeanDefinitionException.class)
    public void givenAutoconfigurationIsDisable_whenApplicationStarts_thenContextWillNotHaveTheAutoconfiguredClasses() {
        context.getBean(RedisTemplate.class);
    }

}
