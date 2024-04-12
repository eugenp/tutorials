package com.baeldung.streams.filter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Customer {
    private String name;
    private int points;
    private String profilePhotoUrl;

    public Customer(String name, int points) {
        this(name, points, "");
    }

    public Customer(String name, int points, String profilePhotoUrl) {
        this.name = name;
        this.points = points;
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public boolean hasOver(int points) {
        return this.points > points;
    }

    public boolean hasOverHundredPoints() {
        return this.points > 100;
    }

    public boolean hasValidProfilePhoto() throws IOException {
        URL url = new URL(this.profilePhotoUrl);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
    }

    public boolean hasValidProfilePhotoWithoutCheckedException() {
        try {
            URL url = new URL(this.profilePhotoUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
