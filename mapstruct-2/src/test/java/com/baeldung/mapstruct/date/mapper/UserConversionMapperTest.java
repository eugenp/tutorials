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

public class UserConversionMapperTest {

    private final UserConversionMapper userConversionMapper = Mappers.getMapper(UserConversionMapper.class);

    @Test
    public void whenMappingUserDtoToUser_thenMapsNameCorrectly() {
        UserDto userDTO = new UserDto();
        userDTO.setName("John Doe");
        userDTO.setBirthDate("2024-08-01");

        User user = userConversionMapper.toUser(userDTO);

        assertNotNull(user);
        Assertions.assertEquals("John Doe", user.getName());
    }

    @Test
    public void whenMappingUserDtoToUser_thenMapsBirthDateCorrectly() throws ParseException {
        UserDto userDTO = new UserDto();
        userDTO.setName("John Doe");
        userDTO.setBirthDate("2024-08-01");

        User user = userConversionMapper.toUser(userDTO);

        assertNotNull(user);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expectedDate = dateFormat.parse("2024-08-01");
        Assertions.assertEquals(expectedDate, user.getBirthDate());
    }

    @Test
    public void whenMappingInvalidDateString_thenThrowsException() {
        UserDto userDTO = new UserDto();
        userDTO.setName("John Doe");
        userDTO.setBirthDate("invalid-date");

        assertThrows(RuntimeException.class, () -> {
            userConversionMapper.toUser(userDTO);
        });
    }
}