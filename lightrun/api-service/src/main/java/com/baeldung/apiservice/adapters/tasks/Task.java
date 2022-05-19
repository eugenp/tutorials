package com.baeldung.apiservice.adapters.tasks;

import java.time.Instant;

public record Task(String id,
                   String title,
                   Instant created,
                   String createdBy,
                   String assignedTo,
                   String status) {
}
