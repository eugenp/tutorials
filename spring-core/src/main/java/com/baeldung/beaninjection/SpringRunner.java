package com.baeldung.beaninjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringRunner {

    public static void main(String[] args) {

        TaskLogger taskLogger = getTaskLoggerUsingConstructorFromXml();

        System.out.println(taskLogger.log() + " using constructor injection from xml");

        taskLogger = getTaskLoggerUsingConstructorFromJavaConfig();

        System.out.println(taskLogger.log() + " using constructor injection from java");

        DutyLogger dutyLogger = getDutyLoggerUsingSetterFromXml();

        System.out.println(dutyLogger.log() + " using setter injection from xml");

        dutyLogger = getDutyLoggerUsingSetterFromJavaConfig();

        System.out.println(dutyLogger.log() + " using setter injection from java");
    }

    private static TaskLogger getTaskLoggerUsingConstructorFromJavaConfig() {

        ApplicationContext context
          = new AnnotationConfigApplicationContext(Config.class);

        return context.getBean(TaskLogger.class);
    }

    private static TaskLogger getTaskLoggerUsingConstructorFromXml() {

        ApplicationContext context
          = new ClassPathXmlApplicationContext("constructor.xml");

        return context.getBean(TaskLogger.class);
    }

    private static DutyLogger getDutyLoggerUsingSetterFromJavaConfig() {

        ApplicationContext context
          = new AnnotationConfigApplicationContext(Config.class);

        return context.getBean(DutyLogger.class);
    }

    private static DutyLogger getDutyLoggerUsingSetterFromXml() {

        ApplicationContext context
          = new ClassPathXmlApplicationContext("setter.xml");

        return context.getBean(DutyLogger.class);
    }
}
