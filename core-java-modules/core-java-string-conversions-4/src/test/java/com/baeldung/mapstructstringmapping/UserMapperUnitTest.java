package com.baeldung.mapstructstringmapping;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

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
