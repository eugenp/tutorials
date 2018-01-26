package com.baeldung.beaninjection;

import com.baeldung.beaninjection.controller.ConstructorDIController;
import com.baeldung.beaninjection.controller.SetterDIController;
import com.baeldung.beaninjection.model.Article;
import com.baeldung.beaninjection.model.Author;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class Application {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {

        applicationContext = SpringApplication.run(Application.class, args);
        SetterDIController setterDIController = (SetterDIController) applicationContext.getBean("setterDIController");
        ConstructorDIController constructorDIController = (ConstructorDIController) applicationContext.getBean("constructorDIController");

        List<Article> articles = setterDIController.getArticles();
        for (Article article : articles) {
            System.out.println("Title:" + article.getTitle() + ", author:" + article.getAuthor().getName());
        }

        List<Author> authors = constructorDIController.getAuthors();
        for (Author author : authors) {
            System.out.println("Name:" + author.getName() + ", age:" + author.getAge());
        }
    }
}
