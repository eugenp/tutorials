package com.example.retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.models.Contributer;
import com.example.models.Repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubBasicApp {
    
    public static void main(String[] args) throws IOException {
        
        String userName = "eugenp";
        
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        
        GitHubBasicService gitHub = retrofit.create(GitHubBasicService.class);
        
        List<Repository> repos = gitHub
            .listRepos(userName)
            .execute()
            .body();
        
        List<Contributer> topContributers = new ArrayList<>();
        
        for(Repository repo : repos) {
            List<Contributer> contributers = gitHub
                .listRepoContributers(userName, repo.getName())
                .execute()
                .body();
            
            for(Contributer contributer : contributers) {
                if(contributer.getContributions() > 100) {
                    topContributers.add(contributer);
                }
            }
        }
        
        Collections.sort(topContributers, (a, b) -> b.getContributions() - a.getContributions());
        
        Set<String> names = new HashSet<>();
        for(Contributer contributer : topContributers) {
            if(!names.contains(contributer.getName())) {
                System.out.println(contributer.getName());
            }
            names.add(contributer.getName());
        }
        
    }

}
