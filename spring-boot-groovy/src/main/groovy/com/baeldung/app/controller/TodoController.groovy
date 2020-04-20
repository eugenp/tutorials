package com.baeldung.app.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import com.baeldung.app.entity.Todo
import com.baeldung.app.service.TodoService

@RestController
@RequestMapping('todo')
public class TodoController {

    @Autowired
    TodoService todoService

    @GetMapping
    List<Todo> getAllTodoList(){
        todoService.findAll()
    }

    @PostMapping
    Todo saveTodo(@RequestBody Todo todo){
        todoService.saveTodo todo
    }

    @PutMapping
    Todo updateTodo(@RequestBody Todo todo){
        todoService.updateTodo todo
    }

    @DeleteMapping('/{todoId}')
    deleteTodo(@PathVariable Integer todoId){
        todoService.deleteTodo todoId
    }

    @GetMapping('/{todoId}')
    Todo getTodoById(@PathVariable Integer todoId){
        todoService.findById todoId
    }
}