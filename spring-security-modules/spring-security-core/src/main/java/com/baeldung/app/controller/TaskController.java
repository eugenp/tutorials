package com.baeldung.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baeldung.app.entity.Task;
import com.baeldung.app.service.TaskService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired(required = false)
    private UserDetailsService userDetailsService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Task>> findAllTasks() {
        Iterable<Task> tasks = taskService.findAll();

        return ResponseEntity.ok().body(tasks);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Iterable<Task>> addTasks(@RequestBody Iterable<Task> newTasks) {
        Iterable<Task> tasks = taskService.save(newTasks);

        return ResponseEntity.ok().body(tasks);
    }

    /**
     * Example of restricting specific endpoints to specific roles using @PreAuthorize.
     */
    @GetMapping("/manager")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<Iterable<Task>> getAlManagerTasks()
    {
        Iterable<Task> tasks = taskService.findAll();

        return ResponseEntity.ok().body(tasks);
    }

    /**
     * Example of restricting specific endpoints to specific roles using SecurityContext.
     */
    @GetMapping("/actuator")
    public ResponseEntity<Iterable<Task>> getAlActuatorTasks()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ACTUATOR")))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Iterable<Task> tasks = taskService.findAll();

        return ResponseEntity.ok().body(tasks);
    }

    /**
     * Example of restricting specific endpoints to specific roles using UserDetailsService.
     */
    @GetMapping("/admin")
    public ResponseEntity<Iterable<Task>> getAlAdminTasks()
    {
        if(userDetailsService != null) {
            UserDetails details = userDetailsService.loadUserByUsername("pam");
            if (details != null && details.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        Iterable<Task> tasks = taskService.findAll();

        return ResponseEntity.ok().body(tasks);
    }

    /**
     * Example of restricting specific endpoints to specific roles using HttpServletRequest.
     */
    @GetMapping("/admin2")
    public ResponseEntity<Iterable<Task>> getAlAdminTasksUsingServlet(HttpServletRequest request)
    {
        if (!request.isUserInRole("ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Iterable<Task> tasks = taskService.findAll();

        return ResponseEntity.ok().body(tasks);
    }
}
