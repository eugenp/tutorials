package com.baeldung.jordaneval.hexagonalarchitecture.adapters;

import com.baeldung.jordaneval.hexagonalarchitecture.domain.ports.ToDoRepository;
import com.baeldung.jordaneval.hexagonalarchitecture.domain.ports.ToDoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    ToDoService toDoService( ToDoRepository toDoRepository ) {
        return new ToDoService( toDoRepository );
    }
}
