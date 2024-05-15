package com.baeldung.properties.value;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:values.properties")
public record PriorityRecord(@Value("${priority:normal}") String priority) {
}
