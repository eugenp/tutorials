package com.baeldung.mapper;

import com.baeldung.dto.ArticleDTO;
import com.baeldung.dto.PersonDTO;
import com.baeldung.entity.Article;
import com.baeldung.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleMapper {
    
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);
    
    ArticleDTO articleToArticleDto(Article article);

    default PersonDTO personToPersonDto(Person person) {
        return Mappers.getMapper(PersonMapper.class).personToPersonDTO(person);
    }
}
