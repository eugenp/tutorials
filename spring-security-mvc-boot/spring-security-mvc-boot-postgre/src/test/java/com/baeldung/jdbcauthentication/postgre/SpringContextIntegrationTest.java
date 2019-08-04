package com.baeldung.jdbcauthentication.postgre;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.jdbcauthentication.postgre.PostgreJdbcAuthenticationApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PostgreJdbcAuthenticationApplication.class)
public class SpringContextIntegrationTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
