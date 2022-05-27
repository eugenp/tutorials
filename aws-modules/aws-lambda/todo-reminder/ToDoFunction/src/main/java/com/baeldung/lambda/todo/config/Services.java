package com.baeldung.lambda.todo.config;

import com.baeldung.lambda.todo.api.PostApi;
import com.baeldung.lambda.todo.api.ToDoApi;
import com.google.inject.AbstractModule;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;
import uk.org.webcompere.lightweightconfig.ConfigLoader;

import static feign.Logger.Level.FULL;

public class Services extends AbstractModule {
    @Override
    protected void configure() {
        Config config = ConfigLoader.loadYmlConfigFromResource("configuration.yml", Config.class);

        ToDoApi toDoApi = Feign.builder()
          .decoder(new GsonDecoder())
          .logger(new Slf4jLogger())
          .logLevel(FULL)
          .requestInterceptor(new BasicAuthRequestInterceptor(config.getToDoCredentials().getUsername(), config.getToDoCredentials().getPassword()))
          .target(ToDoApi.class, config.getToDoEndpoint());

        PostApi postApi = Feign.builder()
          .encoder(new GsonEncoder())
          .logger(new Slf4jLogger())
          .logLevel(FULL)
          .requestInterceptor(new BasicAuthRequestInterceptor(config.getPostCredentials().getUsername(), config.getPostCredentials().getPassword()))
          .target(PostApi.class, config.getPostEndpoint());

        bind(Config.class).toInstance(config);
        bind(ToDoApi.class).toInstance(toDoApi);
        bind(PostApi.class).toInstance(postApi);
    }
}
