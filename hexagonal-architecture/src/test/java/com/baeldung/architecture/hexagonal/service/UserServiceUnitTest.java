package com.baeldung.architecture.hexagonal.service;

import com.baeldung.architecture.hexagonal.db.entity.PostEntity;
import com.baeldung.architecture.hexagonal.db.entity.UserEntity;
import com.baeldung.architecture.hexagonal.domain.Post;
import com.baeldung.architecture.hexagonal.domain.PostOutDTO;
import com.baeldung.architecture.hexagonal.domain.User;
import com.baeldung.architecture.hexagonal.domain.UserOutDTO;
import com.baeldung.architecture.hexagonal.port.outbound.PostRepository;
import com.baeldung.architecture.hexagonal.port.outbound.UserRepository;
import com.baeldung.architecture.hexagonal.usecase.business.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userDAO;

    @Mock
    private PostRepository postDAO;

    @Test
    public void whenCreatingPost_postMustNotBeNull() {
        final PostEntity post = getMockedPostEntity();
        final UserEntity user = mockUserEntity();
        Mockito.when(userDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
        Mockito.when(postDAO.save(Mockito.any(PostEntity.class))).thenReturn(post);
        final PostOutDTO postOutDto = userService.createPost(1, new Post());
        Assertions.assertNotNull(postOutDto);
    }

    @Test
    public void whenCreatingUser_userMustNotBeNull() {
        final UserEntity user = mockUserEntity();
        final User userInDto = new User();
        userInDto.setName("test-name");
        Mockito.when(userDAO.save(Mockito.any(UserEntity.class))).thenReturn(user);
        final UserOutDTO userOut = userService.createUser(userInDto);
        Assertions.assertNotNull(userOut);
    }

    private PostEntity getMockedPostEntity() {
        final PostEntity mockedPostEntity = new PostEntity();
        final UserEntity user = mockUserEntity();
        mockedPostEntity.setTimeStamp(LocalDateTime.MAX);
        mockedPostEntity.setUser(user);
        mockedPostEntity.setContents("test");
        return mockedPostEntity;
    }

    private UserEntity mockUserEntity() {
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setName("test-name");
        return userEntity;
    }
}
