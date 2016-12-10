package org.baeldung.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.baeldung.entity.Task;
import org.baeldung.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class MainController {

    @Autowired
    private TaskRepository taskRepository;

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "loggedout";
    }

//    @RequestMapping(value = "/api/taskList", method = RequestMethod.POST, consumes = "application/json")
//    public ResponseEntity<Iterable<Task>> addTasks(@RequestBody Iterable<Task> newTasks) {
//        Iterable<Task> tasks = taskRepository.save(newTasks);
//
//        return ResponseEntity.ok().body(tasks);
//    }
}
