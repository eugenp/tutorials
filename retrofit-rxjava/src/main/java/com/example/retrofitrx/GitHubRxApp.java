package com.example.retrofitrx;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class GitHubRxApp {
    
    public static void main(String[] args) throws IOException {
        
        String userName = "eugenp";
        
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();
        
        GitHubRxService gitHub = retrofit.create(GitHubRxService.class);
        
        gitHub.listRepos(userName)
            .flatMap( repos -> Observable.from(repos))
            .flatMap( repo -> gitHub.listRepoContributers(userName, repo.getName()) )
            .flatMap( contributers -> Observable.from(contributers))
            .filter( c -> c.getContributions() > 100)
            .sorted( (a, b) -> b.getContributions() - a.getContributions() )
            .map( c -> c.getName())
            .distinct()
            .subscribe(System.out::println);
        
    }
    
}
