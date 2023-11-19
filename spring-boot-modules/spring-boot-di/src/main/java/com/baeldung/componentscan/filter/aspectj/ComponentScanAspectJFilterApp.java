package com.baeldung.componentscan.filter.aspectj;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ASPECTJ,
        pattern = "com.baeldung.componentscan.filter.aspectj.* "
                + "&& !(com.baeldung.componentscan.filter.aspectj.L* "
                + "|| com.baeldung.componentscan.filter.aspectj.C*)"))
public class ComponentScanAspectJFilterApp {
    public static void main(String[] args) {
    }
}
