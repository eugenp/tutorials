package com.baeldung.joinpoint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableAspectJAutoProxy
public class JoinPointAroundCacheAspectIntegrationTest {

    @Autowired
    private ArticleService articleService;

    @Before
    public void removeCache() {
        JoinPointAroundCacheAspect.CACHE.clear();
    }
    @Test
    public void shouldPopulateCache() {
        assertTrue(JoinPointAroundCacheAspect.CACHE.isEmpty());

        List<String> articles = articleService.getArticleList();

        assertFalse(JoinPointAroundCacheAspect.CACHE.isEmpty());
        assertEquals(JoinPointAroundCacheAspect.CACHE.size(), 1);
        assertEquals(JoinPointAroundCacheAspect.CACHE.values().iterator().next(), articles);
    }

}
