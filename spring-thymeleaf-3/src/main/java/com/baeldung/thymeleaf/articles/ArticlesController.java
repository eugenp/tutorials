package main.java.com.baeldung.thymeleaf.articles;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api/articles")
public class ArticlesController {

    @GetMapping
    public String allArticles(Model model) {
        model.addAttribute("articles", fetchArticles());
        return "articles/articles-list";
    }

    private List<Article> fetchArticles() {
        return Arrays.asList(
          new Article(
            "Introduction to Using Thymeleaf in Spring",
              "https://www.baeldung.com/thymeleaf-in-spring-mvc"
            ),
            new Article(
              "Spring Boot CRUD Application with Thymeleaf",
              "https://www.baeldung.com/spring-boot-crud-thymeleaf"
          ),
          new Article(
            "Spring MVC Data and Thymeleaf",
            "https://www.baeldung.com/spring-mvc-thymeleaf-data"
          )
        );
    }
}
