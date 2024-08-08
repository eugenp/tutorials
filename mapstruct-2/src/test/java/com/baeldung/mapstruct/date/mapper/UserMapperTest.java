package com.baeldung.mapstruct.date.mapper;

import com.baeldung.mapstruct.date.model.User;
import com.baeldung.mapstruct.date.model.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void whenMappingUserDtoToUser_thenMapsNameCorrectly() {
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setBirthDate("2024-08-01");

        User user = userMapper.toUser(userDto);

        assertNotNull(user);
        Assertions.assertEquals("John Doe", user.getName());
    }

    @Test
    public void whenCustomMappingUserDtoToUser_thenMapsNameCorrectly() {
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setBirthDate("2024-08-01");

        User user = userMapper.toUser(userDto);

        assertNotNull(user);
        Assertions.assertEquals("John Doe", user.getName());
    }

    @Test
    public void whenMappingUserDtoToUser_thenMapsBirthDateCorrectly() throws ParseException {
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setBirthDate("2024-08-01");

        User user = userMapper.toUser(userDto);

        assertNotNull(user);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expectedDate = dateFormat.parse("2024-08-01");
        Assertions.assertEquals(expectedDate, user.getBirthDate());
    }

    @Test
    public void whenCustomMappingUserDtoToUser_thenMapsBirthDateCorrectly() throws ParseException {
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setBirthDate("2024-08-01");

        User user = userMapper.toUserCustom(userDto);

        assertNotNull(user);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expectedDate = dateFormat.parse("2024-08-01");
        Assertions.assertEquals(expectedDate, user.getBirthDate());
    }

    @Test
    public void whenMappingInvalidDateString_thenThrowsException() {
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setBirthDate("invalid-date");

        assertThrows(RuntimeException.class, () -> {
            userMapper.toUser(userDto);
        });
    }

    @Test
    public void whenCustomMappingInvalidDateString_thenThrowsException() {
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setBirthDate("invalid-date");

        assertThrows(ParseException.class, () -> {
            userMapper.toUserCustom(userDto);
        });
    }
}