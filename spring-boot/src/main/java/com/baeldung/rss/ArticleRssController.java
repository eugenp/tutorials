package com.baeldung.rss;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/rss", produces = "application/*")
public class ArticleRssController {

    @GetMapping
    public String articleFeed() {
        return "articleFeedView";
    }

}
