package com.baeldung.facebook;

import com.restfb.BinaryAttachment;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.FacebookType;
import com.restfb.types.Page;
import com.restfb.types.User;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.exception.FacebookResponseContentException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FacebookService {

    @Autowired
    private FacebookClient facebookClient;

    @Value("${facebook.app.secret}")
    private String appSecret;

    private static final Logger logger = Logger.getLogger(FacebookService.class.getName());


    public User getUserProfile() {
        try {
            return facebookClient.fetchObject("me", User.class, Parameter.with("fields", "id,name,email"));
        } catch (FacebookOAuthException e) {
            // Handle expired/invalid token
            logger.log(Level.SEVERE,"Authentication failed: " + e.getMessage());
            return null;
        } catch (FacebookResponseContentException e) {
            // General API errors
            logger.log(Level.SEVERE,"API error: " + e.getMessage());
            return null;
        }
    }

    public List<User> getFriendList() {
        try {
            Connection<User> friendsConnection = facebookClient.fetchConnection("me/friends", User.class);
            return friendsConnection.getData();
        } catch (Exception e) {

            logger.log(Level.SEVERE,"Error fetching friends list: " + e.getMessage());
            return null;
        }
    }

    public String postStatusUpdate(String message) {
        try {
            FacebookType response = facebookClient.publish("me/feed", FacebookType.class, Parameter.with("message", message));
            return "Post ID: " + response.getId();
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Failed to post status: " + e.getMessage());
            return null;
        }
    }

    public void uploadPhotoToFeed() {
        try (InputStream imageStream = getClass().getResourceAsStream("/static/image.jpg")) {
            FacebookType response = facebookClient.publish("me/photos", FacebookType.class, BinaryAttachment.with("image.jpg", imageStream),
                Parameter.with("message", "Uploaded with RestFB"));
            logger.log(Level.INFO,"Photo uploaded. ID: " + response.getId());
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Failed to read image file: " + e.getMessage());
        }
    }

    public String postToPage(String pageId, String message) {
        try {
            Page page = facebookClient.fetchObject(pageId, Page.class, Parameter.with("fields", "access_token"));

            FacebookClient pageClient = new DefaultFacebookClient(page.getAccessToken(), appSecret, Version.LATEST);

            FacebookType response = pageClient.publish(pageId + "/feed", FacebookType.class, Parameter.with("message", message));

            return "Page Post ID: " + response.getId();
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Failed to post to page: " + e.getMessage());
            return null;
        }
    }
}