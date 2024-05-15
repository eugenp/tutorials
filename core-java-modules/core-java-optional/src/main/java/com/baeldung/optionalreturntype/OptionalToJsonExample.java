package com.baeldung.optionalreturntype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OptionalToJsonExample {
    public static void main(String[] args) {
        UserOptional user = new UserOptional();
        user.setUserId(1l);
        user.setFirstName("Bael Dung");

        ObjectMapper om = new ObjectMapper();
        try {
            System.out.print("user in json is:" + om.writeValueAsString(user));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
