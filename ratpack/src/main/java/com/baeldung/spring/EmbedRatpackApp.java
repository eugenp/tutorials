package com.baeldung.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.server.ServerConfig;
import ratpack.spring.config.EnableRatpack;

/**
 * @author aiet
 */
@SpringBootApplication
@EnableRatpack
public class EmbedRatpackApp {

    @Autowired private Content content;
    @Autowired private ArticleList list;

    @Bean
    public Action<Chain> hello() {
        return chain -> chain.get("hello", ctx -> ctx.render(content.body()));
    }

    @Bean
    public Action<Chain> list() {
        return chain -> chain.get("list", ctx -> ctx.render(list
          .articles()
          .toString()));
    }

    @Bean
    public ServerConfig ratpackServerConfig() {
        return ServerConfig
          .builder()
          .findBaseDir("public")
          .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(EmbedRatpackApp.class, args);
    }

}
