package com.baeldung.tasksservice.service;

import javax.transaction.Transactional;

import com.baeldung.tasksservice.adapters.repository.TaskRecord;
import com.baeldung.tasksservice.adapters.repository.TasksRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeletedUserService {
  @Autowired
  private TasksRepository tasksRepository;

  @Transactional
  public void handleDeletedUser(String user) {
    var ownedByUser = tasksRepository.findByCreatedBy(user);
    tasksRepository.deleteAll(ownedByUser);

    var assignedToUser = tasksRepository.findByAssignedTo(user);
    for (TaskRecord record : assignedToUser) {
      record.setAssignedTo(null);
      record.setStatus("PENDING");
    }
  }
}
