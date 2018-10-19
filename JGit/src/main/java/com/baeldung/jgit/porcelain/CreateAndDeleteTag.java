package com.baeldung.jgit.porcelain;

import java.io.IOException;
import com.baeldung.jgit.helper.Helper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

/**
 * Simple snippet which shows how to create a tag
 *
 * 
 */
public class CreateAndDeleteTag {

    public static void main(String[] args) throws IOException, GitAPIException {
        // prepare test-repository
        try (Repository repository = Helper.openJGitRepository()) {
            try (Git git = new Git(repository)) {
                // remove the tag before creating it
                git.tagDelete().setTags("tag_for_testing").call();

                // set it on the current HEAD
                Ref tag = git.tag().setName("tag_for_testing").call();
                System.out.println("Created/moved tag " + tag + " to repository at " + repository.getDirectory());

                // remove the tag again
                git.tagDelete().setTags("tag_for_testing").call();

                // read some other commit and set the tag on it
                ObjectId id = repository.resolve("HEAD^");
                try (RevWalk walk = new RevWalk(repository)) {
                    RevCommit commit = walk.parseCommit(id);
                    tag = git.tag().setObjectId(commit).setName("tag_for_testing").call();
                    System.out.println("Created/moved tag " + tag + " to repository at " + repository.getDirectory());

                    // remove the tag again
                    git.tagDelete().setTags("tag_for_testing").call();

                    // create an annotated tag
                    tag = git.tag().setName("tag_for_testing").setAnnotated(true).call();
                    System.out.println("Created/moved tag " + tag + " to repository at " + repository.getDirectory());

                    // remove the tag again
                    git.tagDelete().setTags("tag_for_testing").call();

                    walk.dispose();
                }
            }
        }
    }
}
