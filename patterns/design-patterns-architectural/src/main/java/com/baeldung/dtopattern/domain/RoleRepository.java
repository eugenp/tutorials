package com.baeldung.dtopattern.domain;

public interface RoleRepository {
    Role getRoleById(String id);
    Role getRoleByName(String name);
    void save(Role role);
}
