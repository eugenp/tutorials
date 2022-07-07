/****************************************************************************************************************
 *
 *  Copyright (c) 2022 OCLC, Inc. All Rights Reserved.
 *
 *  OCLC proprietary information: the enclosed materials contain
 *  proprietary information of OCLC, Inc. and shall not be disclosed in whole or in
 *  any part to any third party or used by any person for any purpose, without written
 *  consent of OCLC, Inc.  Duplication of any portion of these materials shall include this notice.
 *
 ******************************************************************************************************************/

package com.baeldung.tasksservice.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.tasksservice.adapters.repository.TaskRecord;
import com.baeldung.tasksservice.adapters.repository.TasksRepository;

@Service
public class TasksService {
    @Autowired
    private TasksRepository tasksRepository;

    public TaskRecord getTaskById(String id) {
        return tasksRepository.findById(id)
            .orElseThrow(() -> new UnknownTaskException(id));
    }

    @Transactional
    public void deleteTaskById(String id) {
        var task = tasksRepository.findById(id)
            .orElseThrow(() -> new UnknownTaskException(id));
        tasksRepository.delete(task);
    }

    public List<TaskRecord> search(Optional<String> createdBy, Optional<String> status) {
        if (createdBy.isPresent() && status.isPresent()) {
            return tasksRepository.findByStatusAndCreatedBy(status.get(), createdBy.get());
        } else if (createdBy.isPresent()) {
            return tasksRepository.findByCreatedBy(createdBy.get());
        } else if (status.isPresent()) {
            return tasksRepository.findByStatus(status.get());
        } else {
            return tasksRepository.findAll();
        }
    }

    @Transactional
    public TaskRecord updateTask(String id, Optional<String> newStatus, Optional<String> newAssignedTo) {
        var task = tasksRepository.findById(id)
            .orElseThrow(() -> new UnknownTaskException(id));

        newStatus.ifPresent(task::setStatus);
        newAssignedTo.ifPresent(task::setAssignedTo);

        return task;
    }

    public TaskRecord createTask(String title, String createdBy) {
        var task = new TaskRecord(UUID.randomUUID()
            .toString(), title, Instant.now(), createdBy, null, "PENDING");
        tasksRepository.save(task);
        return task;
    }
}
