package com.baeldung.hexagonal.test;

import com.baeldung.hexagonal.adapter.repository.InMemoryUserRepositoryImpl;
import com.baeldung.hexagonal.domain.MemeberStatus;
import com.baeldung.hexagonal.domain.User;
import com.baeldung.hexagonal.service.UserServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceUnitTest {

    private static User user;

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    private InMemoryUserRepositoryImpl repo;

    @BeforeClass
    public static void setUpUserInstance() {

        user = new User();
        user.setUserId(1);
        user.setFirstName("first");
        user.setLastName("last");
        user.setStatus(MemeberStatus.GOLD);
        user.setRewardedpoints(100);

    }

    @Test
    public void createUser() {
        when(repo.CreateUser(user)).thenReturn(user);
        User repoUser = userService.registerUser(user);
        assertEquals(repoUser.getUserId(), user.getUserId());
    }

   @Test
    public void getUser() {
        when(repo.findAll()).thenReturn(Arrays.asList(user));

        List<User> userList = userService.getAllUsers();
        assertEquals(userList.get(0).getUserId(), user.getUserId());

    }

}
