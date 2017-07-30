package com.example.retrofit;

import java.util.List;

import com.example.models.Contributer;
import com.example.models.Repository;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubBasicService {

    @GET("users/{user}/repos")
    Call<List<Repository>> listRepos(@Path("user") String user);
    
    @GET("repos/{user}/{repo}/contributors")
    Call<List<Contributer>> listRepoContributers(
        @Path("user") String user,
        @Path("repo") String repo);
    
}
