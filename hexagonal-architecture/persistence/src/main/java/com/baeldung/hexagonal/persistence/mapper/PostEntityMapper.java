package com.baeldung.hexagonal.persistence.mapper;

import com.baeldung.hexagonal.core.bo.PostBo;
import com.baeldung.hexagonal.persistence.entity.Post;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class PostEntityMapper {

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    public BoundMapperFacade<Post, PostBo> postMapper() {
        return mapperFactory.getMapperFacade(Post.class, PostBo.class);
    }
}
