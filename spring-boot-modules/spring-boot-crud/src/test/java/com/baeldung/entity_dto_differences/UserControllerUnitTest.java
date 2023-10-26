package com.baeldung.entity_dto_differences;

import com.baeldung.entity_dto_differences.controller.UserController;
import com.baeldung.entity_dto_differences.dto.UserDto;
import com.baeldung.entity_dto_differences.entity.User;
import com.baeldung.entity_dto_differences.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerUnitTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUsers_thenSuccess() {

        User entity = new User(new Random().nextLong(), "John", "Doe", "Dummy address", Collections.singletonList(new User.Book("Dummy name", "Dummy author")));
        Mockito.when(userRepository.findAll()).thenReturn(Collections.singletonList(entity));

        List<UserDto> userDtoList = userController.getUsers();

        assertEquals(1, userDtoList.size());
        UserDto userDto = userDtoList.get(0);
        assertEquals(entity.getId(), userDto.getId());
        assertEquals(entity.getFirstName(), userDto.getFirstName());
        assertEquals(entity.getLastName(), userDto.getLastName());
        assertEquals(entity.getBooks().size(), userDto.getBooks().size());
    }
}
