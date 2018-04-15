package com.baeldung.server;

import java.util.Date;

import javax.validation.constraints.NotEmpty;


public class Tweet {
	
    @NotEmpty
    private String id;

    @NotEmpty
    private String text;

    private Date createdAt = new Date();

    private Tweet() {
    }

    private Tweet(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public static Tweet createTweet(String id, String text) {
    	return new Tweet(id, text);    	
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}