package com.baeldung.spring.controller.rss;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArticleRssController {

    @GetMapping(value = "/rss", produces = "application/*")
    public String articleFeed() {
        return "articleFeedView";
    }

}
