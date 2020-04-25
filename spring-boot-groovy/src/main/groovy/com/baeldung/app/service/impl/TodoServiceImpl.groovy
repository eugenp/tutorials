package com.baeldung.app.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import com.baeldung.app.entity.Todo
import com.baeldung.app.repository.TodoRepository
import com.baeldung.app.service.TodoService

@Service
class TodoServiceImpl implements TodoService {

    @Autowired
    TodoRepository todoRepository

    @Override
    List<Todo> findAll() {
        todoRepository.findAll()
    }

    @Override
    Todo findById(Integer todoId) {
        todoRepository.findById todoId get()
    }

    @Override
    Todo saveTodo(Todo todo){
        todoRepository.save todo
    }

    @Override
    Todo updateTodo(Todo todo){
        todoRepository.save todo
    }

    @Override
    Todo deleteTodo(Integer todoId){
        todoRepository.deleteById todoId
    }
}
