package com.baeldung.mybatis.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringMybatisApplication implements ApplicationRunner {

    @Autowired
    private ArticleMapper articleMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringMybatisApplication.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        articleMapper.getArticle(1L);
    }
}
