package com.baeldung.retrofitguide;

import java.io.IOException;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main {

    public static void main(String[] args) {
        // Manual creation
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/").addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        UserService service = retrofit.create(UserService.class);
        // Using GitHubServiceGenerator
        service = GitHubServiceGenerator.createService(UserService.class);
        Call<User> callSync = service.getUser("eugenp");
        Call<User> callAsync = service.getUser("eugenp");

        try {
            Response<User> response = callSync.execute();
            User user = response.body();
            System.out.println(user);
        } catch (IOException ex) {
        }

        // Execute the call asynchronously. Get a positive or negative callback.
        callAsync.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                System.out.println(user);
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                System.out.println(throwable);
            }
        });

    }
}
