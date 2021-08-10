package com.baeldung.pattern.hexagonal.domain;

import com.baeldung.pattern.hexagonal.domain.model.User;
import com.baeldung.pattern.hexagonal.domain.service.UserServiceImpl;
import com.baeldung.pattern.hexagonal.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private static final String email = "email@gmail.com";

    private static final User user = User.builder()
        .email(email)
        .phone("+39333222111")
        .build();

    @Test
    public void whenSaveUser_thenRepositoryWillSave() {

        userService.save(user);

        verify(userRepository).save(user);
    }

    @Test
    public void whenGetByEmailAndUserExists_thenUserIsFoundInRepository() {

        when(userRepository.getByEmail(email)).thenReturn(user);

        User userByEmail = userService.getByEmail(email);

        assertEquals(userByEmail, user);

        verify(userRepository).getByEmail(email);
    }
}
