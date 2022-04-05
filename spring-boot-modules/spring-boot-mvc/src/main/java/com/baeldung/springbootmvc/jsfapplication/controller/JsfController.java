package com.baeldung.springbootmvc.jsfapplication.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "session")
@Component(value = "jsfController")
public class JsfController {

    public String loadTodoPage() {
        checkPermission();
        return "/todo.xhtml";
    }

    private void checkPermission() {
        // Details omitted
    }

}
