package com.baeldung.setnullproperty.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassMapping;

import com.baeldung.setnullproperty.dto.ArticleDTO;
import com.baeldung.setnullproperty.dto.ReviewableDTO;
import com.baeldung.setnullproperty.dto.WeeklyNewsDTO;
import com.baeldung.setnullproperty.entity.Article;
import com.baeldung.setnullproperty.entity.Reviewable;
import com.baeldung.setnullproperty.entity.WeeklyNews;

@Mapper
public interface ReviewableMapper {

    @SubclassMapping(source = ArticleDTO.class, target = Article.class)
    @SubclassMapping(source = WeeklyNewsDTO.class, target = WeeklyNews.class)
    @Mapping(target = "reviewedBy", expression = "java(null)")
    Reviewable toReviewable(ReviewableDTO dto);

}
