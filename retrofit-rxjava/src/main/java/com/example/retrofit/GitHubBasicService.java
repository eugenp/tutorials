package com.example.retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.example.models.Contributor;
import com.example.models.Repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubBasicService {
    
    private GitHubBasicApi gitHubApi;
    
    public GitHubBasicService() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
          
          gitHubApi = retrofit.create(GitHubBasicApi.class);
    }
    
    public List<String> getTopContributors(String userName) throws IOException {
        List<Repository> repos = gitHubApi
          .listRepos(userName)
          .execute()
          .body();
        
        List<Contributor> topContributors = new ArrayList<>();
        for(Repository repo : repos) {
            List<Contributor> contributers = gitHubApi
              .listRepoContributors(userName, repo.getName())
              .execute()
              .body();
            
            List<Contributor> repoTopContributors = contributers.stream()
              .filter(c -> c.getContributions() > 100)
              .collect(Collectors.toList());
            topContributors.addAll(repoTopContributors);
        }
        
        Collections.sort(topContributors, (a, b) -> b.getContributions() - a.getContributions());
        return topContributors.stream()
          .map(c -> c.getName())
          .distinct()
          .collect(Collectors.toList());
    }

}
