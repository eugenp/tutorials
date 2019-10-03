package com.baeldung.componentscan.filter.regex;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*[t]"))
public class ComponentScanRegexFilterApp {

    public static void main(String[] args) {
    }
}
