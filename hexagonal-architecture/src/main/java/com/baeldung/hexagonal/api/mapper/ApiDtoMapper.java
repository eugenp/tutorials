package com.baeldung.hexagonal.api.mapper;

import com.baeldung.hexagonal.api.dto.PostDto;
import com.baeldung.hexagonal.domain.bo.PostBo;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ApiDtoMapper {

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    @Bean("postDtoMapper")
    public BoundMapperFacade<PostDto, PostBo> postMapper() {
        return mapperFactory.getMapperFacade(PostDto.class, PostBo.class);
    }
}
