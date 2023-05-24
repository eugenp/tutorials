package com.baeldung.mapper;

import com.baeldung.dto.ArticleDTO;
import com.baeldung.dto.PersonDTO;
import com.baeldung.entity.Article;
import com.baeldung.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = PersonMapper.class)
public interface ArticleUsingPersonMapper {

    ArticleUsingPersonMapper INSTANCE = Mappers.getMapper(ArticleUsingPersonMapper.class);

    ArticleDTO articleToArticleDto(Article article);
}
