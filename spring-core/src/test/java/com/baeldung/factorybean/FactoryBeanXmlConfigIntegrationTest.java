package com.baeldung.factorybean;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:factorybean-spring-ctx.xml" })
public class FactoryBeanXmlConfigIntegrationTest {

    @Autowired
    private Tool tool;
    @Resource(name = "&tool")
    private ToolFactory toolFactory;

    @Test
    public void testConstructWorkerByXml() {
        assertThat(tool.getId(), equalTo(1));
        assertThat(toolFactory.getFactoryId(), equalTo(9090));
    }
}