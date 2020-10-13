package com.baeldung.loadtesting.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.Calendar;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private Integer customerRewardsId;
    private Integer customerId;
    private Date transactionDate;

    public Transaction(){
        transactionDate = Calendar.getInstance().getTime();
    }

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
