package com.baeldung.tostring;

import java.util.List;

public class CustomerS4  {
    private String firstName;
    private String lastName;
    private Integer score;
    private List<String> orders;
    private StringBuffer fullname;
 
    public Integer getScore() {
        return score;
    }
    public void setScore(Integer score) {
        this.score = score;
    }
    public List<String> getOrders() {
        return orders;
    }
    public void setOrders(List<String> orders) {
        this.orders = orders;
    }
    public StringBuffer getFullname() {
        return fullname;
    }
    public void setFullname(StringBuffer fullname) {
        this.fullname = fullname;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
   
    @Override
    public String toString() {
        return "Customer [firstName=" + firstName + ", lastName=" + lastName + ", score=" + score + ", orders=" + orders + ", fullname=" + fullname + "]";
    }
}