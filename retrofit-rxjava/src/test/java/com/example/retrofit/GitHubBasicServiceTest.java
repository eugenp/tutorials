package com.example.retrofit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.example.models.Contributer;
import com.example.models.Repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubBasicServiceTest {
    
    GitHubBasicService gitHub;
    
    @Before
    public void init() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        
        gitHub = retrofit.create(GitHubBasicService.class);
    }
    
    @Test
    public void whenListRepos_thenExpect12ReposThatContainsTutorials() {
        try {
            List<Repository> repos = gitHub
                .listRepos("eugenp")
                .execute()
                .body();
            
            assertThat(repos)
                .hasSize(12)
                .extracting(Repository::getName).contains("tutorials");
        } catch (IOException e) {
            fail("Can not communicate with GitHub API");
        }
    }
    
    @Test
    public void whenListRepoContributers_thenExpect30ContributerthatContainsEugenp() {
        try {
            List<Contributer> contributers = gitHub
                .listRepoContributers("eugenp", "tutorials")
                .execute()
                .body();
            
            assertThat(contributers)
                .hasSize(30)
                .extracting(Contributer::getName).contains("eugenp");
        } catch (IOException e) {
            fail("Can not communicate with GitHub API");
        }
    }

}
