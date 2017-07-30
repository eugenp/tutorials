package com.example.retrofitrx;

import java.util.List;

import com.example.models.Contributer;
import com.example.models.Repository;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GitHubRxService {

    @GET("users/{user}/repos")
    Observable<List<Repository>> listRepos(@Path("user") String user);
    
    @GET("repos/{user}/{repo}/contributors")
    Observable<List<Contributer>> listRepoContributers(
        @Path("user") String user,
        @Path("repo") String repo);
    
}
