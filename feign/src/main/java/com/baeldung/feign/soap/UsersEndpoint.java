package com.baeldung.feign.soap;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.HashMap;
import java.util.Map;

@Endpoint
public class UsersEndpoint {

    private static final Map<String, User> userMap = new HashMap<>();

    @PayloadRoot(namespace = "http://www.baeldung.com/springbootsoap/feignclient", localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        GetUserResponse response = new GetUserResponse();
        response.setUser(userMap.get(request.getId()));
        return response;
    }

    @PayloadRoot(namespace = "http://www.baeldung.com/springbootsoap/feignclient", localPart = "createUserRequest")
    @ResponsePayload
    public CreateUserResponse createUser(@RequestPayload CreateUserRequest request) {
        CreateUserResponse response = new CreateUserResponse();
        if (request.getUser().getId().equalsIgnoreCase("500"))
            throw new RuntimeException("This is a reserved user id");
        userMap.put(request.getUser().id, request.getUser());

        response.setMessage("Success! Created the user with id - " + request.getUser().getId());
        return response;
    }

}
