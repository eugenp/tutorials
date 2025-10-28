package com.baeldung.setnullproperty.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.baeldung.setnullproperty.dto.ArticleDTO;
import com.baeldung.setnullproperty.entity.Article;

@Mapper(uses = ReviewableMapper.class)
public interface ArticleMapper {

    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "id", source = "persisted.id")
    @Mapping(target = "reviewedBy", expression = "java(null)")
    Article toArticleUsingExpression(ArticleDTO dto, Article persisted);

    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "id", source = "persisted.id")
    @Mapping(target = "reviewedBy", expression = "java(getDefaultReviewStatus())")
    Article toArticleUsingExpressionMethod(ArticleDTO dto, Article persisted);

    default String getDefaultReviewStatus() {
        return null;
    }

    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "id", source = "persisted.id")
    @Mapping(target = "reviewedBy", ignore = true)
    Article toArticleUsingIgnore(ArticleDTO dto, Article persisted);

    @AfterMapping
    default void setNullReviewedBy(@MappingTarget Article article) {
        article.setReviewedBy(null);
    }

    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "id", source = "persisted.id")
    Article toArticleUsingAfterMapping(ArticleDTO dto, Article persisted);

    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "id", source = "persisted.id")
    @Mapping(target = "reviewedBy", qualifiedByName = "toNull")
    Article toArticleUsingQualifiedBy(ArticleDTO dto, Article persisted);

    @Named("toNull")
    default String mapToNull(String property) {
        return null;
    }
}
