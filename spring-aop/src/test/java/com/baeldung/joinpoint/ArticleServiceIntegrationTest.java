package com.baeldung.joinpoint;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ArticleServiceIntegrationTest {

    @Autowired
    private ArticleService articleService;

    @Test
    public void articleListShouldNotBeEmpty() {
        List<String> articleList = articleService.getArticleList();

        assertFalse(articleList.isEmpty());
    }

}
