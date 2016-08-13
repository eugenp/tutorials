package com.baeldung.git;

public class GitInfoDto {
    private String commitMessage;
    private String branch;
    private String commitId;

    public GitInfoDto(String commitMessage, String branch, String commitId) {
        this.commitMessage = commitMessage;
        this.branch = branch;
        this.commitId = commitId;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public String getBranch() {
        return branch;
    }

    public String getCommitId() {
        return commitId;
    }
}
