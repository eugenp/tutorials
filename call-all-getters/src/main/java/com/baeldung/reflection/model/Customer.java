package com.baeldung.reflection.model;

/**
 * 
 * @author himanshumantri
 *
 */
public class Customer {

    private Integer id;
    private String name;
    private String emailId;
    private Long phoneNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Customer [id=").append(id).append(", name=").append(name).append(", emailId=").append(emailId).append(", phoneNumber=")
                .append(phoneNumber).append("]");
        return builder.toString();
    }

    public Customer(Integer id, String name, String emailId, Long phoneNumber) {
        super();
        this.id = id;
        this.name = name;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
