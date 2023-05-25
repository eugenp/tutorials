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

package com.baeldung.tasksservice.adapters.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksRepository extends JpaRepository<TaskRecord, String> {
    List<TaskRecord> findByStatus(String status);

    List<TaskRecord> findByCreatedBy(String createdBy);

    List<TaskRecord> findByStatusAndCreatedBy(String status, String createdBy);

    List<TaskRecord> findByAssignedTo(String assignedTo);
}
