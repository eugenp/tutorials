package com.baeldung.userdao.test;

import com.baeldung.dao.Dao;
import com.baeldung.entity.User;
import com.baeldung.userdao.UserDao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;

public class UserDaoUnitTest {
    
    private Dao userDao;
    
    @Before
    public void setUpUserDaoInstance() {
        Map<Integer, User> users = new HashMap<>();
        users.put(1, new User("Julie"));
        users.put(2, new User("David"));
        userDao = new UserDao(users);
    }
    
    @Test
    public void givenUserDaoIntance_whenCalledFindById_thenCorrect() {
       assertThat(userDao.findById(1), isA(Optional.class));
    } 
    
    @Test
    public void givenUserDaoIntance_whenCalledFindAll_thenCorrect() {
       assertThat(userDao.findAll(), isA(List.class));
    } 
}
