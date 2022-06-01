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

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tasks")
public class TaskRecord {
    @Id
    @Column(name = "task_id")
    private String id;
    private String title;
    @Column(name = "created_at")
    private Instant created;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "assigned_to")
    private String assignedTo;
    private String status;

    public TaskRecord(final String id, final String title, final Instant created, final String createdBy, final String assignedTo, final String status) {
        this.id = id;
        this.title = title;
        this.created = created;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
        this.status = status;
    }

    private TaskRecord() {
        // Needed for JPA
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Instant getCreated() {
        return created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public String getStatus() {
        return status;
    }

    public void setAssignedTo(final String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public void setStatus(final String status) {
        this.status = status;
    }
}