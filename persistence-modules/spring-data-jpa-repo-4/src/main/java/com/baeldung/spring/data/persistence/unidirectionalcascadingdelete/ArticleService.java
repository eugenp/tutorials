package com.baeldung.spring.data.persistence.unidirectionalcascadingdelete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public void deleteArticle(Article article) {
        articleRepository.delete(article);

    }

}
