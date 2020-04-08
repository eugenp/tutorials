package com.baeldung.domain;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import com.baeldung.application.response.AppMetaDataResponse;
import com.baeldung.domain.exception.DomainException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class ChgRequest {
    private UUID id;

    private String app;
    private String build;
    private String jira;
    private String environment;
    private String releaseNotes;

    private String status;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    public ChgRequest(final UUID id, final AppMetadata appMetadata) {
        this.id = id;
        this.app = appMetadata.getApp();
        this.build = appMetadata.getBuild();
        this.jira = appMetadata.getJira();
        this.environment = appMetadata.getEnvironment();
        this.releaseNotes = appMetadata.getReleaseNotes();
        this.status = ChgRequestStatus.CREATED.name();
        createdDateTime = updatedDateTime = LocalDateTime.now(ZoneOffset.UTC);
    }

    public AppMetaDataResponse appMetaDataResponse() {
        return new AppMetaDataResponse(
                id,
                app,
                build,
                jira,
                environment,
                releaseNotes,
                status,
                createdDateTime,
                updatedDateTime
        );
    }

    public void createChgRequest(final AppMetadata appMetadata) {
        validateChgRequest(appMetadata);
        status = ChgRequestStatus.CREATED.name();
        createdDateTime = updatedDateTime = LocalDateTime.now(ZoneOffset.UTC);
    }

    public void beginChgRequest() {
        status = ChgRequestStatus.IN_PROGRESS.name();
        updatedDateTime = LocalDateTime.now(ZoneOffset.UTC);
    }

    public void markChgRequestAsDone(){
        status = ChgRequestStatus.DONE.name();
        updatedDateTime = LocalDateTime.now(ZoneOffset.UTC);
    }

    public void rollingBackChgReq(){
        status = ChgRequestStatus.ROLLBACK.name();
        updatedDateTime = LocalDateTime.now(ZoneOffset.UTC);
    }

    private void validateChgRequest(final AppMetadata appMetadata) {
        if (appMetadata == null) {
            throw new DomainException("The appMetadata cannot be null.");
        }
    }
}
