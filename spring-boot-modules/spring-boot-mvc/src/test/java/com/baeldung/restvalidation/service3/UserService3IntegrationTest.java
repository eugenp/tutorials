package com.baeldung.restvalidation.service3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.restvalidation.RestValidationApplication;
import com.baeldung.restvalidation.response.InputFieldError;
import com.baeldung.restvalidation.response.UpdateUserResponse;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RestValidationApplication.class)
class UserService3IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenUpdateValidEmail_thenReturnsOK() {

        // When
        ResponseEntity<UpdateUserResponse> responseEntity = updateUser(new User("test@email.com"), null);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void whenUpdateEmptyEmail_thenReturnsErrorMessageInEnglish() {

        // When
        ResponseEntity<UpdateUserResponse> responseEntity = updateUser(new User(""), null);

        // Then
        InputFieldError error = responseEntity.getBody().getFieldErrors().get(0);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Personal Email cannot be empty", error.getMessage());
    }

    @Test
    void whenUpdateEmptyEmailWithLanguageHeaderEqualsToZh_thenReturnsErrorMessageInChinese() {

        // When
        ResponseEntity<UpdateUserResponse> responseEntity = updateUser(new User(""), "zh-tw");

        // Then
        InputFieldError error = responseEntity.getBody().getFieldErrors().get(0);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("個人電郵不能是空白", error.getMessage());
    }

    private ResponseEntity<UpdateUserResponse> updateUser(User user, String language) {

        HttpHeaders headers = new HttpHeaders();
        if (Objects.nonNull(language)) {
            headers.set(HttpHeaders.ACCEPT_LANGUAGE, language);
        }

        return restTemplate.exchange(
            "/user3",
            HttpMethod.PUT,
            new HttpEntity<>(user, headers),
            UpdateUserResponse.class
        );
    }

}