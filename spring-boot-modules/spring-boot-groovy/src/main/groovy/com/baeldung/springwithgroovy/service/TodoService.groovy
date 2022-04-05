package com.baeldung.springwithgroovy.service

import com.baeldung.springwithgroovy.entity.Todo

interface TodoService {

    List<Todo> findAll()

    Todo findById(Integer todoId)

    Todo saveTodo(Todo todo)

    Todo updateTodo(Todo todo)

    Todo deleteTodo(Integer todoId)
}
