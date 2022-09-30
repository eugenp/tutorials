package com.baeldung;

import com.baeldung.spring.cloud.DataFlowServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataFlowServerApplication.class)
public class SpringContextTest {

    @Test
    public void contextLoads() {
    }
}
