package com.baeldung.hexagonal.banking.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.baeldung.hexagonal.banking.domain.Account;
import com.baeldung.hexagonal.banking.domain.AccountHolder;
import com.baeldung.hexagonal.banking.domain.StubAccountFactory;
import com.baeldung.hexagonal.banking.input.port.OpenAccountCommand;
import com.baeldung.hexagonal.banking.output.port.AccountStatePort;

public class OpenAccountServiceUnitTest {
    
    private OpenAccountService target;
    private AccountStatePort mockCreateAccountPort;
    
    @BeforeEach
    void setUp() {
        mockCreateAccountPort = mock(AccountStatePort.class);
        target = new OpenAccountService(mockCreateAccountPort);
    }
    
    @ParameterizedTest
    @MethodSource("givenValidData")
    public void openAccounts_whenDataIsValid(AccountHolder givenAccountHolder, int givenPin, BigDecimal givenAmount, boolean expectedResult) {

       Account stubAccount = StubAccountFactory.stubDefaultAccount();
       when(mockCreateAccountPort.createOrUpdateAccount(any(Account.class))).thenReturn(stubAccount);
       OpenAccountCommand givenOpenAccountCommand =  new OpenAccountCommand(givenAccountHolder, givenPin, givenAmount); 
       
       Long actualAccountNumber = target.openAccount(givenOpenAccountCommand);
       
       verify(mockCreateAccountPort).createOrUpdateAccount(any(Account.class));
       assertThat(actualAccountNumber).isNotNull();
       assertThat(actualAccountNumber).isEqualTo(stubAccount.getAccountNumber());
       
      

    }
    
    @ParameterizedTest
    @MethodSource("givenInvalidData")
    public void notOpenAccount_whenPinOrInitialDepositAmountIsInvalid(AccountHolder givenAccountHolder, int givenPin, BigDecimal givenAmount, boolean expectedResult) {

       OpenAccountCommand givenOpenAccountCommand =  new OpenAccountCommand(givenAccountHolder, givenPin, givenAmount);
       assertThrows(InvalidDataException.class, () -> {target.openAccount(givenOpenAccountCommand);
       });

    }
    
    
    private static Stream<Arguments> givenValidData() {
        return Stream.of(
            Arguments.of(stubDefaultConsumer(), 1234, BigDecimal.TEN, true));
            
    }
    
    private static Stream<Arguments> givenInvalidData() {
        return Stream.of(Arguments.of(stubDefaultConsumer(), 0, BigDecimal.ZERO, false),
            Arguments.of(stubDefaultConsumer(), 1234, BigDecimal.TEN.negate(), false));
            
    }
    
    
    private static AccountHolder stubDefaultConsumer() {        
        return new AccountHolder(7654, "Stubby", "Testbar");
    }
}
