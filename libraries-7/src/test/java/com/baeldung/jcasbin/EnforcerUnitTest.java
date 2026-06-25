package com.baeldung.jcasbin;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.casbin.jcasbin.main.Enforcer;
import org.casbin.jcasbin.model.Model;
import org.casbin.jcasbin.persist.file_adapter.FileAdapter;
import org.junit.jupiter.api.Test;

class EnforcerUnitTest {

    @Test
    void givenAclConfiguration_whenCheckingPermissions_thenTheCorrectResultsAreReturned() throws IOException {
        FileAdapter fileAdapter = new FileAdapter(getClass().getResourceAsStream("/com/baeldung/jcasbin/acl.csv"));

        String content = new String(getClass().getClassLoader().getResourceAsStream("com/baeldung/jcasbin/acl.conf").readAllBytes());
        Model model = new Model();
        model.loadModelFromText(content);

        Enforcer enforcer = new Enforcer(model, fileAdapter);

        assertTrue(enforcer.enforce("alice", "data1", "read"));
        assertTrue(enforcer.enforce("bob", "data2", "write"));

        assertFalse(enforcer.enforce("alice", "data2", "write"));
    }

    @Test
    void givenAclSuperuserConfiguration_whenCheckingPermissions_thenTheCorrectResultsAreReturned() throws IOException {
        FileAdapter fileAdapter = new FileAdapter(getClass().getResourceAsStream("/com/baeldung/jcasbin/acl.csv"));

        String content = new String(getClass().getClassLoader().getResourceAsStream("com/baeldung/jcasbin/acl_superuser.conf").readAllBytes());
        Model model = new Model();
        model.loadModelFromText(content);

        Enforcer enforcer = new Enforcer(model, fileAdapter);

        assertTrue(enforcer.enforce("alice", "data1", "read"));
        assertTrue(enforcer.enforce("bob", "data2", "write"));

        assertTrue(enforcer.enforce("root", "data2", "write"));
    }


    @Test
    void givenRbacConfiguration_whenCheckingPermissions_thenTheCorrectResultsAreReturned() throws IOException {
        FileAdapter fileAdapter = new FileAdapter(getClass().getResourceAsStream("/com/baeldung/jcasbin/rbac.csv"));

        String content = new String(getClass().getClassLoader().getResourceAsStream("com/baeldung/jcasbin/rbac.conf").readAllBytes());
        Model model = new Model();
        model.loadModelFromText(content);

        Enforcer enforcer = new Enforcer(model, fileAdapter);

        assertTrue(enforcer.enforce("alice", "data1", "read"));
        assertTrue(enforcer.enforce("bob", "data2", "write"));

        assertTrue(enforcer.enforce("carol", "data2", "read"));
    }
}
