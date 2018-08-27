package com.baeldung.performancetests.model.destination;

import com.baeldung.performancetests.model.source.Address;
import com.googlecode.jmapper.annotations.JGlobalMap;

import java.util.List;
@JGlobalMap
public class Shop {

    private String shopName;
    private Address shopAddres;
    private String shopUrl;
    private List<Review> reviews;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Address getShopAddres() {
        return shopAddres;
    }

    public void setShopAddres(Address shopAddres) {
        this.shopAddres = shopAddres;
    }

    public String  getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public Shop() {
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Shop(String shopName, Address shopAddres, String shopUrl, List<Review> reviews) {

        this.shopName = shopName;
        this.shopAddres = shopAddres;
        this.shopUrl = shopUrl;
        this.reviews = reviews;
    }
}
