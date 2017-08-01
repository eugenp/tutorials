package com.baeldung.value;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(name = "myProperties", value = "values.properties")
public class ValuesApp {

    @Value("string value")
    private String stringValue;

    @Value("${value.from.file}")
    private String valueFromFile;

    @Value("${systemValue}")
    private String systemValue;

    @Value("${unknown_param:some default}")
    private String someDefault;

    @Value("${priority}")
    private String prioritySystemProperty;

    @Value("${listOfValues}")
    private String[] valuesArray;

    @Value("#{systemProperties['priority']}")
    private String spelValue;

    @Value("#{systemProperties['unknown'] ?: 'some default'}")
    private String spelSomeDefault;

    @Value("#{someBean.someValue}")
    private Integer someBeanValue;

    @Value("#{'${listOfValues}'.split(',')}")
    private List<String> valuesList;

    public static void main(String[] args) {
        System.setProperty("systemValue", "Some system parameter value");
        System.setProperty("priority", "System property");
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ValuesApp.class);
    }

    @Bean
    public SomeBean someBean() {
        return new SomeBean(10);
    }

    @PostConstruct
    public void afterInitialize() {
        System.out.println(stringValue);
        System.out.println(valueFromFile);
        System.out.println(systemValue);
        System.out.println(someDefault);
        System.out.println(prioritySystemProperty);
        System.out.println(Arrays.toString(valuesArray));
        System.out.println(spelValue);
        System.out.println(spelSomeDefault);
        System.out.println(someBeanValue);
        System.out.println(valuesList);
    }
}
