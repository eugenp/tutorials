package com.baeldung.beaninjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.beaninjection.domain.LoggingHelper;

public class MainApp {
    public static void main(String[] args) {

        LoggingHelper logHelper = loadFromJavaConfig();
        logHelper.logMessage("This is a log message");
        
        logHelper = loadFromXmlConfig();
        logHelper.logMessage("This is another log message");
    }

    private static LoggingHelper loadFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        
        LoggingHelper loggingHelper = context.getBean(LoggingHelper.class);
        ((ConfigurableApplicationContext)context).close();
        
        return loggingHelper;
    }
    
    private static LoggingHelper loadFromXmlConfig() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beaninjection.xml");

        LoggingHelper loggingHelper = context.getBean(LoggingHelper.class);
        ((ConfigurableApplicationContext)context).close();
        
        return loggingHelper;
    }
}
