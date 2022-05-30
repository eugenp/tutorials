package com.baeldung.spring.cloud.aws;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 
 * To run this Live Test, we need to have an AWS account and have API keys generated for programmatic access.
 * 
 * Check the README file in this module for more information.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringCloudAwsApplication.class)
public class SpringContextLiveTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
