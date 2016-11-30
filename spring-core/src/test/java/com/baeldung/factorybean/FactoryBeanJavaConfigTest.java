package com.baeldung.factorybean;

<<<<<<< HEAD
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

=======
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

>>>>>>> f354f9e3057481c76c10a1e2aa127d3abb95b329
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FactoryBeanAppConfig.class)
public class FactoryBeanJavaConfigTest {

<<<<<<< HEAD
    @Resource
    private Tool tool;
    @Resource(name = "&toolFactory")
=======
    @Autowired
    private Tool tool;

    @Resource(name = "&tool")
>>>>>>> f354f9e3057481c76c10a1e2aa127d3abb95b329
    private ToolFactory toolFactory;

    @Test
    public void testConstructWorkerByJava() {
        assertThat(tool.getId(), equalTo(2));
        assertThat(toolFactory.getFactoryId(), equalTo(7070));
    }
}
