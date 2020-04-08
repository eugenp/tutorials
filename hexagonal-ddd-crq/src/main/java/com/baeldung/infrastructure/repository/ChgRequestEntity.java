package com.baeldung.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;

import com.baeldung.domain.ChgRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChgRequestEntity {
    @PrimaryKey
    private UUID id;

    private String app;
    private String build;
    private String jira;
    private String environment;
    private String releaseNotes;
    private String status;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    public ChgRequestEntity(ChgRequest chgRequest) {
        this.id = chgRequest.getId();
        this.app = chgRequest.getApp();
        this.build = chgRequest.getBuild();
        this.jira = chgRequest.getJira();
        this.environment = chgRequest.getEnvironment();
        this.releaseNotes = chgRequest.getReleaseNotes();
        this.status = chgRequest.getStatus();
        this.createdDateTime = chgRequest.getCreatedDateTime();
        this.updatedDateTime = chgRequest.getUpdatedDateTime();
    }

    public ChgRequest toChgRequest() {
        return new ChgRequest(
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
}
