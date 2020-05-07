package com.baeldung.hexagonal.banking.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.hexagonal.banking.domain.StubAccountFactory;
import com.baeldung.hexagonal.banking.output.port.AccountStatePort;

public class GetAccountBalanceServiceUnitTest {
    
    private AccountStatePort mockAccountStatePort;
    private GetAccountBalanceService target;
    
    @BeforeEach
    void setUp() {
        mockAccountStatePort = mock(AccountStatePort.class);
        target = new GetAccountBalanceService(mockAccountStatePort);
    }
    
    @Test
    void shouldLoadAccount_thenReturnCurrentBalance() {
        
        when(mockAccountStatePort.loadAccount(1L)).thenReturn(Optional.of(StubAccountFactory.stubDefaultAccount()));
        
        BigDecimal actual = target.getBalance(1L);
        verify(mockAccountStatePort).loadAccount(1L);
        assertThat(actual.compareTo(BigDecimal.ONE)).isEqualTo(0);
    }
    

}
