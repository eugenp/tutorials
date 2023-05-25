package com.baeldung.daopattern.test;

import com.baeldung.daopattern.daos.UserDao;
import com.baeldung.daopattern.entities.User;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserDaoUnitTest {
  
    private static UserDao userDao;
    
    
    @BeforeClass
    public static void setUpUserDaoInstance() {
        userDao = new UserDao();
    }
    
    @Test
    public void givenUserDaoInstance_whenCalledget_thenOneAssertion() {
        assertThat(userDao.get(0)).isInstanceOf(Optional.class);
    }
    
    @Test
    public void givenUserDaoInstance_whenCalledgetAll_thenOneAssertion() {
        assertThat(userDao.getAll()).isInstanceOf(List.class);
    }
    
    @Test
    public void givenUserDaoInstance_whenCalledupdate_thenTwoAssertions() {
        User user = new User("Julie", "julie@domain.com");
        userDao.update(user, new String[] {"Julie", "julie@domain.com"});
        assertThat(userDao.get(2).get().getName()).isEqualTo("Julie");
        assertThat(userDao.get(2).get().getEmail()).isEqualTo("julie@domain.com");
    }
    
    @Test
    public void givenUserDaoInstance_whenCalledsave_thenTwoAssertions() {
        User user = new User("Julie", "julie@domain.com");
        userDao.save(user);
        assertThat(userDao.get(2).get().getName()).isEqualTo("Julie");
        assertThat(userDao.get(2).get().getEmail()).isEqualTo("julie@domain.com");
    }
    
    @Test
    public void givenUserDaoInstance_whenCalleddelete_thenOneAssertion() {
        User user = new User("Julie", "julie@domain.com");
        userDao.delete(user);
        assertThat(userDao.getAll().size()).isEqualTo(2);
    }
}
