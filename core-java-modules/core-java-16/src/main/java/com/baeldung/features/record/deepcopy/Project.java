package com.baeldung.features.record.deepcopy;

import java.util.List;

public record Project(List<Task> tasks) {

    public Project {
        tasks = List.copyOf(tasks);
    }
<<<<<<< HEAD
=======

    public Project copy() {
        return new Project(this.tasks());
    }
>>>>>>> 987d59b3ba (article: Shallow Copy vs Deep Copy on Records)
}
