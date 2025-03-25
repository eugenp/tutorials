package com.baeldung.githubapi;

import com.google.common.base.Charsets;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepositoryLiveTest {
    private static final Logger LOG = LoggerFactory.getLogger(RepositoryLiveTest.class);

    @Test
    void whenWeListAUsersRepositories_thenWeCanAccessTheRepositories() throws IOException {
        GitHub gitHub = GitHub.connectAnonymously();

        GHUser user = gitHub.getUser("eugenp");
        List<GHRepository> repositoriesList = user.listRepositories().toList();
        assertThat(repositoriesList).isNotEmpty();
    }

    @Test
    void whenWeIterateAUsersRepositories_thenWeCanAccessTheRepositories() throws IOException {
        GitHub gitHub = GitHub.connectAnonymously();

        GHUser user = gitHub.getUser("eugenp");

        Set<String> names = new HashSet<>();
        for (GHRepository ghRepository : user.listRepositories()) {
            names.add(ghRepository.getName());
        }

        assertThat(names).isNotEmpty();
    }

    @Test
    void whenWeDirectlyAccessAUsersRepository_thenWeCanQueryRepositoryDetails() throws IOException {
        GitHub gitHub = GitHub.connectAnonymously();

        GHUser user = gitHub.getUser("eugenp");
        GHRepository repository = user.getRepository("tutorials");
        assertEquals("tutorials", repository.getName());
        assertEquals("eugenp/tutorials", repository.getFullName());
    }

    @Test
    void whenWeDirectlyAccessARepository_thenWeCanQueryRepositoryDetails() throws IOException {
        GitHub gitHub = GitHub.connectAnonymously();

        GHRepository repository = gitHub.getRepository("eugenp/tutorials");
        assertEquals("tutorials", repository.getName());
        assertEquals("eugenp/tutorials", repository.getFullName());
    }

    @Test
    void whenWeAccessARepositoryBranch_thenWeCanAccessCommitDetails() throws IOException {
        GitHub gitHub = GitHub.connectAnonymously();

        GHRepository repository = gitHub.getRepository("eugenp/tutorials");

        String defaultBranch = repository.getDefaultBranch();
        GHBranch branch = repository.getBranch(defaultBranch);
        String branchHash = branch.getSHA1();

        GHCommit commit = repository.getCommit(branchHash);
        LOG.info("Commit message: {}", commit.getCommitShortInfo().getMessage());
    }

    @Test
    void whenWeAccessARepositoryBranch_thenWeCanAccessFiles() throws IOException {
        GitHub gitHub = GitHub.connectAnonymously();

        GHRepository repository = gitHub.getRepository("eugenp/tutorials");

        String defaultBranch = repository.getDefaultBranch();
        GHContent file = repository.getFileContent("pom.xml", defaultBranch);

        String fileContents = IOUtils.toString(file.read(), Charsets.UTF_8);
        LOG.info("pom.xml file contents: {}", fileContents);
    }

    @Test
    void whenWeAccessTheRepository_thenWeCanDirectlyAccessTheReadme() throws IOException {
        GitHub gitHub = GitHub.connectAnonymously();

        GHRepository repository = gitHub.getRepository("eugenp/tutorials");

        GHContent readme = repository.getReadme();
        String fileContents = IOUtils.toString(readme.read(), Charsets.UTF_8);
        LOG.info("Readme file contents: {}", fileContents);
    }
}
