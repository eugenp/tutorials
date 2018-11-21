package com.baeldung.loadtesting.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.Calendar;

@Entity
@Data
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
}
