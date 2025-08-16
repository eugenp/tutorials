package com.baeldung.dataloaderbatchprocessing;

import com.baeldung.dataloaderbatchprocessing.entity.User;
import com.baeldung.dataloaderbatchprocessing.service.UserService;

import org.dataloader.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = DataLoaderApp.class)
public class DataLoaderUnitTest {

    private UserService userService;
    private UserDataLoader loaderFactory;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        loaderFactory = new UserDataLoader(userService);
    }

    @Test
    void givenThreeUsers_whenUsingDataLoader_thenLoadUsersInSingleDBCall() throws Exception {
        User user1 = new User("101", "User_101");
        User user2 = new User("102", "User_102");
        User user3 = new User("103", "User_103");

        List<User> users = Arrays.asList(user1, user2, user3);

        when(userService.getUsersByIds(Arrays.asList("101", "102", "103"))).thenReturn(CompletableFuture.completedFuture(users));

        DataLoader<String, User> userLoader = loaderFactory.createUserLoader();

        CompletableFuture<User> future1 = userLoader.load("101");
        CompletableFuture<User> future2 = userLoader.load("102");
        CompletableFuture<User> future3 = userLoader.load("103");

        userLoader.dispatch();

        assertEquals("User_101", future1.get()
            .getName());
        assertEquals("User_102", future2.get()
            .getName());
        assertEquals("User_103", future3.get()
            .getName());

        verify(userService, times(1)).getUsersByIds(Arrays.asList("101", "102", "103"));
    }
}
