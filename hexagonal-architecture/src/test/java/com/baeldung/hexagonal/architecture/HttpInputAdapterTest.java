package com.baeldung.hexagonal.architecture;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.baeldung.hexagonal.architecture.adapter.driving.HttpInputAdapter;
import com.baeldung.hexagonal.architecture.domain.dto.Account;
import com.baeldung.hexagonal.architecture.port.SignUpServiceInputPort;

@RunWith(SpringRunner.class)
@WebMvcTest(HttpInputAdapter.class)
@ComponentScan
class HttpInputAdapterTest {

    @Captor
    ArgumentCaptor<Account> accountCaptor;
    
    @MockBean
    SignUpServiceInputPort signupService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void givenSignupServiceMocked_whenSignup_thenPersistData() throws Exception {
        Mockito.when(signupService.signup(Mockito.any())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
            .get("/signup?firstName=John&lastName=Smith"));
        Mockito.verify(signupService).signup(accountCaptor.capture());
        Account account = accountCaptor.getValue();
        assertEquals("John",account.getFirstName());
        assertEquals("Smith",account.getLastName());
        
    }

}
