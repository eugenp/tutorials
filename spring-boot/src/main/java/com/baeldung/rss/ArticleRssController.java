package com.baeldung.rss;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/rss", produces = "application/*")
public class ArticleRssController {

    @RequestMapping(method = RequestMethod.GET)
    public String articleFeed() {
        return "articleFeedView";
    }

}
