package com.example.retrofitrx;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class GitHubRxService {
    
    private GitHubRxApi gitHubApi;
    
    public GitHubRxService() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();
        
        gitHubApi = retrofit.create(GitHubRxApi.class);
    }
    
    public Observable<String> getTopContributors(String userName) {
        return gitHubApi.listRepos(userName)
          .flatMap( repos -> Observable.from(repos))
          .flatMap( repo -> gitHubApi.listRepoContributors(userName, repo.getName()) )
          .flatMap( contributers -> Observable.from(contributers))
          .filter( c -> c.getContributions() > 100)
          .sorted( (a, b) -> b.getContributions() - a.getContributions() )
          .map( c -> c.getName())
          .distinct();
    }

}
