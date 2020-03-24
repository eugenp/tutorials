package com.baeldung.lambda.dynamodb.bean;

import com.google.gson.Gson;

public class PersonResponse {
    private String message;

    public String toString() {
        final Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
