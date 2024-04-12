package com.baeldung.webflux.zipwhen;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.baeldung.webflux.zipwhen.model.User;
import com.baeldung.webflux.zipwhen.service.DatabaseService;
import com.baeldung.webflux.zipwhen.service.EmailService;
import com.baeldung.webflux.zipwhen.service.UserService;
import com.baeldung.webflux.zipwhen.web.UserController;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class UserControllerUnitTest {
    @Test
    public void givenUserId_whenCombineAllData_thenReturnsMonoWithCombinedData() {
        UserService userService = Mockito.mock(UserService.class);
        EmailService emailService = Mockito.mock(EmailService.class);
        DatabaseService databaseService = Mockito.mock(DatabaseService.class);

        String userId = "123";
        User user = new User(userId, "John Doe");

        Mockito.when(userService.getUser(userId))
          .thenReturn(Mono.just(user));
        Mockito.when(emailService.sendEmail(userId))
          .thenReturn(Mono.just(true));
        Mockito.when(databaseService.saveUserData(user))
          .thenReturn(Mono.just(true));

        UserController userController = new UserController(userService, emailService, databaseService);

        Mono<ResponseEntity<String>> responseMono = userController.combineAllDataFor(userId);

        StepVerifier.create(responseMono)
          .expectNextMatches(responseEntity -> responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody()
            .equals("Response: " + user + ", Email Sent: true, Database Result: " + true))
          .verifyComplete();
    }
}
