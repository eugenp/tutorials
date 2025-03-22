package com.baeldung.githubapi;

import com.google.common.base.Charsets;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepositoryUnitTest {
    @Test
    void whenWeListAUsersRepositories_thenWeCanAccessTheRepositories() throws IOException {
        GitHub gitHub = GitHub.connectAnonymously();

        GHUser user = gitHub.getUser("eugenp");
        List<GHRepository> repositoriesList = user.listRepositories().toList();
        assertEquals(6, repositoriesList.size());
    }

    @Test
    void whenWeIterateAUsersRepositories_thenWeCanAccessTheRepositories() throws IOException {
        GitHub gitHub = GitHub.connectAnonymously();

        GHUser user = gitHub.getUser("eugenp");

        Set<String> names = new HashSet<>();
        for (GHRepository ghRepository : user.listRepositories()) {
            names.add(ghRepository.getName());
        }

        assertEquals(6, names.size());
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
        System.out.println(commit.getCommitShortInfo().getMessage());
    }

    @Test
    void whenWeAccessARepositoryBranch_thenWeCanAccessFiles() throws IOException {
        GitHub gitHub = GitHub.connectAnonymously();

        GHRepository repository = gitHub.getRepository("eugenp/tutorials");

        String defaultBranch = repository.getDefaultBranch();
        GHContent file = repository.getFileContent("pom.xml", defaultBranch);

        String fileContents = IOUtils.toString(file.read(), Charsets.UTF_8);
        System.out.println(fileContents);
    }

    @Test
    void whenWeAccessTheRepository_thenWeCanDirectlyAccessTheReadme() throws IOException {
        GitHub gitHub = GitHub.connectAnonymously();

        GHRepository repository = gitHub.getRepository("eugenp/tutorials");

        GHContent readme = repository.getReadme();
        String fileContents = IOUtils.toString(readme.read(), Charsets.UTF_8);
        System.out.println(fileContents);
    }
}
