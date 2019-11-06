package com.baeldung.lombok.accessors;

import com.baeldung.lombok.accessors.model.*;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class AccessorsUnitTest {

    @Test
    public void givenBasicAccount_thenUseBasicAccessors() {
        BasicAccount account = new BasicAccount();
        account.setBalance(BigDecimal.TEN);
        account.setName("Basic Accessors");

        assertEquals(BigDecimal.TEN, account.getBalance());
        assertEquals("Basic Accessors", account.getName());
    }

    @Test
    public void givenFluentAccount_thenUseFluentAccessors() {
        FluentAccount account = new FluentAccount();
        account.name("Fluent Account");
        account.balance(BigDecimal.TEN);

        assertEquals(BigDecimal.TEN, account.balance());
        assertEquals("Fluent Account", account.name());
    }

    @Test
    public void givenChainedAccount_thenUseChainedAccessors() {
        ChainedAccount account = new ChainedAccount();
        account.setName("Chained Account").setBalance(BigDecimal.TEN);

        assertEquals(BigDecimal.TEN, account.getBalance());
        assertEquals("Chained Account", account.getName());
    }

    @Test
    public void givenChainedFluentAccount_thenUseChainedFluentAccessors() {
        ChainedFluentAccount account = new ChainedFluentAccount()
          .name("Fluent Account")
          .balance(BigDecimal.TEN);

        assertEquals(BigDecimal.TEN, account.balance());
        assertEquals("Fluent Account", account.name());
    }

    @Test
    public void givenPrefixedAccount_thenRemovePrefixFromAccessors() {
        PrefixedAccount account = new PrefixedAccount();
        account.setName("Prefixed Fields");
        account.setBalance(BigDecimal.TEN);

        assertEquals(BigDecimal.TEN, account.getBalance());
        assertEquals("Prefixed Fields", account.getName());
    }

    @Test
    public void givenPrefixedFluentAccount_thenRemovePrefixFromAccessors() {
        PrefixedFluentAccount account = new PrefixedFluentAccount();
        account
          .name("Prefixed Fluent Fields")
          .balance(BigDecimal.TEN);

        assertEquals(BigDecimal.TEN, account.balance());
        assertEquals("Prefixed Fluent Fields", account.name());
    }

}
