package com.baeldung.apiservice.adapters.http;

import java.time.Instant;

public record TaskResponse(String id,
                           String title,
                           Instant created,
                           UserResponse createdBy,
                           UserResponse assignedTo,
                           String status) {
}
