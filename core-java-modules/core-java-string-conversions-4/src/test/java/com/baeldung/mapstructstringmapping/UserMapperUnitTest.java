package com.baeldung.mapstructstringmapping;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserMapperUnitTest {

    @Test
    void givenUserWithStatus_whenMapsToUserDTO_thenEnumIsConvertedToString() {

        User user = new User();
        user.setUsername("Kevin");
        user.setStatus(Status.ACTIVE);

        UserDTO dto = UserMapper.INSTANCE.toDto(user);

        assertEquals("Kevin", dto.getUsername());
        assertEquals("ACTIVE", dto.getStatus());
    }
}
