package com.baeldung.solon.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Transaction;

import com.baeldung.solon.model.Task;
import com.baeldung.solon.persistence.TaskMapper;
import com.mybatisflex.core.query.QueryWrapper;

@Component
public class TaskService {

    @Inject
    TaskMapper taskMapper;

    public List<Task> findAll() {
        return taskMapper.selectListByQuery(QueryWrapper.create().orderBy("id", true));
    }

    public Task findById(long id) {
        Task task = taskMapper.selectOneById(id);
        if (task == null) {
            throw new NoSuchElementException("Task not found");
        }
        return task;
    }

    @Transaction
    public Task create(String title) {
        Task task = new Task();
        task.setTitle(normalizeTitle(title));
        task.setCompleted(false);
        taskMapper.insert(task);
        return task;
    }

    @Transaction
    public Task update(long id, String title, boolean completed) {
        Task task = findById(id);
        task.setTitle(normalizeTitle(title));
        task.setCompleted(completed);
        if (taskMapper.update(task) == 0) {
            throw new NoSuchElementException("Task not found");
        }
        return task;
    }

    @Transaction
    public void delete(long id) {
        if (taskMapper.deleteById(id) == 0) {
            throw new NoSuchElementException("Task not found");
        }
    }

    private String normalizeTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title must not be blank");
        }
        String normalized = title.strip();
        if (normalized.length() > 200) {
            throw new IllegalArgumentException("Title must contain at most 200 characters");
        }
        return normalized;
    }
}
