package com.baeldung.dependson.processor;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.dependson.config.TestConfig;
import com.baeldung.dependson.shared.File;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class FileProcessorIntegrationTest {

    @Autowired
    ApplicationContext context;
    
    @Autowired
    File file;
    
    @Test
    public void whenAllBeansCreated_FileTextEndsWithProcessed() {
        context.getBean("fileProcessor");
        assertTrue(file.getText().endsWith("processed"));
    }

    @Test(expected=BeanCreationException.class)
    public void whenDependentBeanNotAvailable_ThrowsNoSuchBeanDefinitionException(){
        context.getBean("dummyFileProcessor");
    }
    
    @Test(expected=BeanCreationException.class)
    public void whenCircularDependency_ThrowsBeanCreationException(){
        context.getBean("dummyFileReaderCircular");
    }
}
