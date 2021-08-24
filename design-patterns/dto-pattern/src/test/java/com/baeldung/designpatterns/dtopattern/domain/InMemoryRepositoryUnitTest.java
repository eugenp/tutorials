package com.baeldung.designpatterns.dtopattern.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryRepositoryUnitTest {

    @Test
    void getAll_shouldReturnAllUsers() {

        String name = "Test";
        String password = "test123";
        List<Role> roles = new ArrayList<>();

        User user = new User(name, password, roles);
        List<User> expectedUsers = Collections.singletonList(user);

        InMemoryRepository repository = new InMemoryRepository();
        repository.save(user);

        List<User> users = repository.getAll();

        assertEquals(expectedUsers, users);
    }

    @Test
    void save_whenSavingUser_shouldSetId() {
        String name = "Test";
        String password = "test123";
        List<Role> roles = new ArrayList<>();

        User user = new User(name, password, roles);

        InMemoryRepository repository = new InMemoryRepository();
        repository.save(user);

        assertNotNull(user.getId());
    }

    @Test
    void save_whenSavingRole_shouldSetId() {
        String name = "Test";
        Role role = new Role(name);

        InMemoryRepository repository = new InMemoryRepository();
        repository.save(role);

        assertNotNull(role.getId());
    }

    @Test
    void getRoleById_shouldReturnRoleById() {
        String name = "Test";
        Role expectedRole = new Role(name);

        InMemoryRepository repository = new InMemoryRepository();
        repository.save(expectedRole);

        Role role = repository.getRoleById(expectedRole.getId());

        assertEquals(expectedRole, role);
    }

    @Test
    void getRoleByName_shouldReturnRoleByName() {
        String name = "Test";
        Role expectedRole = new Role(name);

        InMemoryRepository repository = new InMemoryRepository();
        repository.save(expectedRole);

        Role role = repository.getRoleByName(name);

        assertEquals(expectedRole, role);
    }

}