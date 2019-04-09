package com.baeldung.jgit;

import com.baeldung.jgit.helper.Helper;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.assertNotNull;

/**
 * Tests which show issues with JGit that we reported upstream.
 */
public class JGitBugIntegrationTest {
    @Test
    public void testRevWalkDisposeClosesReader() throws IOException {
        try (Repository repo = Helper.openJGitRepository()) {
            try (ObjectReader reader = repo.newObjectReader()) {
                try (RevWalk walk = new RevWalk(reader)) {
                    walk.dispose();

                    Ref head = repo.exactRef("refs/heads/master");
                    System.out.println("Found head: " + head);

                    ObjectLoader loader = reader.open(head.getObjectId());
                    assertNotNull(loader);
                }
            }
        }
    }
}
