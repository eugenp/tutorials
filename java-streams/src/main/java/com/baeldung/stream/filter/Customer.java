package com.baeldung.stream.filter;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

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

    public boolean hasValidProfilePhoto() throws IOException {
        URL url = new URL(profilePhotoUrl);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        //Without User-Agent pexels.com returns HTTP 403
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36");
        return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
    }

    public boolean hasValidProfilePhotoWithoutCheckedException() {
        try {
            URL url = new URL(profilePhotoUrl);
            HttpsURLConnection connection = null;
            connection = (HttpsURLConnection) url.openConnection();
            //Without User-Agent pexels.com returns HTTP 403
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36");
            return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
