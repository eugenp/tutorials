package com.baeldung.lombok.accessors;

import com.baeldung.lombok.accessors.model.BasicAccount;
import com.baeldung.lombok.accessors.model.ChainedAccount;
import com.baeldung.lombok.accessors.model.ChainedFluentAccount;
import com.baeldung.lombok.accessors.model.FinalAccount;
import com.baeldung.lombok.accessors.model.FinalChainedFluentAccount;
import com.baeldung.lombok.accessors.model.FluentAccount;
import com.baeldung.lombok.accessors.model.PrefixedAccount;
import com.baeldung.lombok.accessors.model.PrefixedFluentAccount;
import org.junit.Test;

import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void givenFinalAccount_thenGettersAndSettersAreFinal() {
        FinalAccount account = new FinalAccount();
        account.setName("Final Account");
        account.setBalance(BigDecimal.TEN);

        assertEquals("Final Account", account.getName());
        assertEquals(BigDecimal.TEN, account.getBalance());

        //verify if all getters and setters are final methods
        boolean getterSettersAreFinal = Arrays.stream(FinalAccount.class.getMethods())
          .filter(method -> method.getName().matches("^(get|set)(Name|Balance)$"))
          .allMatch(method -> Modifier.isFinal(method.getModifiers()));
        assertTrue(getterSettersAreFinal);

    }

    @Test
    public void givenFinalChainedFluentAccount_thenGettersAndSettersAreFinal() {
        FinalChainedFluentAccount account = new FinalChainedFluentAccount();
        account
          .name("Final Chained Fluent Account")
          .balance(BigDecimal.TEN);

        assertEquals("Final Chained Fluent Account", account.name());
        assertEquals(BigDecimal.TEN, account.balance());

        //verify if all getters and setters are final methods
        boolean getterSettersAreFinal = Arrays.stream(FinalAccount.class.getMethods())
          .filter(method -> method.getName().matches("^(name|balance)$"))
          .allMatch(method -> Modifier.isFinal(method.getModifiers()));
        assertTrue(getterSettersAreFinal);

    }

}