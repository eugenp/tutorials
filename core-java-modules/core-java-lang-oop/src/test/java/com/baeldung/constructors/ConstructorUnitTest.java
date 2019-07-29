package com.baeldung.constructors;

import com.google.common.collect.Comparators;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.*;

public class ConstructorUnitTest {
    final static Logger LOGGER = Logger.getLogger(ConstructorUnitTest.class.getName());

    @Test
    public void givenNoExplicitContructor_whenUsed_thenFails() {
        BankAccount account = new BankAccount();
        assertThatThrownBy(() -> {
            account.toString();
        }).isInstanceOf(Exception.class);
    }

    @Test
    public void givenNoArgumentConstructor_whenUsed_thenSucceeds() {
        BankAccountEmptyConstructor account = new BankAccountEmptyConstructor();
        assertThatCode(() -> {
            account.toString();
        }).doesNotThrowAnyException();
    }

    @Test
    public void givenParameterisedConstructor_whenUsed_thenSucceeds() {
        LocalDateTime opened = LocalDateTime.of(2018, Month.JUNE, 29, 06, 30, 00);
        BankAccountParameterizedConstructor account =
                new BankAccountParameterizedConstructor("Tom", opened, 1000.0f);

        assertThatCode(() -> {
            account.toString();
        }).doesNotThrowAnyException();
    }

    @Test
    public void givenCopyContructor_whenUser_thenMaintainsLogic() {
        LocalDateTime opened = LocalDateTime.of(2018, Month.JUNE, 29, 06, 30, 00);
        BankAccountCopyConstructor account = new BankAccountCopyConstructor("Tim", opened, 1000.0f);
        BankAccountCopyConstructor newAccount = new BankAccountCopyConstructor(account);

        assertThat(account.getName()).isEqualTo(newAccount.getName());
        assertThat(account.getOpened()).isNotEqualTo(newAccount.getOpened());

        assertThat(newAccount.getBalance()).isEqualTo(0.0f);
    }

    @Test
    public void givenChainedConstructor_whenUsed_thenMaintainsLogic() {
        BankAccountChainedConstructors account = new BankAccountChainedConstructors("Tim");
        BankAccountChainedConstructors newAccount = new BankAccountChainedConstructors("Tim", LocalDateTime.now(), 0.0f);

        assertThat(account.getName()).isEqualTo(newAccount.getName());
        assertThat(account.getBalance()).isEqualTo(newAccount.getBalance());
    }
}
