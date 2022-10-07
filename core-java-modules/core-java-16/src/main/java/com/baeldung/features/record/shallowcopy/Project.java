package com.baeldung.features.record.shallowcopy;

import java.util.List;

public record Project(List<Task> tasks) {
<<<<<<< HEAD
=======

    public Project copy() {
        return new Project(this.tasks());
    }
>>>>>>> 987d59b3ba (article: Shallow Copy vs Deep Copy on Records)
}
