package com.baeldung.performancetests.model.destination;

import com.baeldung.performancetests.model.source.User;
import com.googlecode.jmapper.annotations.JGlobalMap;

@JGlobalMap
public class Review {

    int shippingGrade;
    int pricingGrade;
    int serviceGrade;
    User reviewingUser;
    String note;

    public int getShippingGrade() {
        return shippingGrade;
    }

    public void setShippingGrade(int shippingGrade) {
        this.shippingGrade = shippingGrade;
    }

    public int getPricingGrade() {
        return pricingGrade;
    }

    public void setPricingGrade(int pricingGrade) {
        this.pricingGrade = pricingGrade;
    }

    public int getServiceGrade() {
        return serviceGrade;
    }

    public void setServiceGrade(int serviceGrade) {
        this.serviceGrade = serviceGrade;
    }

    public User getReviewingUser() {
        return reviewingUser;
    }

    public void setReviewingUser(User reviewingUser) {
        this.reviewingUser = reviewingUser;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Review() {

    }

    public Review(int shippingGrade, int pricingGrade, int serviceGrade, User reviewingUser, String note) {

        this.shippingGrade = shippingGrade;
        this.pricingGrade = pricingGrade;
        this.serviceGrade = serviceGrade;
        this.reviewingUser = reviewingUser;
        this.note = note;
    }
}
