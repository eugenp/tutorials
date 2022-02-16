package com.baeldung.businesslogic.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.baeldung.businesslogic.entity.Todo;

@Service
public class SecurityService {
    public boolean canUpdateTodo(Todo todo, User user) {
        return todo.getCreatedBy()
            .equals(user.getUsername()) && todo.isEditable();
    }
}
