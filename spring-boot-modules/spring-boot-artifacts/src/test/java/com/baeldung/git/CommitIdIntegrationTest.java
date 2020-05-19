package com.baeldung.git;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CommitIdApplication.class)
@TestPropertySource(properties = { "spring.jmx.default-domain=test" })
public class CommitIdIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(CommitIdIntegrationTest.class);

    @Value("${git.commit.message.short:UNKNOWN}")
    private String commitMessage;

    @Value("${git.branch:UNKNOWN}")
    private String branch;

    @Value("${git.commit.id:UNKNOWN}")
    private String commitId;

    @Test
    public void whenInjecting_shouldDisplay() throws Exception {

        LOG.info(commitId);
        LOG.info(commitMessage);
        LOG.info(branch);

        assertThat(commitMessage).isNotEqualTo("UNKNOWN");

        assertThat(branch).isNotEqualTo("UNKNOWN");

        assertThat(commitId).isNotEqualTo("UNKNOWN");
    }
}