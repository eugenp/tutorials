package com.baeldung.disableautoconfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataMongoDB.class)
public class SpringDataMongoDBIntegrationTest {

    @Autowired
    private ApplicationContext context;

    @Test(expected = NoSuchBeanDefinitionException.class)
    public void givenAutoConfigDisabled_whenStarting_thenNoAutoconfiguredBeansInContext() {
        context.getBean(MongoTemplate.class);
    }

}
