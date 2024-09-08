package com.baeldung.ioccontainer;

import static org.junit.jupiter.api.Assertions.*;

import com.baeldung.ioccontainer.bean.CustomBeanFactoryPostProcessor;
import com.baeldung.ioccontainer.bean.CustomBeanPostProcessor;
import com.baeldung.ioccontainer.bean.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IOCContainerAppUnitTest {

    @BeforeEach
    @AfterEach
    public void resetInstantiationFlag() {
        Student.setBeanInstantiated(false);
        CustomBeanPostProcessor.setBeanPostProcessorRegistered(false);
        CustomBeanFactoryPostProcessor.setBeanFactoryPostProcessorRegistered(false);
    }

    @Test
    public void whenBFInitialized_thenStudentNotInitialized() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc-container-difference-example.xml");
        assertNotNull(context);
        assertTrue(Student.isBeanInstantiated());
    }

    @Test
    public void whenBFInitialized_thenStudentInitialized() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc-container-difference-example.xml");
        Student student = (Student) context.getBean("student");
        assertTrue(Student.isBeanInstantiated());
    }
    
    @Test
    public void whenAppContInitialized_thenStudentInitialized() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc-container-difference-example.xml");
        assertTrue(Student.isBeanInstantiated());
    }

    @Test
    public void whenBFInitialized_thenBFPProcessorAndBPProcessorNotRegAutomatically() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc-container-difference-example.xml");

        assertTrue(CustomBeanFactoryPostProcessor.isBeanFactoryPostProcessorRegistered());
        assertTrue(CustomBeanPostProcessor.isBeanPostProcessorRegistered());
    }

    @Test
    public void whenBFPostProcessorAndBPProcessorRegisteredManually_thenReturnTrue() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc-container-difference-example.xml");

        CustomBeanFactoryPostProcessor beanFactoryPostProcessor = new CustomBeanFactoryPostProcessor();
        beanFactoryPostProcessor.postProcessBeanFactory(applicationContext.getBeanFactory());
        assertTrue(CustomBeanFactoryPostProcessor.isBeanFactoryPostProcessorRegistered());

        CustomBeanPostProcessor beanPostProcessor = new CustomBeanPostProcessor();
        applicationContext.getBeanFactory().addBeanPostProcessor(beanPostProcessor);
        Student student = (Student) applicationContext.getBean("student");
        assertTrue(CustomBeanPostProcessor.isBeanPostProcessorRegistered());
    }
    
    @Test
    public void whenAppContInitialized_thenBFPostProcessorAndBPostProcessorRegisteredAutomatically() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc-container-difference-example.xml");

        assertTrue(CustomBeanFactoryPostProcessor.isBeanFactoryPostProcessorRegistered());
        assertTrue(CustomBeanPostProcessor.isBeanPostProcessorRegistered());
    }
}
