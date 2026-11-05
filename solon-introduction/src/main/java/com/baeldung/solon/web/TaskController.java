package com.baeldung.solon.web;

import java.util.List;

import org.noear.solon.annotation.Body;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Delete;
import org.noear.solon.annotation.Get;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Post;
import org.noear.solon.annotation.Put;
import org.noear.solon.core.handle.Context;

import com.baeldung.solon.model.Task;
import com.baeldung.solon.service.TaskService;

@Controller
@Mapping("/tasks")
public class TaskController {

    @Inject
    private TaskService taskService;

    @Get
    @Mapping
    public List<Task> list() {
        return taskService.findAll();
    }

    @Get
    @Mapping("/{id}")
    public Task get(long id) {
        return taskService.findById(id);
    }

    @Post
    @Mapping
    public Task create(@Body TaskRequest request, Context context) {
        Task task = taskService.create(request.title());
        context.status(201);
        context.headerSet("Location", "/tasks/" + task.getId());
        return task;
    }

    @Put
    @Mapping("/{id}")
    public Task update(long id, @Body TaskRequest request) {
        return taskService.update(id, request.title(), Boolean.TRUE.equals(request.completed()));
    }

    @Delete
    @Mapping("/{id}")
    public void delete(long id, Context context) {
        taskService.delete(id);
        context.status(204);
    }
}
