package com.baeldung;

import com.baeldung.spring.cloud.DataFlowShellApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * This live test requires:
 * complete data-flow server and shell setup running
 * 
 * <br>
 * For more info:
 * https://www.baeldung.com/spring-cloud-data-flow-stream-processing
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataFlowShellApplication.class)
public class SpringContextLiveTest {

    @Test
    public void contextLoads() {
    }

}
