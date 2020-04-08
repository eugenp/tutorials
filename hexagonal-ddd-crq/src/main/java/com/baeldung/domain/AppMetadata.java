package com.baeldung.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class AppMetadata {
    private final String app;
    private final String build;
    private final String jira;
    private final String environment;
    private final String releaseNotes;

    @JsonCreator
    public AppMetadata(
            @JsonProperty("app") final String app,
            @JsonProperty("build") final String build,
            @JsonProperty("jira") final String jira,
            @JsonProperty("environment") final String environment,
            @JsonProperty("releaseNotes") final String releaseNotes) {
        this.app = app;
        this.build = build;
        this.jira = jira;
        this.environment = environment;
        this.releaseNotes = releaseNotes;
    }
}
