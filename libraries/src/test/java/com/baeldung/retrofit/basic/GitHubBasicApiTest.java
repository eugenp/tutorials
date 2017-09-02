package com.baeldung.retrofit.basic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.baeldung.retrofit.basic.GitHubBasicApi;
import com.baeldung.retrofit.models.Contributor;
import com.baeldung.retrofit.models.Repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubBasicApiTest {
    
    GitHubBasicApi gitHub;
    
    @Before
    public void init() {
        Retrofit retrofit = new Retrofit.Builder()
          .baseUrl("https://api.github.com/")
          .addConverterFactory(GsonConverterFactory.create())
          .build();
        
        gitHub = retrofit.create(GitHubBasicApi.class);
    }
    
    @Test
<<<<<<< HEAD
    public void whenListRepos_thenExpectReposThatContainTutorials() {
=======
    public void whenListRepos_thenExpect12ReposThatContainsTutorials() {
>>>>>>> 966d84698081ac0d2a086733b554bd900f2327c0
        try {
            List<Repository> repos = gitHub
              .listRepos("eugenp")
              .execute()
              .body();
            
            assertThat(repos)
<<<<<<< HEAD
              .isNotEmpty()
=======
              .hasSize(12)
>>>>>>> 966d84698081ac0d2a086733b554bd900f2327c0
              .extracting(Repository::getName).contains("tutorials");
        } catch (IOException e) {
            fail("Can not communicate with GitHub API");
        }
    }
    
    @Test
<<<<<<< HEAD
    public void whenListRepoContributers_thenExpectContributorsThatContainEugenp() {
        try {
            List<Contributor> contributors = gitHub
=======
    public void whenListRepoContributers_thenExpect30ContributerthatContainsEugenp() {
        try {
            List<Contributor> contributers = gitHub
>>>>>>> 966d84698081ac0d2a086733b554bd900f2327c0
              .listRepoContributors("eugenp", "tutorials")
              .execute()
              .body();
            
<<<<<<< HEAD
            assertThat(contributors)
              .isNotEmpty()
=======
            assertThat(contributers)
              .hasSize(30)
>>>>>>> 966d84698081ac0d2a086733b554bd900f2327c0
              .extracting(Contributor::getName).contains("eugenp");
        } catch (IOException e) {
            fail("Can not communicate with GitHub API");
        }
    }

}
