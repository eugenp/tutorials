package com.baeldung.git;

public class GitInfoDto {
    private String mail;
    private String branch;
    private String commitId;
    private String userName;

    public GitInfoDto(String mail, String branch, String commitId, String userName) {
        this.mail = mail;
        this.branch = branch;
        this.commitId = commitId;
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public String getBranch() {
        return branch;
    }

    public String getCommitId() {
        return commitId;
    }

    public String getUserName() {
        return userName;
    }
}
