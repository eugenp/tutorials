package services;

import models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import services.implementation.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private UserService userService;

    @Before
    public void setUp() throws Exception {
        userService = new UserServiceImpl();
    }

    @Test
    public void saveUser() {
        User user = mockUser();
        userService.saveUser(user);

        assertEquals(userService.getUsers().get(3).getFirstName(), "Joy");
    }

    @Test
    public void getUsers() {
        User user = mockUser();
        userService.saveUser(user);

        assertEquals(userService.getUsers().get(3), user);
    }

    private User mockUser() {
        User user = new User();
        user.setId(3l);
        user.setFirstName("Joy");
        user.setLastName("Kwere");
        return user;
    }

}
