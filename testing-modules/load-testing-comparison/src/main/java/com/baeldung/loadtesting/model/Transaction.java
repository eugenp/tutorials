package com.baeldung.loadtesting.model;

import java.util.Date;

public class Transaction {

    private Integer id;
    private Integer customerRewardsId;
    private Integer customerId;
    private Date transactionDate;

    public void setTransactionDate(Date transactionDate){
        this.transactionDate = transactionDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerRewardsId() {
        return customerRewardsId;
    }

    public void setCustomerRewardsId(Integer customerRewardsId) {
        this.customerRewardsId = customerRewardsId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }
    
    
}
