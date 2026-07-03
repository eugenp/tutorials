package com.baeldung.jcasbin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.casbin.jcasbin.main.Enforcer;
import org.casbin.jcasbin.model.Model;
import org.casbin.jcasbin.persist.file_adapter.FileAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class ManagementUnitTest {

    @TempDir
    private Path tempDir;

    @Test
    void whenQueryingSubjects_thenTheCorrectSubjectsAreReturned() throws IOException {
        FileAdapter fileAdapter = new FileAdapter(getClass().getResourceAsStream("/com/baeldung/jcasbin/acl.csv"));

        String content = new String(getClass().getClassLoader().getResourceAsStream("com/baeldung/jcasbin/acl.conf").readAllBytes());
        Model model = new Model();
        model.loadModelFromText(content);

        Enforcer enforcer = new Enforcer(model, fileAdapter);

        List<String> subjects = enforcer.getAllSubjects();
        assertEquals(2, subjects.size());
        assertTrue(subjects.contains("alice"));
        assertTrue(subjects.contains("bob"));
    }

    @Test
    void whenQueryingObjects_thenTheCorrectObjectsAreReturned() throws IOException {
        FileAdapter fileAdapter = new FileAdapter(getClass().getResourceAsStream("/com/baeldung/jcasbin/acl.csv"));

        String content = new String(getClass().getClassLoader().getResourceAsStream("com/baeldung/jcasbin/acl.conf").readAllBytes());
        Model model = new Model();
        model.loadModelFromText(content);

        Enforcer enforcer = new Enforcer(model, fileAdapter);

        List<String> objects = enforcer.getAllObjects();
        assertEquals(2, objects.size());
        assertTrue(objects.contains("data1"));
        assertTrue(objects.contains("data2"));
    }

    @Test
    void whenQueryingActions_thenTheCorrectActionsAreReturned() throws IOException {
        FileAdapter fileAdapter = new FileAdapter(getClass().getResourceAsStream("/com/baeldung/jcasbin/acl.csv"));

        String content = new String(getClass().getClassLoader().getResourceAsStream("com/baeldung/jcasbin/acl.conf").readAllBytes());
        Model model = new Model();
        model.loadModelFromText(content);

        Enforcer enforcer = new Enforcer(model, fileAdapter);

        List<String> actions = enforcer.getAllActions();
        assertEquals(2, actions.size());
        assertTrue(actions.contains("read"));
        assertTrue(actions.contains("write"));
    }

    @Test
    void givenAclConfiguration_whenQueryingAllowedActions_thenTheCorrectActionsAreReturned() throws IOException {
        FileAdapter fileAdapter = new FileAdapter(getClass().getResourceAsStream("/com/baeldung/jcasbin/acl.csv"));

        String content = new String(getClass().getClassLoader().getResourceAsStream("com/baeldung/jcasbin/acl.conf").readAllBytes());
        Model model = new Model();
        model.loadModelFromText(content);

        Enforcer enforcer = new Enforcer(model, fileAdapter);

        Set<String> actions = enforcer.getPermittedActions("alice", "data1");
        assertEquals(1, actions.size());
        assertTrue(actions.contains("read"));
    }

    @Test
    void givenRbacConfiguration_whenQueryingAllowedActions_thenTheCorrectActionsAreReturned() throws IOException {
        FileAdapter fileAdapter = new FileAdapter(getClass().getResourceAsStream("/com/baeldung/jcasbin/rbac.csv"));

        String content = new String(getClass().getClassLoader().getResourceAsStream("com/baeldung/jcasbin/rbac.conf").readAllBytes());
        Model model = new Model();
        model.loadModelFromText(content);

        Enforcer enforcer = new Enforcer(model, fileAdapter);

        Set<String> actions = enforcer.getPermittedActions("carol", "data2");
        assertEquals(2, actions.size());
        assertTrue(actions.contains("read"));
        assertTrue(actions.contains("write"));
    }

    @Test
    void whenQueryingRoles_thenTheCorrectRolesAreReturned() throws IOException {
        FileAdapter fileAdapter = new FileAdapter(getClass().getResourceAsStream("/com/baeldung/jcasbin/rbac.csv"));

        String content = new String(getClass().getClassLoader().getResourceAsStream("com/baeldung/jcasbin/rbac.conf").readAllBytes());
        Model model = new Model();
        model.loadModelFromText(content);

        Enforcer enforcer = new Enforcer(model, fileAdapter);

        List<String> roles = enforcer.getRolesForUser("carol");
        assertEquals(1, roles.size());
        assertTrue(roles.contains("superuser"));
    }

    @Test
    void whenAssigningPermissions_thenTheNewPermissionsWork() throws IOException {
        FileAdapter fileAdapter = new FileAdapter(getClass().getResourceAsStream("/com/baeldung/jcasbin/rbac.csv"));

        String content = new String(getClass().getClassLoader().getResourceAsStream("com/baeldung/jcasbin/rbac.conf").readAllBytes());
        Model model = new Model();
        model.loadModelFromText(content);

        Enforcer enforcer = new Enforcer(model, fileAdapter);

        assertFalse(enforcer.enforce("alice", "data2", "read"));

        enforcer.addPermissionForUser("alice", "data2", "read");

        assertTrue(enforcer.enforce("alice", "data2", "read"));
    }

    @Test
    void whenAssigningRoles_thenTheNewPermissionsWork() throws IOException {
        FileAdapter fileAdapter = new FileAdapter(getClass().getResourceAsStream("/com/baeldung/jcasbin/rbac.csv"));

        String content = new String(getClass().getClassLoader().getResourceAsStream("com/baeldung/jcasbin/rbac.conf").readAllBytes());
        Model model = new Model();
        model.loadModelFromText(content);

        Enforcer enforcer = new Enforcer(model, fileAdapter);

        assertFalse(enforcer.enforce("alice", "data2", "read"));

        enforcer.addRoleForUser("alice", "superuser");

        assertTrue(enforcer.enforce("alice", "data2", "read"));
    }

    @Test
    void whenSavingChanges_thenTheNewPermissionsWork() throws IOException {
        Path tempFile = tempDir.resolve("rbac.csv");
        try (InputStream in = getClass().getResourceAsStream("/com/baeldung/jcasbin/rbac.csv")) {
            Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
        }

        String content = new String(getClass().getClassLoader().getResourceAsStream("com/baeldung/jcasbin/rbac.conf").readAllBytes());
        Model model = new Model();
        model.loadModelFromText(content);

        {
            Enforcer enforcer = new Enforcer(model, new FileAdapter(tempFile.toString()));

            assertFalse(enforcer.enforce("alice", "data2", "read"));

            enforcer.addRoleForUser("alice", "superuser");
            enforcer.savePolicy();
        }

        {
            Enforcer enforcer = new Enforcer(model, new FileAdapter(tempFile.toString()));

            assertTrue(enforcer.enforce("alice", "data2", "read"));
        }
    }
}
