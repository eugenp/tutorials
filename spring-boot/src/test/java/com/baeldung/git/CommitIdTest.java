package com.baeldung.git;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CommitIdApplication.class)
public class CommitIdTest {

    @Value("${git.commit.message.short}")
    private String commitMessage;

    @Value("${git.branch}")
    private String branch;

    @Value("${git.commit.id}")
    private String commitId;

    @Test
    public void shouldInjectGitInfoProperties() throws Exception {
        assertThat(commitMessage).isNotNull();
        assertThat(commitMessage).isNotEqualTo("${git.commit.message.short}");
        assertThat(branch).isNotNull();
        assertThat(branch).isNotEqualTo("${git.branch}");
        assertThat(commitId).isNotNull();
        assertThat(commitId).isNotEqualTo("${git.commit.id}");
    }
}