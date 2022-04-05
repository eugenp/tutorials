package com.baeldung.spring.cloud.archaius.dynamosources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * To run this Live Test we need to:
 * * start a dynamodb instance locally on port 8000(e.g. with the following command `docker run -p 8000:8000 --name bael-dynamodb amazon/dynamodb-local`)
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DynamoSourcesApplication.class)
public class SpringContextLiveTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}