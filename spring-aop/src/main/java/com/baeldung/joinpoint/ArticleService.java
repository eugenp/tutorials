package com.baeldung.joinpoint;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ArticleService {

    public List<String> getArticleList() {
        return Arrays.asList(
                "Article 1",
                "Article 2"
        );
    }

}
