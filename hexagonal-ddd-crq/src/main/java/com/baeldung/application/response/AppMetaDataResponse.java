package com.baeldung.application.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppMetaDataResponse {
    private UUID id;
    private String app;
    private String build;
    private String jira;
    private String environment;
    private String releaseNotes;
    private String chgRequestStatus;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
}
