package com.baeldung.creationaldp.builder;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BuilderPatternIntegrationTest {
    @Test
    public void whenCreatingObjectThroughBuilder_thenObjectValid() {
        BankAccount newAccount = new BankAccount
          .BankAccountBuilder("Jon", "22738022275")
          .withEmail("jon@example.com")
          .withAge(22)
          .wantNewsletter(true)
          .build();
        
        assertEquals(newAccount.getName(), "Jon");
        assertEquals(newAccount.getAccountNumber(), "22738022275");
        assertEquals(newAccount.getEmail(), "jon@example.com");
        assertEquals(newAccount.getAge(), 22);
        assertEquals(newAccount.isNewsletter(), true);
    }
    
    @Test
    public void whenSkippingOptionalParameters_thenObjectValid() {
        BankAccount newAccount = new BankAccount
          .BankAccountBuilder("Jon", "22738022275")
          .build();
        
        assertEquals(newAccount.getName(), "Jon");
        assertEquals(newAccount.getAccountNumber(), "22738022275");
        assertEquals(newAccount.getEmail(), null);
        assertEquals(newAccount.getAge(), 0);
        assertEquals(newAccount.isNewsletter(), false);
    }
}
