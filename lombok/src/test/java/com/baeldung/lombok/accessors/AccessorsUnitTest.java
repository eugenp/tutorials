package com.baeldung.lombok.accessors;

import com.baeldung.lombok.accessors.model.*;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class AccessorsUnitTest {

    @Test
    public void givenBasicAccount_thenUseBasicAccessors() {
        BasicAccount account = new BasicAccount();
        account.setName("Basic Accessors");
        account.setBalance(BigDecimal.TEN);

        assertEquals("Basic Accessors", account.getName());
        assertEquals(BigDecimal.TEN, account.getBalance());
    }

    @Test
    public void givenFluentAccount_thenUseFluentAccessors() {
        FluentAccount account = new FluentAccount();
        account.name("Fluent Account");
        account.balance(BigDecimal.TEN);

        assertEquals("Fluent Account", account.name());
        assertEquals(BigDecimal.TEN, account.balance());
    }

    @Test
    public void givenChainedAccount_thenUseChainedAccessors() {
        ChainedAccount account = new ChainedAccount();
        account.setName("Chained Account").setBalance(BigDecimal.TEN);

        assertEquals("Chained Account", account.getName());
        assertEquals(BigDecimal.TEN, account.getBalance());
    }

    @Test
    public void givenChainedFluentAccount_thenUseChainedFluentAccessors() {
        ChainedFluentAccount account = new ChainedFluentAccount()
          .name("Fluent Account")
          .balance(BigDecimal.TEN);

        assertEquals("Fluent Account", account.name());
        assertEquals(BigDecimal.TEN, account.balance());
    }

    @Test
    public void givenPrefixedAccount_thenRemovePrefixFromAccessors() {
        PrefixedAccount account = new PrefixedAccount();
        account.setName("Prefixed Fields");
        account.setBalance(BigDecimal.TEN);
        account.setNotes("Notes");

        assertEquals("Prefixed Fields", account.getName());
        assertEquals(BigDecimal.TEN, account.getBalance());
        assertEquals("Notes", account.getNotes());
    }

    @Test
    public void givenPrefixedFluentAccount_thenRemovePrefixFromAccessors() {
        PrefixedFluentAccount account = new PrefixedFluentAccount();
        account
          .name("Prefixed Fluent Fields")
          .balance(BigDecimal.TEN);

        assertEquals("Prefixed Fluent Fields", account.name());
        assertEquals(BigDecimal.TEN, account.balance());
    }

}
