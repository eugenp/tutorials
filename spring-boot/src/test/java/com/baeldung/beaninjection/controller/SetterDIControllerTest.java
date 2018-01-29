package com.baeldung.beaninjection.controller;

import com.baeldung.beaninjection.Application;
import com.baeldung.beaninjection.model.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by ggallo on 29/01/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class SetterDIControllerTest {

    @Autowired
    private SetterDIController setterDIController;

    @Test
    public void whenCallToSetterDIController_thenReturnListOfArticles() {
        List<Article> articleList = setterDIController.getArticles();

        assertTrue(articleList.size() == 1);
        assertTrue(articleList.get(0).getTitle().equals("First Article for Baeldung"));
        assertTrue(articleList.get(0).getAuthor().getName().equals("Gorka"));
    }

}
