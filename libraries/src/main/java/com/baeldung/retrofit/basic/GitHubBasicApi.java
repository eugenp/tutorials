package com.baeldung.retrofit.basic;

import java.util.List;

import com.baeldung.retrofit.models.Contributor;
import com.baeldung.retrofit.models.Repository;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubBasicApi {

    /**
     * List GitHub repositories of user
     * @param user GitHub Account
     * @return GitHub repositories
     */
    @GET("users/{user}/repos")
    Call<List<Repository>> listRepos(@Path("user") String user);

    /**
     * List Contributors of a GitHub Repository
     * @param user GitHub Account
     * @param repo GitHub Repository
     * @return GitHub Repository Contributors
     */
    @GET("repos/{user}/{repo}/contributors")
    Call<List<Contributor>> listRepoContributors(@Path("user") String user, @Path("repo") String repo);

}
