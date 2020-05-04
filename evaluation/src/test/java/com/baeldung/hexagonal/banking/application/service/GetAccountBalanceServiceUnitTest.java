package com.baeldung.hexagonal.banking.application.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.hexagonal.banking.domain.StubAccountFactory;
import com.baeldung.hexagonal.banking.output.port.LoadAccountPort;

public class GetAccountBalanceServiceUnitTest {
    
    private LoadAccountPort mockLoadAccountPort;
    private GetAccountBalanceService target;
    
    @BeforeEach
    void setUp() {
        mockLoadAccountPort = mock(LoadAccountPort.class);
        target = new GetAccountBalanceService(mockLoadAccountPort);
    }
    
    @Test
    void shouldLoadAccount_thenReturnCurrentBalance() {
        
        when(mockLoadAccountPort.loadAccount(1L)).thenReturn(Optional.of(StubAccountFactory.stubDefaultAccount()));
        
        BigDecimal actual = target.getBalance(1L);
        verify(mockLoadAccountPort).loadAccount(1L);
        assertThat(actual.compareTo(BigDecimal.ONE)).isEqualTo(0);
    }
    

}
