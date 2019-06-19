package com.baeldung.hexagonal.api.mapper;

import com.baeldung.hexagonal.api.dto.PostDto;
import com.baeldung.hexagonal.core.bo.PostBo;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class ApiDtoMapper {

  MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

  public BoundMapperFacade<PostDto, PostBo> postMapper() {
    return mapperFactory.getMapperFacade(PostDto.class, PostBo.class);
  }
}
