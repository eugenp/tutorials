package com.baeldung.mockinglogger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class UserServiceUnitTest {

    private Logger mockLogger;
    private UserService userService;

    @BeforeEach
    public void setup() {
        mockLogger = mock(Logger.class);
        userService = new UserService(mockLogger);
    }

    @Test
    void givenAUserServiceLogic_whenVerifyingIfUserIsNotAnAdmin_thenReturnCorrectLog() {

        try (MockedStatic<LoggerFactory> mockedFactory = mockStatic(LoggerFactory.class)) {
            mockedFactory.when(() -> LoggerFactory.getLogger(UserService.class))
                .thenReturn(mockLogger);
            userService.checkAdminStatus(false);

            verify(mockLogger).error("You are not an admin");

        }
    }

    @Test
    void givenAUserServiceLogic_whenVerifyingIfUserIsAnAdmin_thenReturnCorrectLog() {

        try (MockedStatic<LoggerFactory> mockedFactory = mockStatic(LoggerFactory.class)) {
            mockedFactory.when(() -> LoggerFactory.getLogger(UserService.class))
                .thenReturn(mockLogger);
            userService.checkAdminStatus(true);

            verify(mockLogger).info("You are an admin, access granted");
        }
    }

}