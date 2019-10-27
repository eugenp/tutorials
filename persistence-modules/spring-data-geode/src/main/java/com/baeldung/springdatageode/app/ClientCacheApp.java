package com.baeldung.springdatageode.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableClusterConfiguration;
import org.springframework.data.gemfire.config.annotation.EnableContinuousQueries;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnableIndexing;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

import com.baeldung.springdatageode.controller.AppController;
import com.baeldung.springdatageode.domain.Author;
import com.baeldung.springdatageode.repo.AuthorRepository;
import com.baeldung.springdatageode.service.AuthorService;

@SpringBootApplication
@ClientCacheApplication(subscriptionEnabled = true)
@EnableEntityDefinedRegions(basePackageClasses = Author.class)
@EnableIndexing
@EnableGemfireRepositories(basePackageClasses = AuthorRepository.class)
@ComponentScan(basePackageClasses = {AppController.class, AuthorService.class})
@EnableClusterConfiguration(useHttp = true, requireHttps=false)
@EnableContinuousQueries
public class ClientCacheApp {
    
    public static void main(String[] args) {
        SpringApplication.run(ClientCacheApp.class, args);
    }
    
}
