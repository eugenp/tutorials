package com.baeldung.dynamic.autowire;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DynamicAutowireConfig.class)
public class DynamicAutowireIntegrationTest {

    @Autowired
    private BeanFactoryDynamicAutowireService beanFactoryDynamicAutowireService;

    @Autowired
    private InterfaceDynamicAutowireService interfaceDynamicAutowireService;

    @Test
    public void testConstructWorkerByJava() {
        assertThat(beanFactoryDynamicAutowireService.isServerActive("uk", 101), is(false));
        assertThat(interfaceDynamicAutowireService.isServerActive("uk", 101), is(false));

        assertThat(beanFactoryDynamicAutowireService.isServerActive("usa", 101), is(true));
        assertThat(interfaceDynamicAutowireService.isServerActive("usa", 101), is(true));
    }
}
