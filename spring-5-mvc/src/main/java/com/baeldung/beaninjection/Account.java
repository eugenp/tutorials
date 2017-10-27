package com.baeldung.beaninjection;

import org.springframework.stereotype.Component;

@Component
public class Account {

    private Long accountNumber;

    /**
     * @return the accountNumber
     */
    public Long getAccountNumber() {
        return accountNumber;
    }

    /**
     * @param accountNumber the accountNumber to set
     */
    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

}
