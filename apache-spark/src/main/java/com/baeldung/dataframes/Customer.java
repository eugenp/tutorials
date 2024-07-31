package com.baeldung.dataframes;

public class Customer {
    String id;
    String name;
    String gender;
    int transaction_amount;

    public Customer() {

    }

    public Customer(String id, String name, String gender, int transaction_amount) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.transaction_amount = transaction_amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(int transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

}
