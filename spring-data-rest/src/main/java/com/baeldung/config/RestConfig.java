package com.baeldung.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import com.baeldung.projections.CustomBook;


@Configuration
public class RestConfig extends RepositoryRestConfigurerAdapter{

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration repositoryRestConfiguration){
        repositoryRestConfiguration.getProjectionConfiguration().addProjection(CustomBook.class);
    }
}
