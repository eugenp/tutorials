package com.baeldung.componentscan.filter.assignable;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = Animal.class))
public class ComponentScanAssignableTypeFilterApp {

    public static void main(String[] args) {
    }
}
