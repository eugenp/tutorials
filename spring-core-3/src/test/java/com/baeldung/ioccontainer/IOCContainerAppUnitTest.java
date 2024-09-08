package com.baeldung.ioccontainer;

import static org.junit.jupiter.api.Assertions.*;

import com.baeldung.ioccontainer.bean.CustomBeanFactoryPostProcessor;
import com.baeldung.ioccontainer.bean.CustomBeanPostProcessor;
import com.baeldung.ioccontainer.bean.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
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
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions("ioc-container-difference-example.xml");
        assertFalse(Student.isBeanInstantiated());
    }

    @Test
    public void whenBFInitialized_thenStudentInitialized() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions("ioc-container-difference-example.xml");
        Student student = (Student) factory.getBean("student");
        assertTrue(Student.isBeanInstantiated());
        assertNotNull(student);
    }
    
    @Test
    public void whenAppContInitialized_thenStudentInitialized() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc-container-difference-example.xml");

        assertTrue(Student.isBeanInstantiated());
        assertNotNull(context);
    }

    @Test
    public void whenBFInitialized_thenBFPProcessorAndBPProcessorNotRegAutomatically() {
        ConfigurableListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader((BeanDefinitionRegistry) factory);
        reader.loadBeanDefinitions("ioc-container-difference-example.xml");

        assertFalse(CustomBeanFactoryPostProcessor.isBeanFactoryPostProcessorRegistered());
        assertFalse(CustomBeanPostProcessor.isBeanPostProcessorRegistered());
    }

    @Test
    public void whenBFPostProcessorAndBPProcessorRegisteredManually_thenReturnTrue() {
        ConfigurableListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader((BeanDefinitionRegistry) factory);
        reader.loadBeanDefinitions("ioc-container-difference-example.xml");

        CustomBeanFactoryPostProcessor beanFactoryPostProcessor = new CustomBeanFactoryPostProcessor();
        beanFactoryPostProcessor.postProcessBeanFactory(factory);
        assertTrue(CustomBeanFactoryPostProcessor.isBeanFactoryPostProcessorRegistered());

        CustomBeanPostProcessor beanPostProcessor = new CustomBeanPostProcessor();
        factory.addBeanPostProcessor(beanPostProcessor);
        Student student = (Student) factory.getBean("student");
        assertTrue(CustomBeanPostProcessor.isBeanPostProcessorRegistered());
        assertNotNull(student);
    }
    
    @Test
    public void whenAppContInitialized_thenBFPostProcessorAndBPostProcessorRegisteredAutomatically() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc-container-difference-example.xml");

        assertTrue(CustomBeanFactoryPostProcessor.isBeanFactoryPostProcessorRegistered());
        assertTrue(CustomBeanPostProcessor.isBeanPostProcessorRegistered());
        assertNotNull(context);
    }
}
