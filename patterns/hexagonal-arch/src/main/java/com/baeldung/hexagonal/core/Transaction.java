package com.baeldung.hexagonal.core;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Basic domain model stub
 * @author ysharma2512
 *
 */
public class Transaction {
    public Transaction(Date date, BigDecimal amount, String type) {
        super();
        this.date = date;
        this.amount = amount;
        this.type = type;
    }

    private Date date;
    private BigDecimal amount;
    private String type;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
