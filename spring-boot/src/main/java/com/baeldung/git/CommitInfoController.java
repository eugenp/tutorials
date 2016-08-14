package com.baeldung.git;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommitInfoController {

    @Value("${git.commit.message.short}")
    private String commitMessage;

    @Value("${git.branch}")
    private String branch;

    @Value("${git.commit.id}")
    private String commitId;

    @RequestMapping("/commitId")
    public GitInfoDto getCommitId() {
        return new GitInfoDto(commitMessage, branch, commitId);
    }
}
