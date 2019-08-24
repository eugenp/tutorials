package com.baeldung.hexagonal.springapp;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.core.TodoException;
import com.baeldung.hexagonal.core.TodoItem;
import com.baeldung.hexagonal.core.TodoService;

@RestController
@RequestMapping(path = "/todo")
public class TodoController {

    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public void addItem(@RequestParam("description") String description, @RequestParam("dueDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dueDate) throws TodoException {
        todoService.addTodoItem(description, dueDate);
    }

}
