package com.baeldung.designpatterns.dtopattern.domain;

public interface RoleRepository {
    Role getRoleById(String id);
    Role getRoleByName(String name);
    void save(Role role);
}
