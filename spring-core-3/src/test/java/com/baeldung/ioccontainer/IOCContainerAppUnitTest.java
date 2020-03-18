package com.baeldung.ioccontainer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.baeldung.ioccontainer.bean.CustomBeanFactoryPostProcessor;
import com.baeldung.ioccontainer.bean.CustomBeanPostProcessor;
import com.baeldung.ioccontainer.bean.Student;

public class IOCContainerAppUnitTest {

    @BeforeEach
    @AfterEach
    public void resetInstantiationFlag() {
        Student.isBeanInstantiated = false;
        CustomBeanPostProcessor.isBeanInstantiated = false;
        CustomBeanFactoryPostProcessor.isBeanInstantiated = false;
    }

    @Test
    public void whenBFInitialized_thenStudentNotInitialized() {
        Resource res = new ClassPathResource("ioc-container-difference-example.xml");
        BeanFactory factory = new XmlBeanFactory(res);
        
        assertFalse(Student.isBeanInstantiated);
    }

    @Test
    public void whenBFInitialized_thenStudentInitialized() {
    
        Resource res = new ClassPathResource("ioc-container-difference-example.xml");
        BeanFactory factory = new XmlBeanFactory(res);
        Student student = (Student) factory.getBean("student");
    
        assertTrue(Student.isBeanInstantiated);
    }
    
    @Test
    public void whenAppContInitialized_thenStudentInitialized() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc-container-difference-example.xml");
        
        assertTrue(Student.isBeanInstantiated);
    }

    @Test
    public void whenBFInitialized_thenBFPProcessorAndBPProcessorNotRegAutomatically() {
        Resource res = new ClassPathResource("ioc-container-difference-example.xml");
        ConfigurableListableBeanFactory factory = new XmlBeanFactory(res);

        assertFalse(CustomBeanFactoryPostProcessor.isBeanInstantiated);
        assertFalse(CustomBeanPostProcessor.isBeanInstantiated);
    }

    @Test
    public void whenAppContInitialized_thenBFPostProcessorAndBPostProcessorRegisteredAutomatically() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc-container-difference-example.xml");

        assertTrue(CustomBeanFactoryPostProcessor.isBeanInstantiated);
        assertTrue(CustomBeanPostProcessor.isBeanInstantiated);
    }

    @Test
    public void whenBFPostProcessorAndBPProcessorRegisteredManually_thenReturnTrue() {
        Resource res = new ClassPathResource("ioc-container-difference-example.xml");
        ConfigurableListableBeanFactory factory = new XmlBeanFactory(res);

        CustomBeanFactoryPostProcessor beanFactoryPostProcessor = new CustomBeanFactoryPostProcessor();
        beanFactoryPostProcessor.postProcessBeanFactory(factory);
        assertTrue(CustomBeanFactoryPostProcessor.isBeanInstantiated);

        CustomBeanPostProcessor beanPostProcessor = new CustomBeanPostProcessor();
        factory.addBeanPostProcessor(beanPostProcessor);
        Student student = (Student) factory.getBean("student");
        assertTrue(CustomBeanPostProcessor.isBeanInstantiated);
    }

}
