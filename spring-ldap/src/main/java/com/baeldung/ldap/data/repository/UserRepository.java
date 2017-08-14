package com.baeldung.ldap.data.repository;

import java.util.List;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends LdapRepository<User> {

    public User findByUsername(String username);

    public User findByUsernameAndPassword(String username, String password);

    public List<User> findByUsernameLikeIgnoreCase(String username);

}
