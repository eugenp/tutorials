package com.forketyfork.guest.springmvc.web;

public class MyOutputResource {

    private String responseMessage;

    public MyOutputResource(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

}
