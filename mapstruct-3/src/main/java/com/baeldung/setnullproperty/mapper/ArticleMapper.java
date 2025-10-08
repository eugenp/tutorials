package com.baeldung.setnullproperty.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.baeldung.setnullproperty.dto.ArticleDTO;
import com.baeldung.setnullproperty.entity.Article;

@Mapper
public interface ArticleMapper {

    //    @Mapping(target = "reviewStatus", constant = null)
    //    Article toArticleUsingNullDirectly(ArticleDTO dto);

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
    //    @Mapping(target = ".", source = "oldObject")
    //    @Mapping(target = "reviewedBy", ignore = true)
    //    Article toArticleUsingIgnore(ArticleDTO dto, Article persisted);
    //

    //
    //    Article toArticleUsingAfterMapping(ArticleDTO dto);
    //
    //    @AfterMapping
    //    default void setNullReviewedBy(@MappingTarget Article article) {
    //        article.setReviewedBy(null);
    //    }
    //
    //    @Mapping(target = "reviewedBy", qualifiedByName = "toNull")
    //    Article toArticleWithNullStatus(ArticleDTO dto);
    //
    //    @Named("toNull")
    //    default String mapToNull(String property) {
    //        return null;
    //    }
}
