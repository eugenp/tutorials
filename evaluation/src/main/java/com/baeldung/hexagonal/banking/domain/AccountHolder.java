package com.baeldung.hexagonal.banking.domain;


public class AccountHolder {
    
    private int idNumber;
    private String firstName;
    private String lastName;

  
    public AccountHolder(int idNumber, String firstName, String lastName){
        this.idNumber = idNumber;
       
    }

    public int getIdNumber() {
        return idNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    
    
   
    
}
