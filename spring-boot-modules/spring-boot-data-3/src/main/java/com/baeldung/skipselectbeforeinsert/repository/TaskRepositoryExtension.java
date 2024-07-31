package com.baeldung.skipselectbeforeinsert.repository;

import com.baeldung.skipselectbeforeinsert.model.Task;

public interface TaskRepositoryExtension {
    Task persistAndFlush(Task task);
}
