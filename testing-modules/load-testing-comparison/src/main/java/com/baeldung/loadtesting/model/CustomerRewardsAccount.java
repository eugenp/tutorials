package com.baeldung.loadtesting.model;

public class CustomerRewardsAccount {

    private Integer id;
    private Integer customerId;

    public Integer getCustomerId(){
        return this.customerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    
    
}
