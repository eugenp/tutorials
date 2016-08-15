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

    @Value("${git.commit.message.short:#{null}}")
    private String commitMessage;

    @Value("${git.branch:#{null}}")
    private String branch;

    @Value("${git.commit.id:#{null}}")
    private String commitId;

    @Test
    public void shouldInjectGitInfoProperties() throws Exception {
        assertThat(commitMessage)
          .isNotNull();

        assertThat(branch)
          .isNotNull();

        assertThat(commitId)
          .isNotNull();
    }
}