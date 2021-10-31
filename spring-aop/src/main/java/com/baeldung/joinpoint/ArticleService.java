package com.baeldung.joinpoint;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ArticleService {

    public List<String> getArticleList() {
        return Arrays.asList(
          "Article 1",
          "Article 2"
        );
    }

    public List<String> getArticleList(String startsWithFilter) {
        if (StringUtils.isBlank(startsWithFilter)) {
            throw new IllegalArgumentException("startsWithFilter can't be blank");
        }

        return getArticleList()
          .stream()
          .filter(a -> a.startsWith(startsWithFilter))
          .collect(toList());
    }

}
