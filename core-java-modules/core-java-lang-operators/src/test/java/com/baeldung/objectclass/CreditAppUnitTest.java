package com.baeldung.objectclass;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class CreditAppUnitTest {
    
    @Test
    public void givenLender_whenInstanceOf_thenReturnTrue() {
        User lender = new Lender();
        assertTrue(lender instanceof Lender);
        assertTrue(lender instanceof User);
    }
    
    @Test
    public void givenUser_whenInstanceOfLender_thenDowncast() {
        User user = new Lender();
        Lender lender = null;
        
        if(user instanceof Lender) {
            lender = (Lender) user;
        }
        
        assertNotNull(lender);
    }
    
    @Test
    public void givenUser_whenIsInstanceOfLender_thenDowncast() {
        User user = new Lender();
        Lender lender = null;
        
        if(Lender.class.isInstance(user)) {
            lender = (Lender) user;
        }
        
        assertNotNull(lender);
    }
    
    @Ignore
    @Test
    public void givenBorrower_whenDoubleOrNotString_thenRequestLoan() {
        Borrower borrower = new Borrower();
        double amount = 100.0;
        
        /*if(amount instanceof Double) { // Compilation error, no autoboxing
            borrower.requestLoan(amount);
        }
        
        if(!(amount instanceof String)) { // Compilation error, incompatible operands
            borrower.requestLoan(amount);
        }*/
        
    }
    
    @Test
    public void givenBorrower_whenLoanAmountIsDouble_thenRequestLoan() {
        Borrower borrower = new Borrower();
        double amount = 100.0;
        
        if(Double.class.isInstance(amount)) { // No compilation error
            borrower.requestLoan(amount);
        }
        assertEquals(100, borrower.getTotalLoanAmount());
    }
    
    @Test
    public void givenBorrower_whenLoanAmountIsNotString_thenRequestLoan() {
        Borrower borrower = new Borrower();
        Double amount = 100.0;
        
        if(!String.class.isInstance(amount)) { // No compilation error
            borrower.requestLoan(amount);
        }
        assertEquals(100, borrower.getTotalLoanAmount());
    }
    
    @Test
    public void givenLender_whenGetClass_thenEqualsLenderType() {
        User lender = new Lender();
        assertEquals(Lender.class, lender.getClass());
        assertNotEquals(User.class, lender.getClass());
    }

}
