package org.baeldung.startup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:startupConfig.xml")
public class SpringStartupXMLConfigIntegrationTest {

    @Autowired
    private ApplicationContext ctx;

    @Test
    public void whenPostConstruct_shouldLogEnv() throws Exception {
        ctx.getBean(InitMethodExampleBean.class);
    }

    @Test
    public void whenAllStrategies_shouldLogOrder() throws Exception {
        ctx.getBean(AllStrategiesExampleBean.class);
    }
}
