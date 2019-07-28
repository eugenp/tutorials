package org.baeldung.examples.r2dbc;

import java.math.BigDecimal;

public class Account {

    private Long id;
    private String iban;
    private BigDecimal balance;
    
    
    public Account() {}
    
    public Account(Long id, String iban, BigDecimal balance) {
        this.id = id;
        this.iban = iban;
        this.balance = balance;
    }

    public Account(Long id, String iban, Double balance) {
        this.id = id;
        this.iban = iban;
        this.balance = new BigDecimal(balance);
    }
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the iban
     */
    public String getIban() {
        return iban;
    }

    /**
     * @param iban the iban to set
     */
    public void setIban(String iban) {
        this.iban = iban;
    }

    /**
     * @return the balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
