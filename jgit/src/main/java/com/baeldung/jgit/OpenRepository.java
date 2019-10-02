package com.baeldung.jgit;

import com.baeldung.jgit.helper.Helper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import java.io.File;
import java.io.IOException;

/**
 * Simple snippet which shows how to open an existing repository
 *
 * 
 */
public class OpenRepository {

    public static void main(String[] args) throws IOException, GitAPIException {
        // first create a test-repository, the return is including the .get directory here!
        File repoDir = createSampleGitRepo();

        // now open the resulting repository with a FileRepositoryBuilder
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        try (Repository repository = builder.setGitDir(repoDir)
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build()) {
            System.out.println("Having repository: " + repository.getDirectory());

            // the Ref holds an ObjectId for any type of object (tree, commit, blob, tree)
            Ref head = repository.exactRef("refs/heads/master");
            System.out.println("Ref of refs/heads/master: " + head);
        }
    }

    private static File createSampleGitRepo() throws IOException, GitAPIException {
        try (Repository repository = Helper.createNewRepository()) {
            System.out.println("Temporary repository at " + repository.getDirectory());

            // create the file
            File myfile = new File(repository.getDirectory().getParent(), "testfile");
            if(!myfile.createNewFile()) {
                throw new IOException("Could not create file " + myfile);
            }

            // run the add-call
            try (Git git = new Git(repository)) {
                git.add()
                        .addFilepattern("testfile")
                        .call();


                // and then commit the changes
                git.commit()
                        .setMessage("Added testfile")
                        .call();
            }

            System.out.println("Added file " + myfile + " to repository at " + repository.getDirectory());

            return repository.getDirectory();
        }
    }
}
