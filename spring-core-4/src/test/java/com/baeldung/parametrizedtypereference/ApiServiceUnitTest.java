package com.baeldung.parametrizedtypereference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiServiceUnitTest {

    @Mock
    private RestTemplate restTemplate;

    private ApiService apiService;

    @BeforeEach
    void setUp() {
        apiService = new ApiService(restTemplate);
    }

    @Test
    void whenFetchingUserList_thenReturnsCorrectType() {
        // given
        List<User> expectedUsers = Arrays.asList(
                new User(1L, "John Doe", "john@example.com", "Engineering"),
                new User(2L, "Jane Smith", "jane@example.com", "Marketing")
        );
        ResponseEntity<List<User>> responseEntity = new ResponseEntity<>(expectedUsers, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("/api/users"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(responseEntity);

        // when
        List<User> result = apiService.fetchUserList();

        // then
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("jane@example.com", result.get(1).getEmail());
    }

    @Test
    void whenCreatingUsers_thenReturnsCreatedUsers() {
        // given
        List<User> inputUsers = Arrays.asList(
                new User(null, "John Doe", "john@example.com", "Engineering"),
                new User(null, "Jane Smith", "jane@example.com", "Marketing")
        );

        List<User> expectedUsers = Arrays.asList(
                new User(1L, "John Doe", "john@example.com", "Engineering"),
                new User(2L, "Jane Smith", "jane@example.com", "Marketing")
        );

        ResponseEntity<List<User>> responseEntity =
                new ResponseEntity<>(expectedUsers, HttpStatus.CREATED);

        when(restTemplate.exchange(
                eq("/api/users/batch"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)
        )).thenReturn(responseEntity);

        // when
        List<User> result = apiService.createUsers(inputUsers);

        // then
        assertNotNull(result.get(0).getId());
        assertNotNull(result.get(1).getId());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Smith", result.get(1).getName());
    }

    @Test
    void whenFetchingUsersCorrectApproach_thenReturnsTypedList() {
        // given
        List<User> expectedUsers = Arrays.asList(
                new User(1L, "John Doe", "john@example.com", "Engineering")
        );
        ResponseEntity<List<User>> responseEntity = new ResponseEntity<>(expectedUsers, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("/api/users"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(responseEntity);

        // when
        List<User> result = apiService.fetchUsersCorrectApproach();

        // then
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Engineering", result.get(0).getDepartment());
    }

    @Test
    void whenFetchingSingleUser_thenReturnsUser() {
        // given
        User expectedUser = new User(1L, "John Doe", "john@example.com", "Engineering");

        when(restTemplate.getForObject("/api/users/1", User.class))
                .thenReturn(expectedUser);

        // when
        User result = apiService.fetchUser(1L);

        // then
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    void whenFetchingUsersArray_thenReturnsArray() {
        // given
        User[] expectedUsers = {
                new User(1L, "John Doe", "john@example.com", "Engineering"),
                new User(2L, "Jane Smith", "jane@example.com", "Marketing")
        };

        when(restTemplate.getForObject("/api/users", User[].class))
                .thenReturn(expectedUsers);

        // when
        User[] result = apiService.fetchUsersArray();

        // then
        assertEquals(2, result.length);
        assertEquals("John Doe", result[0].getName());
        assertEquals("Jane Smith", result[1].getName());
    }
}
