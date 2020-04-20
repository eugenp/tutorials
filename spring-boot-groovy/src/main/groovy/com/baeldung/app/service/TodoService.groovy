package com.baeldung.app.service

import com.baeldung.app.entity.Todo

interface TodoService {

    List<Todo> findAll()

    Todo findById(Integer todoId)

    Todo saveTodo(Todo todo)

    Todo updateTodo(Todo todo)

    Todo deleteTodo(Integer todoId)
}
