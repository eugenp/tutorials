package com.baeldung.sparkjava;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SparkRestExample {
    public static void main(String[] args) {
        post("/users", (request, response) -> {
            User user = new Gson().fromJson(request.body(), User.class);
            UserStore.addUser(user);

            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });

        get("/users", (request, response) -> {
            return new Gson().toJson(
                new StandardResponse(StatusResponse.SUCCESS,new Gson()
                    .toJsonTree(UserStore.getUsers())));
        });

        get("/users/:id", (request, response) -> {
            return new Gson().toJson(
                new StandardResponse(StatusResponse.SUCCESS,new Gson()
                  .toJsonTree(UserStore.getUser(request.params(":id")))));
        });

        put("/users/:id", (request, response) -> {
            User editedUser = UserStore.editUser(request.params(":id"), 
                new Gson().fromJson(request.body(), HashMap.class));
            
            if (editedUser != null) {
                return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS,new Gson()
                        .toJsonTree(editedUser)));
            }else  {
                return new Gson().toJson(
                    new StandardResponse(StatusResponse.ERROR,new Gson()
                        .toJson("User not found or error in edit")));
            }
        });

        delete("/users/:id", (request, response) -> {
            UserStore.deleteUser(request.params(":id"));
            return new Gson().toJson(
                new StandardResponse(StatusResponse.SUCCESS, "user deleted"));
        });

        options("/users/:id", (request, response) -> {
            return new Gson().toJson(
                new StandardResponse(StatusResponse.SUCCESS, 
                    (UserStore.userExist(
                        request.params(":id"))) ? "User exists" : "User does not exists" ));
        });

    }

}
