package com.baeldung.jgit.porcelain;

import java.io.IOException;
import com.baeldung.jgit.helper.Helper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple snippet which shows how to get the commit-ids for a file to provide log information.
 *
 * 
 */
public class Log {

    private static final Logger logger = LoggerFactory.getLogger(Log.class);

    @SuppressWarnings("unused")
    public static void main(String[] args) throws IOException, GitAPIException {
        try (Repository repository = Helper.openJGitRepository()) {
            try (Git git = new Git(repository)) {
                Iterable<RevCommit> logs = git.log()
                        .call();
                int count = 0;
                for (RevCommit rev : logs) {
                    logger.trace("Commit: " + rev /* + ", name: " + rev.getName() + ", id: " + rev.getId().getName() */);
                    count++;
                }
                logger.debug("Had " + count + " commits overall on current branch");

                logs = git.log()
                        .add(repository.resolve(git.getRepository().getFullBranch()))
                        .call();
                count = 0;
                for (RevCommit rev : logs) {
                    logger.trace("Commit: " + rev /* + ", name: " + rev.getName() + ", id: " + rev.getId().getName() */);
                    count++;
                }
                logger.debug("Had " + count + " commits overall on "+git.getRepository().getFullBranch());

                logs = git.log()
                        .all()
                        .call();
                count = 0;
                for (RevCommit rev : logs) {
                    logger.trace("Commit: " + rev /* + ", name: " + rev.getName() + ", id: " + rev.getId().getName() */);
                    count++;
                }
                logger.debug("Had " + count + " commits overall in repository");

                logs = git.log()
                        // for all log.all()
                        .addPath("README.md")
                        .call();
                count = 0;
                for (RevCommit rev : logs) {
                    logger.trace("Commit: " + rev /* + ", name: " + rev.getName() + ", id: " + rev.getId().getName() */);
                    count++;
                }
                logger.debug("Had " + count + " commits on README.md");

                logs = git.log()
                        // for all log.all()
                        .addPath("pom.xml")
                        .call();
                count = 0;
                for (RevCommit rev : logs) {
                    logger.trace("Commit: " + rev /* + ", name: " + rev.getName() + ", id: " + rev.getId().getName() */);
                    count++;
                }
                logger.debug("Had " + count + " commits on pom.xml");
            }
        }
    }
}
