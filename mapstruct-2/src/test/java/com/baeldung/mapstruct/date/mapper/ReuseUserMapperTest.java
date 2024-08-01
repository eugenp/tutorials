package com.baeldung.mapstruct.date.mapper;

import com.baeldung.mapstruct.date.model.User;
import com.baeldung.mapstruct.date.model.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReuseUserMapperTest {

    private final ReuseUserMapper userMapper = Mappers.getMapper(ReuseUserMapper.class);

    @Test
    public void whenMappingUserDTOToUser_thenMapsNameCorrectly() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setBirthDate("2024-08-01");

        User user = userMapper.toUser(userDTO);

        assertNotNull(user);
        Assertions.assertEquals("John Doe", user.getName());
    }

    @Test
    public void whenMappingUserDTOToUser_thenMapsBirthDateCorrectly() throws ParseException {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setBirthDate("2024-08-01");

        User user = userMapper.toUser(userDTO);

        assertNotNull(user);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expectedDate = dateFormat.parse("2024-08-01");
        Assertions.assertEquals(expectedDate, user.getBirthDate());
    }

    @Test
    public void whenMappingInvalidDateString_thenThrowsException() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setBirthDate("invalid-date");

        assertThrows(RuntimeException.class, () -> {
            userMapper.toUser(userDTO);
        });
    }
}