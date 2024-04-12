package com.baeldung.apiservice.adapters.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.apiservice.adapters.tasks.Task;
import com.baeldung.apiservice.adapters.tasks.TaskRepository;
import com.baeldung.apiservice.adapters.users.UserRepository;

@RequestMapping("/")
@RestController
public class TasksController {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable("id") String id) {
        Task task = taskRepository.getTaskById(id);

        if (task == null) {
            throw new UnknownTaskException();
        }

        return buildResponse(task);
    }

    private TaskResponse buildResponse(Task task) {
        return new TaskResponse(task.id(), task.title(), task.created(), getUser(task.createdBy()), getUser(task.assignedTo()), task.status());
    }

    private UserResponse getUser(String userId) {
        if (userId == null) {
            return null;
        }

        var user = userRepository.getUserById(userId);
        if (user == null) {
            return null;
        }

        return new UserResponse(user.id(), user.name());
    }

    @ExceptionHandler(UnknownTaskException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleUnknownTask() {
    }
}
