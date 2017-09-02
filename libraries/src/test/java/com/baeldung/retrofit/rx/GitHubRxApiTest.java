package com.baeldung.retrofit.rx;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.baeldung.retrofit.models.Contributor;
import com.baeldung.retrofit.models.Repository;
import com.baeldung.retrofit.rx.GitHubRxApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubRxApiTest {
    
    GitHubRxApi gitHub;
    
    @Before
    public void init() {
        Retrofit retrofit = new Retrofit.Builder()
          .baseUrl("https://api.github.com/")
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
          .build();
        
        gitHub = retrofit.create(GitHubRxApi.class);
    }
    
    @Test
<<<<<<< HEAD
    public void whenListRepos_thenExpectReposThatContainTutorials() {
=======
    public void whenListRepos_thenExpect12ReposThatContainsTutorials() {
>>>>>>> 966d84698081ac0d2a086733b554bd900f2327c0
        gitHub
          .listRepos("eugenp")
          .subscribe( repos -> {
              assertThat(repos)
<<<<<<< HEAD
                .isNotEmpty()
=======
                .hasSize(12)
>>>>>>> 966d84698081ac0d2a086733b554bd900f2327c0
                .extracting(Repository::getName).contains("tutorials");
          });
    }
    
    @Test
<<<<<<< HEAD
    public void whenListRepoContributers_thenExpectContributorsThatContainEugenp() {
        gitHub
          .listRepoContributors("eugenp", "tutorials")
          .subscribe(contributors -> {
              assertThat(contributors)
                .isNotEmpty()
=======
    public void whenListRepoContributers_thenExpect30ContributerthatContainsEugenp() {
        gitHub
          .listRepoContributors("eugenp", "tutorials")
          .subscribe(contributers -> {
              assertThat(contributers)
                .hasSize(30)
>>>>>>> 966d84698081ac0d2a086733b554bd900f2327c0
                .extracting(Contributor::getName).contains("eugenp");
          });
    }

}
