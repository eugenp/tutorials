package com.baeldung.hexagonal.domain;

public class Customer {

    private int customerId;
    private String firstName;
    private String lastName;
    private MemeberStatus status;
    private int rewardedpoints;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public MemeberStatus getStatus() {
        return status;
    }

    public void setStatus(MemeberStatus status) {
        this.status = status;
    }

    public int getRewardedpoints() {
        return rewardedpoints;
    }

    public void setRewardedpoints(int rewardedpoints) {
        this.rewardedpoints = rewardedpoints;
    }

}
