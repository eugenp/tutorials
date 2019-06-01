package com.baeldung.application;

import com.baeldung.domain.TodoItemRepository;
import com.baeldung.domain.TodoItemService;
import com.baeldung.domain.TodoItemServiceImpl;
import com.baeldung.infrastructure.TodoItemRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean
    public TodoItemService todoItemService(){
        return new TodoItemServiceImpl(todoItemRepository());
    }

    @Bean
    public TodoItemRepository todoItemRepository(){
        return new TodoItemRepositoryImpl();
    }
}
