package com.baeldung.git;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommitInfoController {
    @Value("${git.build.user.name}")
    private String userName;

    @Value("${git.branch}")
    private String branch;

    @Value("${git.commit.id}")
    private String commitId;

    @Value("${git.build.user.email}")
    private String userMail;

    @RequestMapping("/commitId")
    public GitInfoDto getCommitId() {
        return new GitInfoDto(userMail, branch, commitId, userName);
    }
}
