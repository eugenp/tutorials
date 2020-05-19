package com.baeldung.jmapper;

import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.JMapperAPI;
import org.junit.Test;

import java.time.LocalDate;

import static com.googlecode.jmapper.api.JMapperAPI.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JMapperIntegrationTest {

    @Test
    public void givenUser_whenUseAnnotation_thenConverted() {
        JMapper<UserDto, User> userMapper = new JMapper<>(UserDto.class, User.class);

        User user = new User(1L, "john@test.com", LocalDate.of(1980, 8, 20));
        UserDto result = userMapper.getDestination(user);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getUsername());
    }

    @Test
    public void givenUser_whenUseGlobalMapAnnotation_thenConverted() {
        JMapper<UserDto1, User> userMapper = new JMapper<>(UserDto1.class, User.class);

        User user = new User(1L, "john@test.com", LocalDate.of(1980, 8, 20));
        UserDto1 result = userMapper.getDestination(user);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    public void givenUser_whenUseAnnotationExplicitConversion_thenConverted() {
        JMapper<UserDto, User> userMapper = new JMapper<>(UserDto.class, User.class);

        User user = new User(1L, "john@test.com", LocalDate.of(1980, 8, 20));
        UserDto result = userMapper.getDestination(user);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getUsername());
        assertTrue(result.getAge() > 0);
    }

    // ======================= XML

    @Test
    public void givenUser_whenUseXml_thenConverted() {
        JMapper<UserDto, User> userMapper = new JMapper<>(UserDto.class, User.class, "user_jmapper.xml");

        User user = new User(1L, "john@test.com", LocalDate.of(1980, 8, 20));
        UserDto result = userMapper.getDestination(user);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getUsername());
    }

    @Test
    public void givenUser_whenUseXmlGlobal_thenConverted() {
        JMapper<UserDto1, User> userMapper = new JMapper<>(UserDto1.class, User.class, "user_jmapper1.xml");

        User user = new User(1L, "john@test.com", LocalDate.of(1980, 8, 20));
        UserDto1 result = userMapper.getDestination(user);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getEmail());
    }

    // ===== API

    @Test
    public void givenUser_whenUseApi_thenConverted() {
        JMapperAPI jmapperApi = new JMapperAPI().add(mappedClass(UserDto.class).add(attribute("id").value("id")).add(attribute("username").value("email")));
        JMapper<UserDto, User> userMapper = new JMapper<>(UserDto.class, User.class, jmapperApi);

        User user = new User(1L, "john@test.com", LocalDate.of(1980, 8, 20));
        UserDto result = userMapper.getDestination(user);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getUsername());
    }

    @Test
    public void givenUser_whenUseApiGlobal_thenConverted() {
        JMapperAPI jmapperApi = new JMapperAPI().add(mappedClass(UserDto.class).add(global()));
        JMapper<UserDto1, User> userMapper1 = new JMapper<>(UserDto1.class, User.class, jmapperApi);

        User user = new User(1L, "john@test.com", LocalDate.of(1980, 8, 20));
        UserDto1 result = userMapper1.getDestination(user);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getEmail());
    }

}
