package com.baeldung.retrofit.dynamic;

import com.baeldung.retrofit.models.Contributor;
import com.baeldung.retrofit.models.Repository;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class GitHubDynamicApiService {

    private GitHubDynamicApi gitHubDynamicApi;

    GitHubDynamicApiService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/").addConverterFactory(GsonConverterFactory.create()).build();

        gitHubDynamicApi = retrofit.create(GitHubDynamicApi.class);
    }

    List<String> getTopContributors(String url) throws IOException {
        List<Repository> repos = gitHubDynamicApi.reposList(url).execute().body();

        repos = repos != null ? repos : Collections.emptyList();

        return repos.stream().flatMap(repo -> getContributors("repos/eugenp/"+repo+"/contributors")).sorted((a, b) -> b.getContributions() - a.getContributions()).map(com.baeldung.retrofit.models.Contributor::getName).distinct().sorted().collect(Collectors.toList());
    }

    private Stream<Contributor> getContributors(String fullUrl) {
        List<Contributor> contributors = null;
        try {
            contributors = gitHubDynamicApi.contributorsList(fullUrl).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        contributors = contributors != null ? contributors : Collections.emptyList();

        return contributors.stream().filter(c -> c.getContributions() > 100);
    }
}
