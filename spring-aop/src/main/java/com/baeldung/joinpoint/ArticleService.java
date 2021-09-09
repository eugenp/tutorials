package com.baeldung.joinpoint;

import java.util.Arrays;
import java.util.List;

public class ArticleService {

    public List<String> getArticleList() {
        return Arrays.asList(
                "Article 1",
                "Article 2"
        );
    }

}
