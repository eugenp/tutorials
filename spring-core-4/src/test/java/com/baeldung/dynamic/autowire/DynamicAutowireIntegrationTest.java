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
    private CustomMapFromListDynamicAutowireService customMapFromListDynamicAutowireService;

    @Test
    public void givenDynamicallyAutowiredBean_whenCheckingServerInGB_thenServerIsNotActive() {
        assertThat(beanFactoryDynamicAutowireService.isServerActive("GB", 101), is(false));
        assertThat(customMapFromListDynamicAutowireService.isServerActive("GB", 101), is(false));
    }

    @Test
    public void givenDynamicallyAutowiredBean_whenCheckingServerInUS_thenServerIsActive() {
        assertThat(beanFactoryDynamicAutowireService.isServerActive("US", 101), is(true));
        assertThat(customMapFromListDynamicAutowireService.isServerActive("US", 101), is(true));
    }
}
