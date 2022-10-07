package com.baeldung.features.record.deepcopy;

import java.util.List;

public record Project(List<Task> tasks) {

    public Project {
        tasks = List.copyOf(tasks);
    }

    public Project copy() {
        return new Project(this.tasks());
    }
}
