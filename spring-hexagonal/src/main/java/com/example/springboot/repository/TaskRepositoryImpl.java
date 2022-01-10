package com.example.springboot.repository;

import com.example.springboot.domain.Task;
import com.example.springboot.repository.mongodb.TaskMongoDBRepository;
import com.example.springboot.repository.postgres.TaskPostgresDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    @Autowired
    private TaskMongoDBRepository dbRepository;

    @Override
    public Task createTask(Task task) {
        return dbRepository.save(task);
    }

    @Override
    public Task readTask(String id) {
        Optional<Task> task = dbRepository.findById(id);
        return task.isPresent() ? task.get() : null;
    }

    @Override
    public Task updateTask(Task task) {
        return dbRepository.save(task);
    }

    @Override
    public boolean deleteTask(String id) {
        dbRepository.deleteById(id);
        return true;
    }
}
