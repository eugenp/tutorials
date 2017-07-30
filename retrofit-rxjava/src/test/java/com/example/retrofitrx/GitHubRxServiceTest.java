package com.example.retrofitrx;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.example.models.Contributer;
import com.example.models.Repository;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubRxServiceTest {
    
    GitHubRxService gitHub;
    
    @Before
    public void init() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();
        
        gitHub = retrofit.create(GitHubRxService.class);
    }
    
    @Test
    public void whenListRepos_thenExpect12ReposThatContainsTutorials() {
        gitHub
            .listRepos("eugenp")
            .subscribe( repos -> {
                assertThat(repos)
                .hasSize(12)
                .extracting(Repository::getName).contains("tutorials");
            });
    }
    
    @Test
    public void whenListRepoContributers_thenExpect30ContributerthatContainsEugenp() {
        gitHub
            .listRepoContributers("eugenp", "tutorials")
            .subscribe(contributers -> {
                assertThat(contributers)
                .hasSize(30)
                .extracting(Contributer::getName).contains("eugenp");
            });
    }

}
