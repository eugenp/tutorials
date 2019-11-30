package com.baeldung.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order")
public class Order{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "amount", nullable = false)
    private Long amount;

    public Order() {
        super();
        //Auto-generated constructor stub
    }

    public Order(Long amount) {
        super();
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
    
    public boolean isOrderValid() {
        if(this.amount > 0 && this.id > 0) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
