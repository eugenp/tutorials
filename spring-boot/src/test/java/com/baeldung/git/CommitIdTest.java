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

    private static final String UNKNOWN_STATUS = "UNKNOWN";

    @Value("${git.commit.message.short:#{'UNKNOWN'}}")
    private String commitMessage;

    @Value("${git.branch:#{'UNKNOWN'}")
    private String branch;

    @Value("${git.commit.id:#{'UNKNOWN'}")
    private String commitId;

    @Test
    public void shouldInjectGitInfoProperties() throws Exception {

        assertThat(commitMessage)
                .isNotEqualTo(UNKNOWN_STATUS);

        assertThat(branch)
                .isNotEqualTo(UNKNOWN_STATUS);

        assertThat(commitId)
                .isNotEqualTo(UNKNOWN_STATUS);
    }
}