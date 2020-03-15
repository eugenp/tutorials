package com.baeldung.ioccontainer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
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

    private LogAppender logAppender;
    private List<LoggingEvent> loggingEvents;

    @BeforeEach
    public void initializeLogAppender() {
        logAppender = new LogAppender();
        Logger.getRootLogger()
            .addAppender(logAppender);
        loggingEvents = logAppender.events;
    }

    @AfterEach
    public void removeLogAppender() {
        Logger.getRootLogger()
            .removeAppender(logAppender);
    }

    @Test
    public void whenBFInitialized_thenNoStudentLogPrinted() {
        Resource res = new ClassPathResource("ioc-container-difference-example.xml");
        BeanFactory factory = new XmlBeanFactory(res);

        String expected = "Student Bean is initialized";
        assertFalse(checkWhetherLoggerContains(expected));
    }

    @Test
    public void whenBFInitialized_thenStudentLogPrinted() {

        Resource res = new ClassPathResource("ioc-container-difference-example.xml");
        BeanFactory factory = new XmlBeanFactory(res);
        Student student = (Student) factory.getBean("student");

        String expected = "Student Bean is initialized";
        assertTrue(checkWhetherLoggerContains(expected));
    }

    @Test
    public void whenAppContInitialized_thenStudentObjInitialized() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc-container-difference-example.xml");

        String expected = "Student Bean is initialized";
        assertTrue(checkWhetherLoggerContains(expected));
    }

    @Test
    public void whenBFInitialized_thenBFPProcessorAndBPProcessorNotRegAutomatically() {
        Resource res = new ClassPathResource("ioc-container-difference-example.xml");
        ConfigurableListableBeanFactory factory = new XmlBeanFactory(res);

        String beanFactoryPostProcessorExpectedLog = "BeanFactoryPostProcessor is Registered";
        assertFalse(checkWhetherLoggerContains(beanFactoryPostProcessorExpectedLog));

        String beanPostProcessorExpectedLog = "BeanPostProcessor is Registered Before Initialization";
        assertFalse(checkWhetherLoggerContains(beanPostProcessorExpectedLog));
    }

    @Test
    public void whenAppContInitialized_thenBFPostProcessorAndBPostProcessorRegisteredAutomatically() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc-container-difference-example.xml");

        String beanFactoryPostProcessorExpectedLog = "BeanFactoryPostProcessor is Registered";
        assertTrue(checkWhetherLoggerContains(beanFactoryPostProcessorExpectedLog));

        String beanPostProcessorExpectedLog = "BeanPostProcessor is Registered Before Initialization";
        assertTrue(checkWhetherLoggerContains(beanPostProcessorExpectedLog));
    }

    @Test
    public void whenBFPostProcessorAndBPProcessorRegisteredManually_thenReturnTrue() {
        Resource res = new ClassPathResource("ioc-container-difference-example.xml");
        ConfigurableListableBeanFactory factory = new XmlBeanFactory(res);

        CustomBeanFactoryPostProcessor beanFactoryPostProcessor = new CustomBeanFactoryPostProcessor();
        beanFactoryPostProcessor.postProcessBeanFactory(factory);
        String beanFactoryPostProcessorExpectedLog = "BeanFactoryPostProcessor is Registered";
        assertTrue(checkWhetherLoggerContains(beanFactoryPostProcessorExpectedLog));

        CustomBeanPostProcessor beanPostProcessor = new CustomBeanPostProcessor();
        factory.addBeanPostProcessor(beanPostProcessor);
        Student student = (Student) factory.getBean("student");
        String beanPostProcessorExpectedLog = "BeanPostProcessor is Registered Before Initialization";
        assertTrue(checkWhetherLoggerContains(beanPostProcessorExpectedLog));
    }

    private boolean checkWhetherLoggerContains(String expectedLogMessge) {
        boolean isLogExist = loggingEvents.stream()
            .anyMatch(logEvent -> logEvent.getMessage()
                .equals(expectedLogMessge));
        return isLogExist;
    }

    public static class LogAppender extends AppenderSkeleton {
        public List<LoggingEvent> events = new ArrayList<LoggingEvent>();

        public void close() {
        }

        public boolean requiresLayout() {
            return false;
        }

        @Override
        protected void append(LoggingEvent event) {
            events.add(event);
        }

    }
}
