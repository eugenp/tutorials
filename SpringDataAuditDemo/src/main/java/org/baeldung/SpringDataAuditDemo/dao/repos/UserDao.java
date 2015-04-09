package org.baeldung.SpringDataAuditDemo.dao.repos;

import org.baeldung.SpringDataAuditDemo.dao.GenericDao;
import org.baeldung.SpringDataAuditDemo.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Represents dao interface for {@link org.baeldung.SpringDataAuditDemo.model.User} entity. 
 */
public interface UserDao extends GenericDao<User, Long>, UserDetailsService {

    // @Override
    // This method can load user from db but only by username.
    // After this method returns the password will be checked in spring security AbstractUserDetailsAuthenticationProvider.authenticate method.
    // So, username should be unique in the db, otherwise this method always return first found record.
    @Query(value = "select u from User u where u.username=:username")
    public UserDetails loadUserByUsername(@Param("username") String username) throws UsernameNotFoundException;
}
