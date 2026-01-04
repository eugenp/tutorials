package com.baeldung.mapstructstringmapping;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperUnitTest {

    @Test
    void shouldMapEnumToString() {
        User user = new User();
        user.setUsername("Kevin");
        user.setStatus(Status.ACTIVE);

        UserDTO dto = UserMapper.INSTANCE.toDto(user);

        assertEquals("Kevin", dto.getUsername());
        assertEquals("ACTIVE", dto.getStatus());
    }
}
