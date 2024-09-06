package io.github.venkat1701.stringtodatemapstruct.mapper;

import io.github.venkat1701.stringtodatemapstruct.dto.UserDTO;
import io.github.venkat1701.stringtodatemapstruct.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class UserMapperTest {
    private final UserMapper mapper = UserMapper.INSTANCE;

    @Test
    public void testUserToUserDto() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfBirth = dateFormat.parse("2006-01-17");
        User user = new User();
        user.setDateOfBirth(dateOfBirth);

        UserDTO userDTO = mapper.userToUserDto(user);
        assertEquals("2006-01-17", userDTO.getDateOfBirth());
    }

    @Test
    public void testUserDtoToUser() throws ParseException {
        UserDTO userDTO = new UserDTO();
        userDTO.setDateOfBirth("2006-01-17");

        User user = mapper.userDtoToUser(userDTO);
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2006-01-17"), user.getDateOfBirth());
    }
}
