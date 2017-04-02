package com.baeldung.value;

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
    private String value1;

    @Value("${value.from.file}")
    private String value2;

    @Value("${systemValue}")
    private String value3;

    @Value("${unknown_param:some default}")
    private String value4;

    @Value("${priority}")
    private String value5;

    @Value("#{systemProperties['priority']}")
    private String value6;

    @Value("#{systemEnvironment['priority'] ?: 'default env'}")
    private String value7;

    @Value("#{someBean.someValue}")
    private Integer value8;

    @Value("#{'${listOfValues}'.split(',')}")
    private List<String> valuesList;

    public static void main(String[] args) {
        System.setProperty("systemValue", "Some system parameter value");
        System.setProperty("priority", "System property");
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ValuesApp.class);
    }

    @Bean
    public SomeBean someBean() {
        return new SomeBean(42);
    }

    @PostConstruct
    public void afterInitialize() {
        System.out.println(value1);
        System.out.println(value2);
        System.out.println(value3);
        System.out.println(value4);
        System.out.println(value5);
        System.out.println(value6);
        System.out.println(value7);
        System.out.println(value8);
        System.out.println(valuesList);
    }
}
