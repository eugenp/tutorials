package com.baeldung.springmustache.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baeldung.springmustache.model.Article;

@Controller
public class ArticleController {

    @RequestMapping("/article")
    public ModelAndView displayArticle(Map<String, Object> model) {

        List<Article> articles = new LinkedList<>();
        IntStream.range(0, 10)
         .forEach(count -> {
            articles.add(generateArticle("Article Title " + count));
          });

        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("articles", articles);

        return new ModelAndView("index", modelMap);
    }

    private Article generateArticle(String title) {
        Article article = new Article();
        DataFactory factory = new DataFactory();
        article.setTitle(title);
        article.setBody(
          "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur faucibus tempor diam. In molestie arcu eget ante facilisis sodales. Maecenas porta tellus sapien, eget rutrum nisi blandit in. Mauris tempor auctor ante, ut blandit velit venenatis id. Ut varius, augue aliquet feugiat congue, arcu ipsum finibus purus, dapibus semper velit sapien venenatis magna. Nunc quam ex, aliquet at rutrum sed, vestibulum quis libero. In laoreet libero cursus maximus vulputate. Nullam in fermentum sem. Duis aliquam ullamcorper dui, et dictum justo placerat id. Aliquam pretium orci quis sapien convallis, non blandit est tempus.");
        article.setPublishDate(factory.getBirthDate().toString());
        article.setAuthor(factory.getName());
        return article;
    }
}


