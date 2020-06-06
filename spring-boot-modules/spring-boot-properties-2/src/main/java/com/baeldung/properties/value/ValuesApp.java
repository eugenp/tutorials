package com.baeldung.properties.value;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
	
    @Value("#{${valuesMap}}")
    private Map<String, Integer> valuesMap;

    @Value("#{${valuesMap}.key1}")
    private Integer valuesMapKey1;

    @Value("#{${valuesMap}['unknownKey']}")
    private Integer unknownMapKey;

    @Value("#{${unknownMap : {key1:'1', key2 : '2'}}}")
    private Map<String, Integer> unknownMap;

    @Value("#{${valuesMap}['unknownKey'] ?: 5}")
    private Integer unknownMapKeyWithDefaultValue;

    @Value("#{${valuesMap}.?[value>'1']}")
    private Map<String, Integer> valuesMapFiltered;

    @Value("#{systemProperties}")
    private Map<String, String> systemPropertiesMap;

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
        System.out.println(valuesMap);
        System.out.println(valuesMapKey1);
        System.out.println(unknownMapKey);
        System.out.println(unknownMap);
        System.out.println(unknownMapKeyWithDefaultValue);
        System.out.println(valuesMapFiltered);
        System.out.println(systemPropertiesMap);
    }
}
