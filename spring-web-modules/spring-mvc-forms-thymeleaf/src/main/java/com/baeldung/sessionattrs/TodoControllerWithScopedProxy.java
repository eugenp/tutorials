package com.baeldung.sessionattrs;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/scopedproxy")
public class TodoControllerWithScopedProxy {

    private TodoList todos;

    public TodoControllerWithScopedProxy(TodoList todos) {
        this.todos = todos;
    }

    @GetMapping("/form")
    public String showForm(Model model) {
        if (!todos.isEmpty()) {
            model.addAttribute("todo", todos.peekLast());
        } else {
            model.addAttribute("todo", new TodoItem());
        }

        return "scopedproxyform";
    }

    @PostMapping("/form")
    public String create(@ModelAttribute TodoItem todo) {
        todo.setCreateDate(LocalDateTime.now());
        todos.add(todo);
        return "redirect:/scopedproxy/todos.html";
    }

    @GetMapping("/todos.html")
    public String list(Model model) {
        model.addAttribute("todos", todos);
        return "scopedproxytodos";
    }
}
