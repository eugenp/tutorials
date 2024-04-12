package com.baeldung.dtopattern.domain;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
class InMemoryRepository implements UserRepository, RoleRepository {

    private Map<String, User> users = new LinkedHashMap<>();
    private Map<String, Role> roles = new LinkedHashMap<>();

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void save(User user) {
        user.setId(UUID.randomUUID().toString());
        users.put(user.getId(), user);
    }

    @Override
    public void save(Role role) {
        role.setId(UUID.randomUUID().toString());
        roles.put(role.getId(), role);
    }

    @Override
    public Role getRoleById(String id) {
        return roles.get(id);
    }

    @Override
    public Role getRoleByName(String name) {
        return roles.values()
            .stream()
            .filter(role -> role.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElse(null);
    }

    @Override
    public void deleteAll() {
        users.clear();
        roles.clear();
    }
}
