package com.baeldung.jgit.porcelain;

import java.io.File;
import java.io.IOException;
import com.baeldung.jgit.helper.Helper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple snippet which shows how to add a file to the index
 *
 * 
 */
public class AddFile {

    private static final Logger logger = LoggerFactory.getLogger(AddFile.class);

    public static void main(String[] args) throws IOException, GitAPIException {
        // prepare a new test-repository
        try (Repository repository = Helper.createNewRepository()) {
            try (Git git = new Git(repository)) {
                // create the file
                File myfile = new File(repository.getDirectory().getParent(), "testfile");
                if(!myfile.createNewFile()) {
                    throw new IOException("Could not create file " + myfile);
                }

                // run the add-call
                git.add()
                        .addFilepattern("testfile")
                        .call();

                logger.debug("Added file " + myfile + " to repository at " + repository.getDirectory());
            }
        }
    }
}
