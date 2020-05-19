package com.baeldung.architecture.hexagonal.configuration;

import com.baeldung.architecture.hexagonal.HexagonalApplication;
import com.baeldung.architecture.hexagonal.PostService;
import com.baeldung.architecture.hexagonal.adapter.PostRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = HexagonalApplication.class)
public class BeanConfiguration {

    @Bean
    PostService postService(PostRepository repository) {
        return new PostService(repository);
    }
}
