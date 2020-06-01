package com.baeldung.hexagonal.framework;

import com.baeldung.hexagonal.application.PlayListService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayListConfig {

    @Bean
    public PlayListRepoAdapter getPlayListRepo() {
        return new PlayListRepo();
    }

    @Bean
    public PlayListService getPlayListService(PlayListRepoAdapter repoAdapter) {
        return new PlayListService(repoAdapter);
    }

}
