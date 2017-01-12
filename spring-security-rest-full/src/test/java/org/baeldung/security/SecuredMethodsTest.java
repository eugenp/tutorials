package org.baeldung.security;

import org.baeldung.config.TestsSecurityConfig;
import org.baeldung.persistence.dao.ArticleRepository;
import org.baeldung.persistence.model.Article;
import org.baeldung.persistence.model.Tag;
import org.baeldung.persistence.model.User;
import org.baeldung.persistence.service.ArticleService;
import org.baeldung.persistence.service.UserService;
import org.baeldung.spring.PersistenceConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

import static com.google.common.collect.Sets.newHashSet;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class, TestsSecurityConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
@TestExecutionListeners(listeners={
    DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    WithSecurityContextTestExecutionListener.class})
public class SecuredMethodsTest {

    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleRepository articleRepository;

    final User admin;
    final User user1;
    {
        admin = new User();
        admin.setUsername("admin");
        admin.setFirstName("John");
        admin.setLastName("Smith");

        user1 = new User();
        user1.setUsername("user1");
        user1.setFirstName("Harry");
        user1.setLastName("Cane");
    }

    @Before
    public void setup() {
        userService.save(admin);
        Article article = new Article(admin, "Hello World!");
        article.setTags(newHashSet(new Tag("spring"), new Tag("security"), new Tag("expressions")));
        articleService.save(article);

        userService.save(user1);
        article = new Article(user1, "My first article");
        article.setTags(newHashSet(new Tag("spring"), new Tag("SpEL")));
        articleService.save(article);

        article = new Article(user1, "My second article");
        article.setTags(newHashSet(new Tag("SpEL"), new Tag("security"), new Tag("expressions")));
        articleService.save(article);
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    public void givenAdmin_whenSearchAllComments_thenGetAllUsersComments() {
        List<Article> articles = articleService.findAll();
        assertThat(articles.size(), is(3));
    }

    @Test
    @WithMockUser(value = "user1", roles = {"USER"})
    public void givenUser_whenSearchAllComments_thenGetOnlyHerComments() {
        List<Article> articles = articleService.findAll();
        assertThat(articles.size(), is(2));
    }

    @Test
    @WithMockUser(value = "manager", roles = {"MANAGER"})
    public void givenRoleManager_whenListUsers_thenGetAllUsers() {
        List<User> users = userService.findAll();
        assertThat(users.size(), is(2));
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(value = "user1", roles = {"USER"})
    public void givenUser_whenListUsers_thenFoo() {
        List<User> users = userService.findAll();
    }

    @Test
    @WithMockUser(value = "user1", roles = {"USER"})
    public void givenUser_whenFindHisArticleByTitle_thenFound() {
        Article article = articleService.find(user1, "My first article");
        assertThat(article, notNullValue());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(value = "user1", roles = {"USER"})
    public void givenUser_whenFindNotHisArticleByTitle_thenFail() {
        Article article = articleService.find(admin, "Hello World!");
        assertThat(article, notNullValue());
    }

    @Test
    @WithMockUser(value = "user1", roles = {"USER"})
    public void givenTags_whenFilterArticles_returnOnlyUsersArticles() {
        HashSet<Tag> tags = newHashSet(new Tag("security"), new Tag("expressions"));

        List<Article> allArticles = articleRepository.findAll();
        assertThat(allArticles.size(), is(3));

        List<Article> filterArticles = articleService.filterByTags(allArticles, tags);
        assertThat(filterArticles.size(), is(1));
    }
}
