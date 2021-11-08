package com.baeldung.hexagonal.architecture.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baeldung.hexagonal.architecture.domain.dto.Account;
import com.baeldung.hexagonal.architecture.port.Persistor;
@ExtendWith(MockitoExtension.class)
class SignupServiceTest {
    
    @InjectMocks
    SignupService signupService;
    
    @Captor
    ArgumentCaptor<Account> accountCaptor;
    
    @Mock
    Persistor persistor;

    @Test
    void givenPersitorIsMocked_whenSigningUp_thenPersistedDataInUpperCase() {
        Mockito.when(persistor.persist(Mockito.any())).thenReturn(true);
        Account account = new Account();
        account.setFirstName("john");
        account.setLastName("smith");
        signupService.signup(account );
        Mockito.verify(persistor).persist(accountCaptor.capture());
        Account persistedAccount = accountCaptor.getValue();
        assertEquals("JOHN",persistedAccount.getFirstName());
        assertEquals("SMITH",persistedAccount.getLastName());
    }

}
