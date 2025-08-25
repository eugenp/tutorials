package com.baeldung.dataloaderbatchprocessing;

import com.baeldung.dataloaderbatchprocessing.entity.User;
import com.baeldung.dataloaderbatchprocessing.repository.UserRepository;
import com.baeldung.dataloaderbatchprocessing.service.UserService;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = DataLoaderApp.class)
class UserDataLoaderIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @SpyBean
    private UserService userService;

    private DataLoader<String, User> userDataLoader;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        User user1 = new User("101", "User_101");
        User user2 = new User("102", "User_102");
        User user3 = new User("103", "User_103");
        userRepository.saveAll(Arrays.asList(user1, user2, user3));

        userDataLoader = new DataLoader<>(userService::getUsersByIds);
        DataLoaderRegistry registry = new DataLoaderRegistry();
        registry.register("userDataLoader", userDataLoader);
    }

    @Test
    void whenLoadingUsers_thenBatchLoaderIsInvokedAndResultsReturned() {
        CompletableFuture<User> userFuture1 = userDataLoader.load("101");
        CompletableFuture<User> userFuture2 = userDataLoader.load("102");
        CompletableFuture<User> userFuture3 = userDataLoader.load("103");

        userDataLoader.dispatchAndJoin();

        verify(userService, times(1)).getUsersByIds(anyList());

        assertThat(userFuture1.join().getName()).isEqualTo("User_101");
        assertThat(userFuture2.join().getName()).isEqualTo("User_102");
        assertThat(userFuture3.join().getName()).isEqualTo("User_103");
    }
}

