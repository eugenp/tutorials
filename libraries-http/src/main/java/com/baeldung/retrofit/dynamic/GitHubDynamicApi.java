package com.baeldung.retrofit.dynamic;

import com.baeldung.retrofit.models.Contributor;
import com.baeldung.retrofit.models.Repository;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

import java.util.List;

public interface GitHubDynamicApi {

    @GET
    Call<List<Repository>> reposList(@Url String url);

    @GET("{fullUrl}")
    Call<List<Contributor>> contributorsList(@Path(value = "fullUrl", encoded = true) String fullUrl);
}
