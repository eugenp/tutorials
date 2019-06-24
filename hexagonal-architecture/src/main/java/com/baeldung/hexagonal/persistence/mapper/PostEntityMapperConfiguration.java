package com.baeldung.hexagonal.persistence.mapper;

import com.baeldung.hexagonal.core.domain.bo.PostBo;
import com.baeldung.hexagonal.persistence.entity.Post;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PostEntityMapperConfiguration {

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    @Bean("postEntityMapper")
    public BoundMapperFacade<Post, PostBo> postMapper() {
        return mapperFactory.getMapperFacade(Post.class, PostBo.class);
    }
}
