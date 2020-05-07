package com.baeldung.hexagonal.banking.domain;


public class AccountHolder {
    
    private int idNumber;
    private String holderType;
  
    public AccountHolder(int idNumber, String holderType){
        this.idNumber = idNumber;
        this.holderType = holderType;
    }

    public int getIdNumber() {
        return idNumber;
    }
    
    public String getHolderType() {
        return holderType;
    }
   
    
}
