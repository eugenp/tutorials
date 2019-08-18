package com.baeldung.jgit;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 * Simple snippet which shows how to create a new repository
 *
 * 
 */
public class CreateNewRepository {

    public static void main(String[] args) throws IOException, IllegalStateException, GitAPIException {
        // prepare a new folder
        File localPath = File.createTempFile("TestGitRepository", "");
        if(!localPath.delete()) {
            throw new IOException("Could not delete temporary file " + localPath);
        }

        // create the directory
        try (Git git = Git.init().setDirectory(localPath).call()) {
            System.out.println("Having repository: " + git.getRepository().getDirectory());
        }

        FileUtils.deleteDirectory(localPath);
    }
}
