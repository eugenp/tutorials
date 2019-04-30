package org.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.task.JobConfiguration;
import com.baeldung.task.TaskDemo;

/**
 * This Live Test requires:
 * *  a MySql instance running, that allows a root user with no password, and with a database named 
 * 
 * (e.g. with the following command `docker run -p 3306:3306 --name bael-mysql -e MYSQL_ALLOW_EMPTY_PASSWORD=true -e MYSQL_DATABASE=springcloud mysql:latest`)
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@ContextConfiguration(classes = { JobConfiguration.class, TaskDemo.class }, initializers = {
        ConfigFileApplicationContextInitializer.class })
public class SpringContextLiveTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
