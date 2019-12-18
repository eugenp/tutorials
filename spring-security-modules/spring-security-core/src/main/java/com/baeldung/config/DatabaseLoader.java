package com.baeldung.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.baeldung.entity.Task;
import com.baeldung.repository.TaskRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void run(String... strings) throws Exception {
        this.taskRepository.save(new Task("Send a fax", "pam"));
        this.taskRepository.save(new Task("Print a document", "pam"));
        this.taskRepository.save(new Task("Answer the phone", "pam"));
        this.taskRepository.save(new Task("Call a client", "jim"));
        this.taskRepository.save(new Task("Organize a meeting", "michael"));
    }
}