package com.baeldung.constructor;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

class UserServiceUnitTest {

    @Test
    void whenConstructorInvokedWithInitializer_ThenMockObjectShouldBeCreated(){
        try(MockedConstruction<UserService> mockUserService = Mockito.mockConstruction(UserService.class,(mock,context)-> when(mock.getUserName()).thenReturn("John Doe"))){
            User user = new User();
            Assertions.assertEquals(1,mockUserService.constructed().size());
            Assertions.assertEquals("John Doe",user.getUserName());
        }
    }

    @Test
    void whenConstructorInvokedWithoutInitializer_ThenMockObjectShouldBeCreatedWithNullFields(){
        try(MockedConstruction<UserService> mockUserService = Mockito.mockConstruction(UserService.class)){
            User user = new User();
            Assertions.assertEquals(1,mockUserService.constructed().size());
            Assertions.assertNull(user.getUserName());
        }
    }

    @Test
    void whenConstructorInvokedWithParameters_ThenMockObjectShouldBeCreated(){
        try(MockedConstruction<UserService> mockUserService = Mockito.mockConstruction(UserService.class,(mock, context) -> when(mock.getUserName()).thenReturn("John Doe"))){
            User user = new User("Mike");
            Assertions.assertEquals(1,mockUserService.constructed().size());
            Assertions.assertEquals("John Doe",user.getUserName());
        }
    }

    @Test
    void whenMultipleConstructorsInvoked_ThenMultipleMockObjectsShouldBeCreated(){
        try(MockedConstruction<UserService> mockUserService = Mockito.mockConstruction(UserService.class)){
            User user = new User();
            User secondUser = new User();
            User thirdUser = new User("Mike");

            when(mockUserService.constructed().get(0).getUserName()).thenReturn("John Doe");
            when(mockUserService.constructed().get(1).getUserName()).thenReturn("Steve Smith");

            Assertions.assertEquals(3,mockUserService.constructed().size());
            Assertions.assertEquals("John Doe",user.getUserName());
            Assertions.assertEquals("Steve Smith",secondUser.getUserName());
            Assertions.assertNull(thirdUser.getUserName());
        }
    }
}
