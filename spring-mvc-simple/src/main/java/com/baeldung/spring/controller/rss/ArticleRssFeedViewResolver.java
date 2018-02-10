package com.baeldung.spring.controller.rss;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

public class ArticleRssFeedViewResolver implements ViewResolver {

    @Override
    public View resolveViewName(String s, Locale locale) throws Exception {
        ArticleFeedView articleFeedView = new ArticleFeedView();
        return articleFeedView;
    }
}
