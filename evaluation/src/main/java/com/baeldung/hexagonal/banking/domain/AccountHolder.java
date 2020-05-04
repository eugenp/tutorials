package com.baeldung.hexagonal.banking.domain;


public class AccountHolder {
    
    private final int idNumber;
  
    public AccountHolder(int idNumber){
        this.idNumber = idNumber;
    }

    public int getIdNumber() {
        return idNumber;
    }
   
    
}
