package com.baeldung.mockmvcresponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArticleController.class)
class ArticleControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenGetArticle_thenReturnArticleObjectUsingJackson() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/article"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        Article article = objectMapper.readValue(json, Article.class);

        assertNotNull(article);
        assertEquals(1L, article.getId());
        assertEquals("Learn Spring Boot", article.getTitle());
    }

    @Test
    void whenGetArticle_thenReturnListUsingJacksonTypeReference() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        List<Article> articles = objectMapper.readValue(json, new TypeReference<>() {});

        assertNotNull(articles);
        assertEquals(2, articles.size());
    }

    @Test
    void whenGetArticle_thenReturnListUsingJacksonCollectionType() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        CollectionType collectionType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, Article.class);
        List<Article> articles = objectMapper.readValue(json, collectionType);

        assertNotNull(articles);
        assertEquals(2, articles.size());
    }

    @Test
    void whenGetArticle_thenReturnArticleObjectUsingGson() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/article"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        Article article = new Gson().fromJson(json, Article.class);

        assertNotNull(article);
        assertEquals(1L, article.getId());
        assertEquals("Learn Spring Boot", article.getTitle());
    }

    @Test
    void whenGetArticle_thenReturnArticleListUsingGson() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        TypeToken<List<Article>> typeToken = new TypeToken<>() {};
        List<Article> articles = new Gson().fromJson(json, typeToken.getType());

        assertNotNull(articles);
        assertEquals(2, articles.size());
    }

}